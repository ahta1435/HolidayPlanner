package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatSelectorActivity extends AppCompatActivity {
   private List<String>  seats=new ArrayList<>();
   private int count=0;
   private TextView text,text1;
   private Button btn;
   private DatabaseReference databaseReference;
   private List<String> BookedSeats=new ArrayList<>();
   private List<String> s=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selector);
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        String startPoint=getIntent().getStringExtra("startBusPoint");
        String destinationPoint=getIntent().getStringExtra("destinationBusPoint");
        String TripId=getIntent().getStringExtra("TripId");
        String DocId=getIntent().getStringExtra("DocId");
        final int res=getIntent().getIntExtra("id",0);
         databaseReference= FirebaseDatabase.getInstance().getReference();
        text=(TextView)findViewById(R.id.seat_count);
         btn=(Button)findViewById(R.id.checkOut);
         text1=(TextView)findViewById(R.id.seat_remaining);
         final int childCount = grid.getChildCount();
        for (int i= 0; i < childCount; i++){
            ImageView container = (ImageView) grid.getChildAt(i);
            container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                container.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                String idString=view.getResources().getResourceEntryName(container.getId());
                                  int  found=check(idString,container);
                                    if(found==0) {
                                        if (!seats.isEmpty()) {
                                            int size = seats.size();
                                            int flg = 0;
                                            for (int k = 0; k < size; k++) {
                                                for (int j = k; j <= k; j++) {
                                                    if (seats.get(j).equals(idString)) {
                                                        flg = 1;
                                                        break;
                                                    }
                                                }
                                                if (flg == 1) {
                                                    Toast.makeText(SeatSelectorActivity.this, "Already Selected", Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }
                                            if (flg != 1) {
                                                seats.add(idString);
                                            } else {
                                                Toast.makeText(SeatSelectorActivity.this, "Already Selected", Toast.LENGTH_SHORT).show();
                                            }
                                            text.setText("No. of seats selected: "+seats.size());
                                        } else {
                                            seats.add(idString);
                                            text.setText("No. of seats selected: "+seats.size());
                                        }
                                    }else{
                                        Toast.makeText(SeatSelectorActivity.this, "Seat is reserved", Toast.LENGTH_SHORT).show();
                                    }
                                }
            });

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SeatSelectorActivity.this,PassengerDetailsForBusActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("seatList", (Serializable) seats);
                intent.putExtras(bundle);
                intent.putExtra("startBusPoint",startPoint);
                intent.putExtra("destinationBusPoint",destinationPoint);
                intent.putExtra("TripId",TripId);
                intent.putExtra("DocId",DocId);
                intent.putExtra("id",res);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        String DocId=getIntent().getStringExtra("DocId");
        GridLayout grid=(GridLayout)findViewById(R.id.grid);
        final int childCount=grid.getChildCount();
        databaseReference.child("BookedSeats").child(DocId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            BookedSeats.clear();
                            for (DataSnapshot s : dataSnapshot.getChildren()) {
                                BookedSeats bookedSeats = s.getValue(BookedSeats.class);
                                BookedSeats.addAll(bookedSeats.getSeat());
                                for(int j=0;j<childCount;j++){
                                    ImageView imageView=(ImageView)grid.getChildAt(j);
                                    String id=imageView.getResources().getResourceEntryName(imageView.getId());
                                    int size=BookedSeats.size();
                                    text1.setText("Available Seats:"+(27-size));
                                    for(int k=0;k<size;k++){
                                        String id1=BookedSeats.get(k);
                                        if(id1.equals(id)) {
                                            imageView.setBackgroundColor(getResources().getColor(R.color.AlreadyBookedSeat));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
    private int  check(String idString,ImageView container) {
        int size=BookedSeats.size();
        int found=0;
        for(int i=0;i<size;i++){
              if(BookedSeats.get(i).equals(idString)){
                  container.setBackgroundColor(getResources().getColor(R.color.AlreadyBookedSeat));
                  found=1;
                  break;
              }
        }
        if(found==1){
            return 1;
        }
            return 0;
    }
}
