package com.example.carsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    public static final String carIDExtra  = "com.example.carsapp.MESSAGE1";
    public static final String carNameExtra  = "com.example.carsapp.MESSAGE2";
    public static final String carModelExtra  = "com.example.carsapp.MESSAGE3";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);


    }
}
