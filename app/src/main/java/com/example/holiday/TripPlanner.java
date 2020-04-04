package com.example.holiday;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class TripPlanner extends AppCompatActivity implements MediumPickerDialog.MediumDialog {
    private EditText starting;
    private EditText destination;
    private EditText duration;
    private EditText budget;
    private ImageView means;
    private ProgressDialog progressDialog;
    private Button submit;
    private Uri imageUri=null;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
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
        submit=(Button)findViewById(R.id.plan);
        mAuth=FirebaseAuth.getInstance();
        means.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMediumPicker();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
         @Override
        public void onClick(View v) {
        startPlanning();
        Intent intent=new Intent(TripPlanner.this,PlansActivity.class);
            startActivity(intent);
         }
        });
    }
    //adding the trip to the firebase realtime database
    private void startPlanning() {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
            String uid=firebaseUser.getUid();
            String start = starting.getText().toString().trim();
            String dest = destination.getText().toString().trim();
            String journey = duration.getText().toString().trim();
            String money = budget.getText().toString().trim();

            StorageReference filepath = storageReference.child("Transport").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String images = String.valueOf(uri);
                            if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(dest)
                                    && !TextUtils.isEmpty(journey) && !TextUtils.isEmpty(money) && !TextUtils.isEmpty(images)) {
                                String id=databaseReference.push().getKey();
                                TripAdderModel trip = new TripAdderModel(id, start, dest, journey, money, images);
                                databaseReference.child(uid).child(id).setValue(trip);
                            }
                        }
                    });
                }
            });

    }
    public void startMediumPicker() {
        MediumPickerDialog mediumPickerDialog = new MediumPickerDialog();
        mediumPickerDialog.show(getSupportFragmentManager(), "means");
    }

    public void applyPic(int id){
        if (id == 1) {
            means.setImageResource(R.drawable.bus);
            Resources resources = means.getResources();
           imageUri=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(R.drawable.bus) + '/' +
                    resources.getResourceTypeName(R.drawable.bus) + '/' +
                    resources.getResourceEntryName(R.drawable.bus) );
            }
        if (id == 2) {
            means.setImageResource(R.drawable.aircraft);
        Resources resources = means.getResources();
        imageUri=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(R.drawable.aircraft) + '/' +
                resources.getResourceTypeName(R.drawable.aircraft) + '/' +
                resources.getResourceEntryName(R.drawable.aircraft) );

        }
        if(id==3){
            means.setImageResource(R.drawable.train);
            Resources resources = means.getResources();
            imageUri=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(R.drawable.train) + '/' +
                    resources.getResourceTypeName(R.drawable.train) + '/' +
                    resources.getResourceEntryName(R.drawable.train) );
        }
    }
}
