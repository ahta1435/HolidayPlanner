package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PlansActivity extends AppCompatActivity{
   private RecyclerView planList;
   private DatabaseReference databaseReference;
   private FirebaseRecyclerAdapter<MyPlans,PlansViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        planList=(RecyclerView)findViewById(R.id.my_planning);
        planList.setHasFixedSize(true);
        planList.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Query query=databaseReference.child("YourTrip");
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
        public PlansViewHolder(@NonNull View itemView) {
            super(itemView);
            start=itemView.findViewById(R.id.from);
            destin=itemView.findViewById(R.id.to);
            means=itemView.findViewById(R.id.means);
            duration=itemView.findViewById(R.id.duration_of_trip);
            budget=itemView.findViewById(R.id.budget_of_trip);
        }

        void setMyPlans(MyPlans myPlans) {
                String st=myPlans.getStarting();
                 start.setText(st);
                String dest=myPlans.getDestination();
                destin.setText(dest);
                String dur=myPlans.getDuration();
                duration.setText(dur);
                String bud=myPlans.getBudget();
                budget.setText(bud);
                String imageUrl=myPlans.getImage();
            Glide.with(means).load(imageUrl).into(means);
        }
    }
}

