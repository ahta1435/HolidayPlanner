package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

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
        private TextView Destination, duration, noOfTravellers;
        public PlansViewHolder(@NonNull View itemView) {
            super(itemView);
            Destination=itemView.findViewById(R.id.dest);
           duration=itemView.findViewById(R.id.numberOfDays);
            noOfTravellers=itemView.findViewById(R.id.no_of_people);
        }

        void setMyPlans(MyPlans myPlans) {
            String destination = myPlans.getDestination();
            Destination.setText(destination);
            String duration_dur= myPlans.getDuration();
            duration.setText(duration_dur);
            String  travellers= myPlans.getNumberOfPeople();
            noOfTravellers.setText(travellers);
        }
    }
}

