package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
   private Button btn;
   private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigationView=findViewById(R.id.btm_nav);
        Menu menu=navigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.explore) {
                    startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
                }
                if (id == R.id.my_plans) {
                    startActivity(new Intent(getApplicationContext(),PlansActivity.class));
                }
                if (id == R.id.my_account) {
                    startActivity(new Intent(getApplicationContext(),AccountsActivity.class));
                }
                return false;
            }
        });
        btn=(Button) findViewById(R.id.plan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTripPlanner();
            }
        });
    }
    public void openTripPlanner(){
        Intent intent=new Intent(this,TripPlanner.class);
        startActivity(intent);
    }
}
