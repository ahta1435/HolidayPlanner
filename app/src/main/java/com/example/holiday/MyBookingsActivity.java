package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
                ///filtering of tickets
                FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd");
                dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
                String TodaysDate=dateTimeInGMT.format(new Date());
                HashMap<String,Integer> Months=new HashMap<>();
                Months.put("Jan",1);
                Months.put("Feb",2);
                Months.put("Mar",3);
                Months.put("Apr",4);
                Months.put("May",5);
                Months.put("Jun",6);
                Months.put("Jul",7);
                Months.put("Aug",8);
                Months.put("Sep",9);
                Months.put("Oct",10);
                Months.put("Nov",11);
                Months.put("Dec",12);
                String[] str=TodaysDate.split("-");
                int currentDay=Integer.parseInt(str[2]);
                int currentMonth=Integer.parseInt(str[1]);
                int resId=model.getRes();
                if(resId==2){
                    String busId=model.getBusId();
                    DocumentReference documentReference=firebaseFirestore.collection("Buses")
                            .document(busId);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot){
                            Timestamp timestamp=documentSnapshot.getTimestamp("dateAndTime");
                            String str=timestamp.toDate().toString();
                            String[] str1=str.split(" ");
                            StringBuilder DateOfTicket=new StringBuilder();
                            DateOfTicket.append(str1[2]+"-"+str1[1]+"-"+str1[5]);//STR1[2]-->DATE
                            // ,STR1[1]-->MONTH STR1[5]=YEAR;
                            int ticketDay=Integer.parseInt(str1[2]);
                            String ticketMonth=str1[1];
                            int TicketMonthNumber=Months.get(ticketMonth);
                            //
                            /// checking of the date
                            ///
                            ////
                            if(currentDay>ticketDay){
                                if(currentMonth>=TicketMonthNumber){
                                    String dateOfTicket=DateOfTicket.toString();
                                    String name=model.getName();
                                    String bookingId=model.getBookingId();
                                    String busId=model.getBusId();
                                    String start=model.getStarting();
                                    String destination=model.getDestination();
                                    String seatId=model.getSeatId();
                                    String seat=model.getSeats();
                                    String gender=model.getGender();
                                    String age=model.getAge();
                                    int count=model.getCount();
                                    String image=model.getImage();
                                    int res=model.getRes();
                                    HistoryOfJourneyForBus historyOfJourney=new HistoryOfJourneyForBus(name,bookingId,age,image,count,gender
                                                                    ,start,destination,res,seat,seatId,busId,dateOfTicket);
                                    databaseReference.child("History").child(userId)
                                            .child(getRef(position).getKey()).setValue(historyOfJourney);
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("BookedSeats").child(model.getBusId()).child(model.getSeatId())
                                            .removeValue();
                                    databaseReference.child("YourTrip").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                  databaseReference.child("BooKings").child(userId)
                                           .child(getRef(position).getKey()).removeValue();
                                }else{
                                   holder.SetPassenger(model);
                                }
                            }
                            if(currentDay<ticketDay) {
                                if (currentMonth <= TicketMonthNumber) {
                                    holder.SetPassenger(model);
                                }else{
                                    String dateOfTicket=DateOfTicket.toString();
                                    String name=model.getName();
                                    String bookingId=model.getBookingId();
                                    String busId=model.getBusId();
                                    String start=model.getStarting();
                                    String destination=model.getDestination();
                                    String seatId=model.getSeatId();
                                    String seat=model.getSeats();
                                    String gender=model.getGender();
                                    String age=model.getAge();
                                    int count=model.getCount();
                                    String image=model.getImage();
                                    int res=model.getRes();
                                    HistoryOfJourneyForBus historyOfJourney=new HistoryOfJourneyForBus(name,bookingId,age,image,count,gender
                                            ,start,destination,res,seat,seatId,busId,dateOfTicket);
                                    databaseReference.child("History").child(userId)
                                            .child(getRef(position).getKey()).setValue(historyOfJourney);
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("BookedSeats").child(model.getBusId()).child(model.getSeatId())
                                            .removeValue();
                                    databaseReference.child("YourTrip").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                    databaseReference.child("BooKings").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                }
                            }
                        }
                    });
                }
                if(resId==1){
                    String planeId=model.getPlaneId();
                    DocumentReference documentReference=firebaseFirestore.collection("Planes")
                            .document(planeId);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Timestamp timestamp=documentSnapshot.getTimestamp("dateAndtime");
                            String str=timestamp.toDate().toString();
                            String[] str1=str.split(" ");
                            StringBuilder DateOfTicket=new StringBuilder();
                            DateOfTicket.append(str1[2]+"-"+str1[1]+"-"+str1[5]);//STR1[2]-->DATE
                            // ,STR1[1]-->MONTH STR1[5]=YEAR;
                            int ticketDay=Integer.parseInt(str1[2]);
                            String ticketMonth=str1[1];
                            int TicketMonthNumber=Months.get(ticketMonth);
                            //
                            /// checking of the date
                            ///
                            ////
                            if(currentDay>ticketDay){
                                if(currentMonth>=TicketMonthNumber){
                                    String dateOfTicket=DateOfTicket.toString();
                                    String name=model.getName();
                                    String bookingId=model.getBookingId();
                                    String planeId=model.getPlaneId();
                                    String start=model.getStarting();
                                    String destination=model.getDestination();
                                    String gender=model.getGender();
                                    String age=model.getAge();
                                    int count=model.getCount();
                                    String image=model.getImage();
                                    int res=model.getRes();
                                    HistoryOfJourneyForPlane historyOfJourney=new HistoryOfJourneyForPlane(name,bookingId,age,image,count,gender
                                            ,start,destination,res,planeId,dateOfTicket);
                                    databaseReference.child("History").child(userId)
                                            .child(getRef(position).getKey()).setValue(historyOfJourney);
                                    databaseReference.child("YourTrip").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                    databaseReference.child("BooKings").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                }else{
                                    holder.SetPassenger(model);
                                }
                            }
                            if(currentDay<ticketDay) {
                                if (currentMonth <= TicketMonthNumber) {
                                    holder.SetPassenger(model);
                                }else{
                                    String dateOfTicket=DateOfTicket.toString();
                                    String name=model.getName();
                                    String bookingId=model.getBookingId();
                                    String planeId=model.getPlaneId();
                                    String start=model.getStarting();
                                    String destination=model.getDestination();
                                    String gender=model.getGender();
                                    String age=model.getAge();
                                    int count=model.getCount();
                                    String image=model.getImage();
                                    int res=model.getRes();
                                    HistoryOfJourneyForPlane historyOfJourney=new HistoryOfJourneyForPlane(name,bookingId,age,image,count,gender
                                            ,start,destination,res,planeId,dateOfTicket);
                                    databaseReference.child("History").child(userId)
                                            .child(getRef(position).getKey()).setValue(historyOfJourney);
                                    databaseReference.child("YourTrip").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                    databaseReference.child("BooKings").child(userId)
                                            .child(getRef(position).getKey()).removeValue();
                                }
                            }
                        }
                    });
                }
                                          ///
                                         //showing the details
                                          //
                                         ///

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
                                       Timestamp timestamp=documentSnapshot.getTimestamp("dateAndTime");
                                       String str=timestamp.toDate().toString();
                                       String[] str1=str.split(" ");
                                       StringBuilder DateOfTicket=new StringBuilder();
                                       DateOfTicket.append(str1[2]+"\n"+str1[1]+"\n"+str1[5]);//STR1[2]-->DATE
                                                         // ,STR1[1]-->MONTH STR1[5]=YEAR;
                                       ticket.setText(DateOfTicket);
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
                                       stringBuilder.append(str1[2]+"\n"+str1[1]+"\n"+str1[5]);
                                       ticket.setText(stringBuilder);
                                   }
                               });
                           }
               String imageUrl=passengers.getImage();
               Glide.with(i).load(imageUrl).into(i);

            }
        }
    }
