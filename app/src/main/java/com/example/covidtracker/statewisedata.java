package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.covidtracker.MainActivity.activeCases;
import static com.example.covidtracker.MainActivity.newInfected;
import static com.example.covidtracker.MainActivity.newRecovered;
import static com.example.covidtracker.MainActivity.recovered;
import static com.example.covidtracker.MainActivity.region;
import static com.example.covidtracker.MainActivity.totalInfected;

public class statewisedata extends AppCompatActivity {
    TextView activeCases1;
    TextView newInfected1;
    TextView recovered1;
    TextView newRecovered1;
    TextView totalInfected1;
    TextView regionname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statewisedata);
        regionname=findViewById(R.id.regionname);
        totalInfected1=findViewById(R.id.totalInfected1);
        newInfected1=findViewById(R.id.newInfected1);
        activeCases1=findViewById(R.id.activeCases1);
        recovered1=findViewById(R.id.recovered1);
        newRecovered1=findViewById(R.id.newRecovered1);
        Intent intent=getIntent();
        int index=intent.getIntExtra("index",-1);

        regionname.setText(region.get(index));
        totalInfected1.setText("Total Infected:" + "      " + totalInfected.get(index));
        newInfected1.setText("New Infected:" + "      " + newInfected.get(index));
        activeCases1.setText("Active Cases:" + "      " + activeCases.get(index));
        recovered1.setText("Recovered:" + "      " + recovered.get(index));
        newRecovered1.setText("New Recovered:" + "      " + newRecovered.get(index));




    }
}