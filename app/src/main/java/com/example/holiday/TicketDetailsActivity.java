package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TicketDetailsActivity extends AppCompatActivity {
     private TextView tickets;
     private FirebaseAuth mAuth;
     private Button btn_cancel_ticket;
     private DatabaseReference databaseReference;
     private String BusId;
     private String SeatId;
     private int res;
      int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        tickets=(TextView)findViewById(R.id.passenger);
        btn_cancel_ticket=(Button)findViewById(R.id.cancel_ticket);
        mAuth=FirebaseAuth.getInstance();
        String Id=getIntent().getStringExtra("IdOfTrip");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=mAuth.getCurrentUser();
        String  UserId=user.getUid();
        DisplayTicket(UserId, Id);
    }
    private void DisplayTicket(String userId, String id) {
        databaseReference.child("BooKings").child(userId).child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Passengers passengers=dataSnapshot.getValue(Passengers.class);
                            StringBuilder passengerStrBuilder = new StringBuilder();
                            int res=passengers.getRes();
                            if(res==2){
                                String s=passengers.getSeats();
                                String[] seat=s.split(",");
                                int size=passengers.getCount();
                                String n=passengers.getName();
                                String[] name=n.split(",");
                                String a=passengers.getAge();
                                String[]   age=a.split(",");
                                String g=passengers.getGender();
                                String[]  gender=g.split(",");
                                String bId=passengers.getBookingId();
                                String starting=passengers.getStarting();
                                String destination=passengers.getDestination();
                                passengerStrBuilder.append("FROM: "+starting+"      "+"TO: "+destination);
                                passengerStrBuilder.append("\n");
                                passengerStrBuilder.append("TicketId:   "+bId);
                                for(int i=0;i<size;i++){
                                    passengerStrBuilder.append("\n");
                                    passengerStrBuilder.append("Passenger Name"+(i+1)+": "+name[i]+"\n"+"Passenger age: "+age[i]+"\n"
                                            +"gender:  "+gender[i]+"\n"+"seat No:"+seat[i]);
                                    passengerStrBuilder.append("\n");
                                }
                            }else {
                                int size = passengers.getCount();
                                String n = passengers.getName();
                                String[] name = n.split(",");
                                String a = passengers.getAge();
                                String[] age = a.split(",");
                                String g = passengers.getGender();
                                String[] gender = g.split(",");
                                String bId = passengers.getBookingId();
                                String starting = passengers.getStarting();
                                String destination = passengers.getDestination();
                                passengerStrBuilder.append("FROM: " + starting + "      " + "TO: " + destination);
                                passengerStrBuilder.append("\n");
                                passengerStrBuilder.append("TicketId:   " + bId);
                                for (int i = 0; i < size; i++) {
                                    passengerStrBuilder.append("\n");
                                    passengerStrBuilder.append("Passenger Name" + (i + 1) + ": " + name[i] + "\n" + "Passenger age: " + age[i] + "\n"
                                            + "gender:  " + gender[i]);
                                    passengerStrBuilder.append("\n");
                                }
                            }
                            tickets.setText(passengerStrBuilder);
                        }else{
                            Toast.makeText(TicketDetailsActivity.this,
                                    "ticket has been deleted",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
