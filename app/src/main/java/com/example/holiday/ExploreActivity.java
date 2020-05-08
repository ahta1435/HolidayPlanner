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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExploreActivity extends AppCompatActivity {
    private RecyclerView rview;
    private FirestoreRecyclerAdapter<Places,PlacesViewHolder> firestoreRecyclerAdapter;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        BottomNavigationView navigationView=findViewById(R.id.btm_nav);
        rview=(RecyclerView)findViewById(R.id.list_places);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore=FirebaseFirestore.getInstance();
        CollectionReference collectionReference= firebaseFirestore.collection("places");
        Query query=collectionReference;
        FirestoreRecyclerOptions<Places> response = new FirestoreRecyclerOptions.Builder<Places>()
                .setQuery(query, Places.class)
                .build();
        firestoreRecyclerAdapter =new FirestoreRecyclerAdapter<Places, PlacesViewHolder>(response) {
            @Override
            protected void onBindViewHolder(@NonNull PlacesViewHolder holder, int position, @NonNull Places model) {
                              holder.setPlaces(model);
                              holder.itemView.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
                                      String docId=snapshot.getId();
                                      Intent intent=new Intent(ExploreActivity.this,AboutActivity.class);
                                      intent.putExtra("placeId",docId);
                                      startActivity(intent);
                                  }
                              });
            }
            @NonNull
            @Override
            public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_places,parent,false);
                return new PlacesViewHolder(view);
            }
        };
        rview.setAdapter(firestoreRecyclerAdapter);
        Menu menu=navigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.home){
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                if (id == R.id.my_plans) {
                    startActivity(new Intent(getApplicationContext(),PlansActivity.class));
                }
                if (id == R.id.my_account) {
                    startActivity(new Intent(getApplicationContext(),AccountsActivity.class));
                }
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firestoreRecyclerAdapter!=null){
            firestoreRecyclerAdapter.stopListening();
        }
    }

    public  static class PlacesViewHolder extends RecyclerView.ViewHolder{
        private ImageView pl;
        private TextView text;
        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            pl=itemView.findViewById(R.id.places);
            text=itemView.findViewById(R.id.places_name);
        }
        void setPlaces(Places places){
            String p=places.getName();
            text.setText(p);
            String imageUrl=places.getImages();
            Glide.with(pl).load(imageUrl).into(pl);
        }
    }

}
