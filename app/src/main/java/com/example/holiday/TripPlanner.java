package com.example.holiday;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TripPlanner extends AppCompatActivity implements MediumPickerDialog.MediumDialog {
    private EditText starting;
    private EditText destination;
    private EditText duration;
    private EditText budget;
    private ImageView means;
    private ProgressDialog progressDialog;
    private Button submit;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private static final int CHOOSE_MEDIUM_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planner);
        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference().child("Means");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("YourTrip");
        starting = (EditText) findViewById(R.id.starting_of_trip);
        duration = (EditText) findViewById(R.id.duration_of_trip);
        destination = (EditText) findViewById(R.id.destination_of_trip);
        budget = (EditText) findViewById(R.id.budget_of_trip);
        means = (ImageView) findViewById(R.id.medium);
        means.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMediumPicker();
            }
        });

        // submit=(Button)findViewById(R.id.plan);
        //submit.setOnClickListener(new View.OnClickListener() {
        // @Override
        //  public void onClick(View v) {
        //startPlanning();
        //}
        //});
    }

    /*  private void startPlanning() {
           final  String start=starting.getText().toString().trim();
           final  String dest=destination.getText().toString().trim();
           final String durationOfTrip=duration.getText().toString().trim();
           final String budgetOfTrip=budget.getText().toString().trim();

             if(!TextUtils.isEmpty(start)&&!TextUtils.isEmpty(dest)&&!TextUtils.isEmpty(durationOfTrip)&&!TextUtils.isEmpty(budgetOfTrip)){
                 progressDialog.setMessage("adding Trip...");
                 progressDialog.show();
                    String id =databaseReference.push().getKey();
                    TripAdderModel tripAdderModel=new TripAdderModel(id,start,dest,durationOfTrip,budgetOfTrip,);
                    databaseReference.child(id).setValue(tripAdderModel);
                    progressDialog.dismiss();
             }

       }*/
    public void startMediumPicker() {
        MediumPickerDialog mediumPickerDialog = new MediumPickerDialog();
        mediumPickerDialog.show(getSupportFragmentManager(), "means");
    }

    @Override
    public void applyText(String image) {
        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
        means.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
    }
}
