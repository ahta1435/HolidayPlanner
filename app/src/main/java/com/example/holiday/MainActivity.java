package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView=findViewById(R.id.btm_nav);
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
                if (id == R.id.my_account) {
                    startActivity(new Intent(getApplicationContext(),AccountsActivity.class));
                }
                return true;
            }
        });
    }
}
