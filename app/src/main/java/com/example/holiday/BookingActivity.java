package com.example.holiday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class BookingActivity extends AppCompatActivity {
   private ImageView plane;
   private ImageView train;
   private ImageView bus;
   private TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        String tripId= getIntent().getStringExtra("TripId");
        plane=(ImageView)findViewById(R.id.plane);
        train=(ImageView)findViewById(R.id.train);
        bus=(ImageView)findViewById(R.id.bus);
        final int pl=1;
        final int bu=2;
        final int tr=3;
        plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookingActivity.this,FlightActivity.class);
                intent.putExtra("TripId",tripId);
                intent.putExtra("id",pl);
                finish();
                startActivity(intent);
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookingActivity.this,TrainActivity.class);
                intent.putExtra("TripId",tripId);
                finish();
                startActivity(intent);
            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookingActivity.this,BusActivity.class);
                intent.putExtra("TripId",tripId);
                intent.putExtra("id",bu);
                finish();
                startActivity(intent);
            }
        });
    }
}
