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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class PassengerDetailsForBusActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    private Button btn,book;
    List<EditText> editTexts=new ArrayList<EditText>();
    List<RadioButton> radioButtons=new ArrayList<RadioButton>();
    List<EditText> editTextsAge=new ArrayList<EditText>();
    private FirebaseAuth mAuth;
    private Uri imageUri=null;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private ArrayList<String> items;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details_for_bus);
        String startPoint=getIntent().getStringExtra("startBusPoint");
        String destinationPoint=getIntent().getStringExtra("destinationBusPoint");
        String TripId=getIntent().getStringExtra("TripId");
        String DocId=getIntent().getStringExtra("DocId");
        final int res=getIntent().getIntExtra("id",0);
        Bundle bundle =  getIntent().getExtras();
        items = (ArrayList<String>)bundle.getSerializable("seatList");
        final int size=items.size();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference("Means");
        if(res==2) {
            Resources resources=getResources();
            imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(R.drawable.bus) + '/' +
                    resources.getResourceTypeName(R.drawable.bus) + '/' +
                    resources.getResourceEntryName(R.drawable.bus));
        }
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String UserId=user.getUid();
        StorageReference filepath = storageReference.child("Transport").child(imageUri.getLastPathSegment());
        databaseReference= FirebaseDatabase.getInstance().getReference().child("BooKings");
        databaseReference1=FirebaseDatabase.getInstance().getReference().child("BookedSeats");
        firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference doc=firebaseFirestore.collection("Buses").document(DocId);
        linearLayout = (LinearLayout) findViewById(R.id.parentLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        book=(Button)findViewById(R.id.book_bus_ticket);
        Passenger(size);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(UserId).child(TripId).addValueEventListener(new ValueEventListener() {
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
                                            Book(UserId,DocId,TripId,startPoint,destinationPoint,image,res);
                                        }
                                    });
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error=databaseError.toString();
                        Toast.makeText(PassengerDetailsForBusActivity.this,"Error:"+error,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private void Book(String UserId,String DocId,String TripId,String starting,String destination,String image,int res){
        int count=items.size();
        String[]  names= new String[count];
        String[]  genders=new String[count];
        String[] ages=new String[count];
        StringBuilder name = new StringBuilder();
        StringBuilder age = new StringBuilder();
        StringBuilder gender = new StringBuilder();
        StringBuilder seat=new StringBuilder();
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        List<String> list3=new ArrayList<>();
        for(int i=0;i<count;i++){
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
        for(String s:items){
            seat.append(s).append(",");
        }
        String seatBooked=seat.toString();
        String passengerNames=name.toString();
        String passengerAges=age.toString();
        String passengerGender=gender.toString();
        if(!TextUtils.isEmpty(passengerNames)&&!TextUtils.isEmpty(passengerAges)&&!TextUtils.isEmpty(passengerGender)) {
            String SeatId=databaseReference1.push().getKey();
            BookedSeats bookedSeats=new BookedSeats(items);
            String BookingId = databaseReference.push().getKey();
            AddBusBooking addBooking = new AddBusBooking(passengerNames,BookingId,passengerAges,DocId
                    ,count,passengerGender,starting,destination
                    ,image,seatBooked,res,SeatId);
            databaseReference.child(UserId).child(TripId).setValue(addBooking).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                databaseReference1.child(DocId).child(SeatId).setValue(bookedSeats);
                                Toast.makeText(PassengerDetailsForBusActivity.this, "BOOKING DONE SUCCESSFULLY", Toast.LENGTH_LONG).show();
                            } else {
                                String error=task.getException().getMessage();
                                Toast.makeText(PassengerDetailsForBusActivity.this, "Error:"+error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(PassengerDetailsForBusActivity.this, "Input Field Cannot Be empty", Toast.LENGTH_LONG).show();
        }
    }
    private void Passenger(int size) {
        //adding edit text
        for(int i=0;i<size;i++) {
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
                    if (radioButton.isChecked()) {
                        radioButtons.add(radioButton);
                    } else if (radioBtn.isChecked()) {
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
            text.setHint("Enter Age" + text.getId());
            text.setInputType(InputType.TYPE_CLASS_NUMBER);
            setEditTextAttributesforAge(text);
            addLineSeperator();
        }
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
