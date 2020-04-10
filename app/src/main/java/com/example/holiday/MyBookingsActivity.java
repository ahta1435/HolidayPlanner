package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.holiday.RetrieveTickets.*;
public class MyBookingsActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<RetrieveTickets, BookingHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        recyclerView = (RecyclerView) findViewById(R.id.my_bookings);
        recyclerView.setHasFixedSize(true);
        String tripId = getIntent().getStringExtra("TripId");
       // String bookingId = getIntent().getStringExtra("BookingId");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("BooKings").child(userId).child(tripId);
        FirebaseRecyclerOptions<RetrieveTickets> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<RetrieveTickets>()
                .setQuery(query, RetrieveTickets.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RetrieveTickets, BookingHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull BookingHolder holder, int position, @NonNull RetrieveTickets model) {
                                  holder.setPassengers(model);
            }

            @NonNull
            @Override
            public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tickets, parent, false);
                return new BookingHolder(view);
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                Toast.makeText(MyBookingsActivity.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public static class BookingHolder extends RecyclerView.ViewHolder{
        private TextView passenger;
        public BookingHolder(@NonNull View itemView) {
            super(itemView);
            passenger=(TextView)itemView.findViewById(R.id.passenger);
        }
        void setPassengers(RetrieveTickets retrieveTickets) {
            int size=retrieveTickets.getCount();
            String[] names=new String[size];
            String[] ages=new String[size];
            String[] genders=new String[size];
            List<Map<String,Object>> passengers=retrieveTickets.getPassenger();
            for(int i=0;i<size;i++){
                   names[i]=passengers.get(i).toString();
                   passenger.setText(names[i]);
            }
        }
    }
}