package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {
    PlacesClient placesClient;
    List<Place.Field> placeFields=Arrays.asList(Place.Field.ID,
                                          Place.Field.NAME,
                                             Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        BottomNavigationView navigationView=findViewById(R.id.btm_nav);
       initPlaces();
       setUpPlaceAutocomplete();
        Menu menu=navigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.home){
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
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

    }

    private void setUpPlaceAutocomplete() {
        places_fragment=(AutocompleteSupportFragment)getSupportFragmentManager()
                                      .findFragmentById(R.id.places_autocomplete_fragment);
                   places_fragment.setPlaceFields(placeFields);
                   places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                       @Override
                       public void onPlaceSelected(@NonNull Place place) {
                           Toast.makeText(ExploreActivity.this,""+place.getName(),Toast.LENGTH_SHORT).show();
                       }
                       @Override
                       public void onError(@NonNull Status status) {
                           Toast.makeText(ExploreActivity.this,""+status.getStatusMessage(),Toast.LENGTH_SHORT).show();
                       }
                   });

    }
    private void initPlaces() {
        Places.initialize(this,getString(R.string.places_api_key));
        placesClient=Places.createClient(this);
    }
}
