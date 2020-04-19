package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlansActivity extends AppCompatActivity{
   private RecyclerView planList;
   private DatabaseReference databaseReference;
   private FirebaseAuth mAuth;
   private FirebaseRecyclerAdapter<MyPlans,PlansViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        String userid=firebaseUser.getUid();
        planList=(RecyclerView)findViewById(R.id.my_planning);
        planList.setHasFixedSize(true);
        planList.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Query query=databaseReference.child("YourTrip").child(userid);
        BottomNavigationView navigationView=findViewById(R.id.btm_nav);
        Menu menu=navigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.home){
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                if (id == R.id.explore) {
                    startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
                }
                if (id == R.id.my_account) {
                    startActivity(new Intent(getApplicationContext(),AccountsActivity.class));
                }
                return false;
            }
        });
        FirebaseRecyclerOptions<MyPlans> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<MyPlans>()
                .setQuery(query, MyPlans.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyPlans, PlansViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public PlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_plan, parent, false);

                return new PlansViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull PlansViewHolder holder, int position, @NonNull MyPlans model) {

                                    holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("YourTrip")
                                                    .child(userid)
                                                    .child(getRef(position).getKey())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                               if(task.isSuccessful()){
                                                                   Toast.makeText(PlansActivity.this,"Successfully cancelled",
                                                                           Toast.LENGTH_LONG).show();
                                                               }else{
                                                                   String error=task.getException().getMessage();
                                                                   Toast.makeText(PlansActivity.this,"Error:"+error,
                                                                           Toast.LENGTH_LONG).show();
                                                               }
                                                        }
                                                    });
                                        }
                                    });
                                    holder.btn_book.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String list= FirebaseDatabase.getInstance().getReference()
                                                        .child("YourTrip").child(userid)
                                                        .child(getRef(position).getKey()).toString();
                                            String[] id=list.split("/");
                                            FirebaseDatabase.getInstance().getReference().child("BooKings").child(userid)
                                                    .child(id[5]).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(!dataSnapshot.exists()){
                                                        //splitting the string to get only the specified trip.
                                                        Intent intent=new Intent(PlansActivity.this,BookingActivity.class);
                                                        intent.putExtra("TripId", id[5]);
                                                        startActivity(intent);
                                                    }else{
                                                        Toast.makeText(PlansActivity.this,"Already Booked,Go to My Bookings",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    String Error=databaseError.toString();
                                                    Toast.makeText(PlansActivity.this,"Error:"+Error,Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }
                                    });
                                      holder.setMyPlans(model);
            }
        };
        planList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
       }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.stopListening();
        }
    }
    public static class PlansViewHolder extends RecyclerView.ViewHolder{
        private TextView start, destin, duration,budget;
        private ImageView means;
        private Button btn_cancel,btn_book;
        public PlansViewHolder(@NonNull View itemView) {
            super(itemView);
            start=itemView.findViewById(R.id.from);
            destin=itemView.findViewById(R.id.to);
            means=itemView.findViewById(R.id.means);
            duration=itemView.findViewById(R.id.gender);
            budget=itemView.findViewById(R.id.budget_of_trip);
            btn_cancel=itemView.findViewById(R.id.btn_del);
            btn_book=itemView.findViewById(R.id.btn_book);

        }
        void setMyPlans(MyPlans myPlans) {
                String st=myPlans.getStarting();
                 start.setText(st);
                String dest=myPlans.getDestination();
                destin.setText(dest);
                String dur=myPlans.getDuration();
                duration.setText(dur+"Days");
                String bud=myPlans.getBudget();
                budget.setText("BUDGET:$"+bud);
                String imageUrl=myPlans.getImage();
            Glide.with(means).load(imageUrl).into(means);
        }
    }
}

