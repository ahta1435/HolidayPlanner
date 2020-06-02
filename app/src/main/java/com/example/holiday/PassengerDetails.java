package com.example.holiday;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class PassengerDetails extends AppCompatActivity {
  public int i=1;
    LinearLayout linearLayout;
    private Button btn,book;
    List<EditText> editTexts=new ArrayList<EditText>();
    List<RadioButton> radioButtons=new ArrayList<RadioButton>();
    List<EditText> editTextsAge=new ArrayList<EditText>();
    private FirebaseAuth mAuth;
    private Uri imageUri=null;
    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        String PlaneId = getIntent().getStringExtra("Document");
        storageReference = FirebaseStorage.getInstance().getReference().child("Means");
        String tripId = getIntent().getStringExtra("TripId");
       final int  res=getIntent().getIntExtra("id",0);
        if(res==1) {
            Resources resources=getResources();
            imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(R.drawable.aircraft) + '/' +
                    resources.getResourceTypeName(R.drawable.aircraft) + '/' +
                    resources.getResourceEntryName(R.drawable.aircraft));
        }
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String UserId=user.getUid();
        StorageReference filepath = storageReference.child("Transport").child(imageUri.getLastPathSegment());
        databaseReference= FirebaseDatabase.getInstance().getReference().child("BooKings");
        firebaseFirestore=FirebaseFirestore.getInstance();
       DocumentReference doc=firebaseFirestore.collection("Planes").document(PlaneId);
        linearLayout = (LinearLayout) findViewById(R.id.parentLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        btn=(Button)findViewById(R.id.add_passenger);
        book=(Button)findViewById(R.id.book_plane_ticket);
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
             databaseReference.child(UserId).child(tripId).
                     addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             if(!dataSnapshot.exists()){
                                 filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                     @Override
                                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                         filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                             @Override
                                             public void onSuccess(Uri uri) {
                                                 String image=String.valueOf(uri);
                                                 doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                     @Override
                                                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                         String starting=documentSnapshot.getString("starting");
                                                         String destination=documentSnapshot.getString("destination");
                                                         Book(UserId,PlaneId,tripId,starting,destination,image,res);
                                                     }
                                                 });
                                             }
                                         });
                                     }
                                 });
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {
                             String error=databaseError.toString();
                             Toast.makeText(PassengerDetails.this,"Error:"+error,Toast.LENGTH_LONG).show();
                         }
                     });
             }
        });
    }
    private void Book(String UserId,String PlaneId,String tripId,String starting,String destination,String image,int res) {
        String[]  names= new String[(editTexts.size())];
        String[]  genders=new String[(radioButtons.size())];
        String[] ages=new String[(editTextsAge.size())];
        StringBuilder name = new StringBuilder();
        StringBuilder age = new StringBuilder();
        StringBuilder gender = new StringBuilder();
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        List<String> list3=new ArrayList<>();
        int count=editTexts.size();
        for(i=0;i<editTexts.size();i++){
            names[i]=editTexts.get(i).getText().toString();
            list1.add(names[i]);
            genders[i]=radioButtons.get(i).getText().toString();
            list2.add(genders[i]);
            ages[i]=editTextsAge.get(i).getText().toString();
           list3.add(ages[i]);
        }
        for(String st:list1) {
            name.append(st).append(",");
        }
        for(String g:list2) {
            gender.append(g).append(",");
        }
        for(String a:list3) {
            age.append(a).append(",");
        }
        String passengerNames=name.toString();
        String passengerAges=age.toString();
        String passengerGender=gender.toString();
       // String start=starting;
        //String dest=destination.toString();
        if(!TextUtils.isEmpty(passengerNames)&&!TextUtils.isEmpty(passengerAges)&&!TextUtils.isEmpty(passengerGender)) {
            String BookingId = databaseReference.push().getKey();
            AddPlaneBooking addBooking = new AddPlaneBooking(passengerNames,BookingId,passengerAges,PlaneId,passengerGender,count
                    ,starting,destination,image,res);
            databaseReference.child(UserId).child(tripId).setValue(addBooking).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PassengerDetails.this, "BOOKING DONE SUCCESSFULLY", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                   String error=task.getException().getMessage();
                                Toast.makeText(PassengerDetails.this, "Error:"+error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(PassengerDetails.this, "Input Field Cannot Be empty", Toast.LENGTH_LONG).show();
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

