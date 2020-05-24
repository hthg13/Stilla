package com.example.stilla_app.View.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.example.stilla_app.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OptionActivity extends AppCompatActivity {

    private StillaAPI stillaAPI;
    private MethodsAPI mMethodsAPI = new MethodsAPI();

    private List<WeatherStation> allStations = new ArrayList<>();
    private List<Trip> allTrips = new ArrayList<>();

    private Button mButtonNewTrip;
    private Button mButtonAllTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Hvað villt þú gera í dag?");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_stilla_logo_and_name_round);
        actionBar.setDisplayUseLogoEnabled(true);

        allTrips = mMethodsAPI.getTripList();

        mButtonAllTrips = findViewById(R.id.button_all_trips);
        mButtonNewTrip = findViewById(R.id.button_new_trip);

        mButtonAllTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("allTrips", new ArrayList<>(allTrips));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
