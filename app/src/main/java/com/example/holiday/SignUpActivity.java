package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class SignUpActivity extends AppCompatActivity {
    private EditText regUser;
    private EditText regPass;
    private EditText regConfirmPass;
    private DatabaseReference databaseReference;
    private Button register;
    private FirebaseAuth mAuth;
    private TextView  backToLogin;
    private EditText userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        regUser=(EditText)findViewById(R.id.new_user_id);
        regPass=(EditText)findViewById(R.id.new_user_password);
        regConfirmPass=(EditText)findViewById(R.id.confirm_password);
        register=(Button)findViewById(R.id.create_btn);
        backToLogin=(TextView)findViewById(R.id.LoginPage_btn);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
        mAuth=FirebaseAuth.getInstance();
        userName=(EditText)findViewById(R.id.new_user_name);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=regUser.getText().toString();
                String pass=regPass.getText().toString();
                String confirmPass=regConfirmPass.getText().toString();
                String User=userName.getText().toString();
                if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(confirmPass)){
                    if(pass.equals(confirmPass)){
                       mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()){
                                      FirebaseUser firebaseUser=mAuth.getCurrentUser();
                                        String userId=firebaseUser.getUid();
                                      UserAdderModel userAdderModel=new UserAdderModel(userId,User,user);
                                      databaseReference.child(userId).setValue(userAdderModel);
                                      sendToMain();
                                  }
                                  else{
                                      String error=task.getException().getMessage();
                                      Toast.makeText(SignUpActivity.this,"Error:"+error,Toast.LENGTH_LONG).show();
                                  }
                           }
                       });
                    }else{
                        Toast.makeText(SignUpActivity.this,"Error:Passwrd Field didn't match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            sendToMain();
        }
    }
    private void sendToMain() {
        Intent intent=new Intent(SignUpActivity.this,
                HomeActivity.class);
        startActivity(intent);
    }
}
