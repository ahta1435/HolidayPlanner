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

public class BusActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private TextView starting_stand,starting,destination;
    private TextView destination_stand;
    private TextView doj;
    private Button search;
    private static final int Bus_ACTIVITY_REQUEST_CODE = 0;
    private static final int Bus_ACTIVITY_REQUEST_CODE2 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        String tripId=getIntent().getStringExtra("TripId");
        final int res=getIntent().getIntExtra("id",0);
        final int t=1;
        mAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=mAuth.getCurrentUser();
        String UserId=user.getUid();
       starting_stand=(TextView)findViewById(R.id.starting_BusStand);
       destination_stand=(TextView)findViewById(R.id.destination_bustand);
       starting=(TextView)findViewById(R.id.starting);
       destination=(TextView)findViewById(R.id.destination);
       doj=(TextView)findViewById(R.id.date_picker);
       search=(Button)findViewById(R.id.find_bus);
        doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        databaseReference        //Reading the value of
                .child("YourTrip")   //location which we have
                .child(UserId)            //filled during the
                .child(tripId).           //planning of the trip
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
                Toast.makeText(BusActivity.this,"Error:"+error,Toast.LENGTH_LONG).show();
            }
        });
        starting_stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BusActivity.this,SearchActivity.class);
                intent.putExtra("bus",t);
                startActivityForResult(intent,Bus_ACTIVITY_REQUEST_CODE);
            }
        });
        destination_stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BusActivity.this,SearchActivity.class);
                intent.putExtra("bus",t);
                startActivityForResult(intent,Bus_ACTIVITY_REQUEST_CODE2);
            }
        });
       search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String startBusStand=starting_stand.getText().toString();
                final String destinationBusStand=destination_stand.getText().toString();
                final String dateOfJourney=doj.getText().toString();
                if(!TextUtils.isEmpty(startBusStand)&&!TextUtils.isEmpty(destinationBusStand)&&!TextUtils.isEmpty(dateOfJourney)) {
                    Intent intent = new Intent(BusActivity.this, BusRequestActivity.class);
                    intent.putExtra("TripId",tripId);
                    intent.putExtra("startBusPoint",startBusStand);
                    intent.putExtra("id",res);
                    intent.putExtra("destinationBusPoint",destinationBusStand);
                    intent.putExtra("dateOfJourney",dateOfJourney);
                    finish();
                    startActivity(intent);

                }else{
                    Toast.makeText(BusActivity.this,"Fields Can't be left empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Bus_ACTIVITY_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                String result=data.getStringExtra("keyName");
                starting_stand.setText(result);
            }
        }
        if(requestCode==Bus_ACTIVITY_REQUEST_CODE2){
            if(resultCode==RESULT_OK){
                String result=data.getStringExtra("keyName");
                destination_stand.setText(result);
            }
        }
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date=dayOfMonth+"/"+month+"/"+year;
        doj.setText(date);
    }
}
