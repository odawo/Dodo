package com.vanessaodawo.r_client;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanessaodawo.r_client.Fragments.Login;
import com.vanessaodawo.r_client.Fragments.Rate_D;
import com.vanessaodawo.r_client.Fragments.Ride_Data;
import com.vanessaodawo.r_client.POJO.Rides_Trips;

import java.util.List;

public class Rides extends AppCompatActivity {

    RecyclerView recycle;
    List<Rides_Trips> list;
    DatabaseReference myRef, myRef2;
    FirebaseDatabase database;

    Button rate, rideInfo;

//    String TAG, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        recycle = findViewById(R.id.recyclerView);
        recycle.setHasFixedSize(true);

        rate = findViewById(R.id.goRate);
        rideInfo = findViewById(R.id.checkRide);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("RiderRides");


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate.setVisibility(View.INVISIBLE);
                rideInfo.setVisibility(View.INVISIBLE);
                loadFragment(new Rate_D());
            }
        });

        rideInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate.setVisibility(View.INVISIBLE);
                rideInfo.setVisibility(View.INVISIBLE);
                Toast.makeText(Rides.this, "Still under minor works", Toast.LENGTH_SHORT).show();
//                loadFragment(new Ride_Data());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rides_card_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                loadFragment(new Rate_D());
                break;
             default:
                 return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Rate_D ratings) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fLayoutR, new Rate_D());
        ft.commit();
    }

    private void loadFragment(Ride_Data ride) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fLayoutR, new Ride_Data());
        ft.commit();
    }
}
