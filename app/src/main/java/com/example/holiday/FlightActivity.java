package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FlightActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
   private TextView  starting,destination,Doj, journey_from,journey_to;
   private Button btn_plane_search;
   private DatabaseReference databaseReference;
   private FirebaseAuth mAuth;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE2 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String userId=user.getUid(); //userId
        databaseReference= FirebaseDatabase.getInstance().getReference();
        String TripId=getIntent().getStringExtra("TripId");//trip id
        final int res= getIntent().getIntExtra("id",0);
        starting=(TextView)findViewById(R.id.starting);
        destination=(TextView)findViewById(R.id.destination);
        journey_from = (TextView) findViewById(R.id.starting_airport);
        journey_to = (TextView) findViewById(R.id.destination_bustand);
        Doj=(TextView)findViewById(R.id.date_picker);
        btn_plane_search=(Button)findViewById(R.id.Find_plane);

        databaseReference        //Reading the value of
                .child("YourTrip")   //location which we have
                .child(userId)            //filled during the
                .child(TripId).           //planning of the trip
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   TripAddress tripAddress=dataSnapshot.getValue(TripAddress.class);
                   starting.setText(tripAddress.getStarting().toString());
                   destination.setText("To  "+tripAddress.getDestination().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               String error=databaseError.toString();
                Toast.makeText(FlightActivity.this,"Error:"+error,Toast.LENGTH_LONG).show();
            }
        });
      journey_from.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(FlightActivity.this,SearchActivity.class);
              startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

          }
      });
        journey_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FlightActivity.this,SearchActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE2);

            }
        });
       Doj.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDatePicker();
           }
       });
       btn_plane_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String startAirport=journey_from.getText().toString();
               final String destinationAirport=journey_to.getText().toString();
               final String dateOfJourney=Doj.getText().toString();
               if(!TextUtils.isEmpty(startAirport)&&!TextUtils.isEmpty(destinationAirport)&&!TextUtils.isEmpty(dateOfJourney)) {
                   Intent intent = new Intent(FlightActivity.this, PlaneRequestActivity.class);
                   intent.putExtra("TripId",TripId);
                   intent.putExtra("startAirport",startAirport);
                   intent.putExtra("id",res);
                   intent.putExtra("destinationAirport",destinationAirport);
                   intent.putExtra("dateOfJourney",dateOfJourney);
                   startActivity(intent);
               }else{
                   Toast.makeText(FlightActivity.this,"Fields Can't be left empty",Toast.LENGTH_LONG).show();
               }
           }
       });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                 this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("keyName");
                journey_from.setText(returnString);
            }
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("keyName");
                journey_to.setText(returnString);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       String date=dayOfMonth+"/"+month+"/"+year;
       Doj.setText(date);
    }
}
