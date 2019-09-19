package com.example.carsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView make_model, price, carDetails, lastUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        Intent intent = getIntent();

        String carID = intent.getStringExtra(MainActivity.carIDExtra);
        String carMake = intent.getStringExtra(MainActivity.carNameExtra);
        String carModel = intent.getStringExtra(MainActivity.carModelExtra);

        make_model = findViewById(R.id.make_model);
        price = findViewById(R.id.price);
        carDetails = findViewById(R.id.carDescription);



        make_model.setText(carMake + " - " + carModel);


        new GetCarDetails(this, carID).execute();



    }
}
