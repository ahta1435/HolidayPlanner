package com.example.holiday;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class UserDetails extends AppCompatActivity {
  public int i=1;
    LinearLayout linearLayout;
    private Button btn,book;
    List<EditText> editTexts=new ArrayList<EditText>();
    List<RadioButton> radioButtons=new ArrayList<RadioButton>();
    List<EditText> editTextsAge=new ArrayList<EditText>();
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        String PlaneId = getIntent().getStringExtra("Document");
        String tripId = getIntent().getStringExtra("TripId");
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String UserId=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("BooKings");
        linearLayout = (LinearLayout) findViewById(R.id.parentLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        btn=(Button)findViewById(R.id.add_passenger);
        book=(Button)findViewById(R.id.booking);
            Passenger();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Passenger();
            }
        });
       book.setOnClickListener(new View.OnClickListener() {
         @Override
           public void onClick(View v) {
             databaseReference.child(UserId).child(tripId).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     if(!dataSnapshot.exists()){
                         Book(UserId,PlaneId,tripId);
                     }
                     else{
                       Toast.makeText(UserDetails.this,"already booked",Toast.LENGTH_LONG).show();
                     }
                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {
                      String error=databaseError.toString();
                     Toast.makeText(UserDetails.this,"Error:"+error,Toast.LENGTH_LONG).show();
                 }
             });

          }
        });
    }
    private void Book(String UserId,String PlaneId,String tripId) {
        String[]  names= new String[(editTexts.size())];
        List<String> passenger=new ArrayList<>();
        String[]  gender=new String[(radioButtons.size())];
        List<String>   genders=new ArrayList<>();
        String[] ages=new String[(editTextsAge.size())];
        List<String> age=new ArrayList<>();
        List<Map<String,Object>> Details= new ArrayList<Map<String,Object>>();
        Map<String, Object> map= new HashMap<String,Object>();
        int count=editTexts.size();
        for(i=0;i<editTexts.size();i++){
            names[i]=editTexts.get(i).getText().toString();
            map.put("name", names[i]);
            gender[i]=radioButtons.get(i).getText().toString();
            map.put("gender",gender[i]);
            ages[i]=editTextsAge.get(i).getText().toString();
            map.put("age",ages[i]);
            Details.add(map);
        }
        if(!Details.isEmpty()) {
            String BookingId = databaseReference.push().getKey();
            AddBooking addBooking = new AddBooking(Details,PlaneId,count);
            databaseReference.child(UserId).child(tripId).setValue(addBooking).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(UserDetails.this,MyBookingsActivity.class);
                                intent.putExtra("TripId",tripId);
                                intent.putExtra("BookingId",BookingId);
                                startActivity(intent);
                            } else {
                                Toast.makeText(UserDetails.this, "Error In Loading", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(UserDetails.this, "Input Field Cannot Be empty", Toast.LENGTH_LONG).show();
        }
    }
    private void Passenger() {
        //adding edit text
            EditText editText = new EditText(this);
            linearLayout.addView(editText);
            editText.setId(i);
            editTexts.add(editText);
            editText.setHint("Passenger name" + editText.getId());
            setEditTextAttributes(editText);
            addLineSeperator();
            //adding radio Buttons
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(radioGroup);
            radioGroup.setId(i);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText("Male ");
            RadioButton radioBtn = new RadioButton(this);
            radioBtn.setText("Female");
            radioGroup.addView(radioButton);
            radioGroup.addView(radioBtn);
            setRadioButtonAttributes(radioButton, radioBtn);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(radioButton.isChecked()){
                       radioButtons.add(radioButton);
                    }else if(radioBtn.isChecked()){
                         radioButtons.add(radioBtn);
                    }
                }
            });
            addLineSeperator();
            //adding another Edit Text
            EditText text = new EditText(this);
            linearLayout.addView(text);
             text.setId(i);
             editTextsAge.add(text);
            text.setHint("Enter Age"+text.getId());
            text.setInputType(InputType.TYPE_CLASS_NUMBER);
            setEditTextAttributesforAge(text);
            addLineSeperator();
            i++;
        }

    private void setEditTextAttributes(EditText editText) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(16),
                0
        );
        editText.setLayoutParams(params);
    }
    private void setEditTextAttributesforAge(EditText editText) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(16),
                0
        );
        editText.setLayoutParams(params);
    }

    private void setRadioButtonAttributes(RadioButton radioButton,RadioButton radioBtn) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );
        radioBtn.setLayoutParams(params);
        radioButton.setLayoutParams(params);
    }
    //This function to convert DPs to pixels
    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    private void addLineSeperator() {
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2);
        params.setMargins(0, convertDpToPixel(10), 0, convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        linearLayout.addView(lineLayout);
    }
}
