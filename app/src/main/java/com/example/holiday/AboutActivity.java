package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AboutActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private TextView textView,placeName,thingsToCarry,KnownFor;
    private ImageView placeImage;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String placeId = getIntent().getStringExtra("placeId");
        firebaseFirestore = FirebaseFirestore.getInstance();
        textView = (TextView) findViewById(R.id.description);
        placeName= (TextView) findViewById(R.id.place_name);
        placeImage=(ImageView)findViewById(R.id.place_image);
        thingsToCarry=(TextView) findViewById(R.id.place_things_to_carry);
        KnownFor=(TextView)findViewById(R.id.place_known);
        btn = (Button) findViewById(R.id.plan_journey);
        DocumentReference docRef= firebaseFirestore.collection("places").document(placeId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                     String imageUrl=documentSnapshot.getString("Images");
                      Glide.with(placeImage).load(imageUrl).into(placeImage);
                      String place_name=documentSnapshot.getString("Name");
                      placeName.setText("About "+place_name);
                     String desc=documentSnapshot.getString("description");
                     textView.setText(desc);
                     String knownfor=documentSnapshot.getString("known");
                     String[] str=knownfor.split(",");
                     StringBuilder stringBuilder=new StringBuilder();
                     for(int i=0;i<str.length;i++){
                         stringBuilder.append(str[i]+"\n\n");
                     }
                     KnownFor.setText(stringBuilder);
                String things=documentSnapshot.getString("Things");
                String[] str1=things.split(",");
                StringBuilder stringBuilder1=new StringBuilder();
                for(int i=0;i<str1.length;i++){
                    stringBuilder1.append(str1[i]+"\n\n");
                }
                thingsToCarry.setText(stringBuilder1);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AboutActivity.this,TripPlanner.class);
                startActivity(intent);
            }
        });
    }
}
