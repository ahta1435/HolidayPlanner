package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MyBookingsActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Passengers,TicketHolder> firebaseRecyclerAdapter;
    private String BusId;
    private String SeatId;
    private int res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        String tripId = getIntent().getStringExtra("TripId");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
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
                             holder.view_details.setOnClickListener(new View.OnClickListener() {
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
                                                         if(res==2){
                                                             FirebaseDatabase.getInstance().getReference()
                                                                     .child("BookedSeats").child(BusId).child(SeatId)
                                                                     .removeValue()
                                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                         @Override
                                                                         public void onComplete(@NonNull Task<Void> task) {
                                                                             if(task.isSuccessful()){
                                                                                 Toast.makeText(MyBookingsActivity.this,
                                                                                         "Seats Released Successfully", Toast.LENGTH_LONG).show();
                                                                             }
                                                                         }
                                                                     });
                                                         }
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });
                                     FirebaseDatabase.getInstance().getReference()
                                             .child("BooKings").child(userId).child(getRef(position).getKey()).removeValue()
                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                 Toast.makeText(MyBookingsActivity.this,
                                                         "Ticket SuccessFully Cancelled", Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     });

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
              private TextView s,t,ticket;
              private ImageView i;
              private Button btn_cancel_ticket,view_details;
            public TicketHolder(@NonNull View itemView) {
                super(itemView);
                s=(TextView)itemView.findViewById(R.id.journey_from_airport);
                t=(TextView)itemView.findViewById(R.id.journey_to);
                i=(ImageView) itemView.findViewById(R.id.means);
                btn_cancel_ticket=(Button)itemView.findViewById(R.id.cancel_ticket);
                view_details=(Button)itemView.findViewById(R.id.view_detail_of_ticket);
                ticket=(TextView)itemView.findViewById(R.id.date_of_ticket);

            }
           public void SetPassenger(Passengers passengers){
               FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
               @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
               dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
               String TodaysDate=dateTimeInGMT.format(new Date());
               HashMap<String,Integer> Months=new HashMap<>();
               Months.put("JAN",1);
               Months.put("FEB",2);
               Months.put("MAR",3);
               Months.put("APR",4);
               Months.put("MAY",5);
               Months.put("JUN",6);
               Months.put("JUL",7);
               Months.put("AUG",8);
               Months.put("SEP",9);
               Months.put("OCT",10);
               Months.put("NOV",11);
               Months.put("DEC",12);
               String[] str=TodaysDate.split("-");
                int curday=Integer.parseInt(str[2]);
                String currentMonth=str[1];
                int runningmonth=Months.get(currentMonth);
               String start=passengers.getStarting();
                         s.setText(start);
               String destination=passengers.getDestination();
                       t.setText(destination);
                       int res=passengers.getRes();
                           if(res==2){
                              String busId=passengers.getBusId();
                               DocumentReference documentReference=firebaseFirestore.collection("Buses")
                                       .document(busId);
                               documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                   @Override
                                   public void onSuccess(DocumentSnapshot documentSnapshot){
                                       ///for ticket day and month
                                       ///
                                       Timestamp timestamp=documentSnapshot.getTimestamp("dateAndTime");
                                       String str=timestamp.toDate().toString();
                                       String[] str1=str.split(" ");
                                       StringBuilder DateOfTicket=new StringBuilder();
                                       DateOfTicket.append(str1[2]+"-"+str1[1]+"-"+str1[5]);//STR1[2]-->DATE
                                                         // ,STR1[1]-->MONTH STR1[5]=YEAR;
                                       int ticketday=Integer.parseInt(str1[2]);
                                       String ticketmonth=str1[1];
                                       ///
                                       ///current date and month
                                       ///
                                       ////
                                       ticket.setText(DateOfTicket);
                                       /*if(curday>ticketday){
                                           int runningmonth=Months.get("curmonth");
                                           int monthOfTicket=Months.get("ticketmonth");
                                           if(runningmonth>=monthOfTicket){
                                               ticket.setText("Today is ahead of that day");
                                           }else{
                                               ticket.setText(str1[2]+"\n"+str1[1]);
                                           }
                                       }*/
                                      // if(curday<ticketday){
                                        //   ticket.setText(str1[2]+"\n"+str1[1]);
                                      // }

                                   }
                               });
                           }
                           if(res==1){
                               String planeId=passengers.getPlaneId();
                               DocumentReference documentReference=firebaseFirestore.collection("Planes")
                                       .document(planeId);
                               documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                   @Override
                                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                                       Timestamp timestamp=documentSnapshot.getTimestamp("dateAndtime");
                                       String str=timestamp.toDate().toString();
                                       String[] str1=str.split(" ");
                                       StringBuilder stringBuilder=new StringBuilder();
                                       //str1[2]+"\n"+
                                      // stringBuilder.append(str1[1]);
                                       //ticket.setText(str1[5]);
                                   }
                               });
                           }
               String imageUrl=passengers.getImage();
               Glide.with(i).load(imageUrl).into(i);

            }
        }
    }
