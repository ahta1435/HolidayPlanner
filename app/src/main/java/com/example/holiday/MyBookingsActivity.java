package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBookingsActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Passengers,TicketHolder> firebaseRecyclerAdapter;
    private String BusId;
    private String SeatId;
    private int res;
    private DatabaseReference databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        String tripId = getIntent().getStringExtra("TripId");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        databaseReference1= FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView=(RecyclerView)findViewById(R.id.tickets);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query=databaseReference.child("BooKings").child(userId);
        FirebaseRecyclerOptions<Passengers> firebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<Passengers>()
                    .setQuery(query,Passengers.class)
                .build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Passengers, TicketHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull TicketHolder holder, int position, @NonNull Passengers model) {
                             holder.itemView.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     String list= FirebaseDatabase.getInstance().getReference()
                                             .child("BooKings").child(userId)
                                             .child(getRef(position).getKey()).toString();
                                     String[] id=list.split("/");
                                     Intent intent=new Intent(MyBookingsActivity.this,TicketDetailsActivity.class);
                                     intent.putExtra("IdOfTrip",id[5]);
                                     startActivity(intent);
                                 }
                             });
                             holder.btn_cancel_ticket.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     databaseReference.child("BooKings").child(userId)
                                             .child(getRef(position).getKey())
                                             .addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                     if(dataSnapshot.exists()) {
                                                         Passengers addBusBooking = dataSnapshot.getValue(Passengers.class);
                                                         BusId = addBusBooking.getBusId();
                                                         SeatId = addBusBooking.getSeatId();
                                                         res = addBusBooking.getRes();
                                                         databaseReference1.child("BookedSeats")
                                                                 .child(BusId).child(SeatId).removeValue();
                                                     }
                                                 }
                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });
                                      FirebaseDatabase.getInstance().getReference()
                                             .child("BooKings").child(userId).child(getRef(position).getKey()).removeValue();

                                 }
                             });
                              holder.SetPassenger(model);
            }

            @NonNull
            @Override
            public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tickets,parent,false);
                return new TicketHolder(view);
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
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.stopListening();
        }
    }
    public static class TicketHolder extends RecyclerView.ViewHolder{
              private TextView s,t;
              private ImageView i;
              private Button btn_cancel_ticket;
            public TicketHolder(@NonNull View itemView) {
                super(itemView);
                s=(TextView)itemView.findViewById(R.id.journey_from_airport);
                t=(TextView)itemView.findViewById(R.id.journey_to);
                i=(ImageView) itemView.findViewById(R.id.means);
                btn_cancel_ticket=(Button)itemView.findViewById(R.id.cancel_ticket);

            }
           public void SetPassenger(Passengers passengers){

               String start=passengers.getStarting();
                         s.setText(start);
               String destination=passengers.getDestination();
                       t.setText(destination);
               String imageUrl=passengers.getImage();
               Glide.with(i).load(imageUrl).into(i);

            }
        }
    }
