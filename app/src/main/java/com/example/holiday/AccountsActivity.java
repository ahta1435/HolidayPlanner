package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccountsActivity extends AppCompatActivity {
     private Button logOut;
     private FirebaseAuth mAuth;
     private TextView val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        logOut=(Button)findViewById(R.id.logout_btn);
        mAuth=FirebaseAuth.getInstance();
        BottomNavigationView navigationView=findViewById(R.id.btm_nav);
        Menu menu=navigationView.getMenu();
        MenuItem menuItem=menu.getItem(3);
        menuItem.setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.home){
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                if (id == R.id.explore) {
                    startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
                }
                if (id == R.id.my_plans) {
                    startActivity(new Intent(getApplicationContext(),PlansActivity.class));
                }
              //  if (id == R.id.my_account) {
                  //  startActivity(new Intent(getApplicationContext(),AccountsActivity.class));
                //}
                return false;
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(AccountsActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
