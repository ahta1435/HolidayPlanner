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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectorActivity extends AppCompatActivity {
   private List<String>  seats=new ArrayList<>();
   private int count=0;
   private TextView text;
   private Button btn;
   private DatabaseReference databaseReference;
   private List<String> BookedSeats=new ArrayList<>();
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
       final int childCount = grid.getChildCount();
        databaseReference.child("BookedSeats").child(DocId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BookedSeats.clear();
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot s : dataSnapshot.getChildren()) {
                              BookedSeats bookedSeats = s.getValue(BookedSeats.class);
                                   BookedSeats.addAll(bookedSeats.getSeat());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
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
                                            int size2 = BookedSeats.size();
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
                                        } else {
                                            seats.add(idString);
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
                  String id=databaseReference.push().getKey();
                BookedSeats bookedSeats=new BookedSeats(seats);
                databaseReference.child("BookedSeats").child(DocId).child(id).setValue(bookedSeats);
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
            }
        });


    }

    private int  check(String idString,ImageView container) {
        int size=BookedSeats.size();
        int found=0;
        for(int i=0;i<size;i++){
              if(BookedSeats.get(i).equals(idString)){
                  container.setBackgroundColor(getResources().getColor(R.color.Blue));
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
