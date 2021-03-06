package com.example.stilla_app.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stilla_app.Data.Model.Singeltons.CalculatedTrip;
import com.example.stilla_app.Data.Model.TripRelated.Forecast;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.R;
import com.example.stilla_app.View.Adapters.RecyclerViewAdaper_trip_forecast;
import java.util.ArrayList;
import java.util.List;

public class TripForecastActivity extends AppCompatActivity {

    private MethodsAPI mMethodsAPI = new MethodsAPI();

    private TextView name;
    private TextView start;
    private TextView finish;
    private TextView transportation;
    private TextView destinations;
    private TextView notifications;
    private Button viewMapButton;

    private ArrayList<String> mTime = new ArrayList<>();
    private ArrayList<String> mF = new ArrayList<>();
    private ArrayList<String> mD= new ArrayList<>();
    private ArrayList<String> mT = new ArrayList<>();
    private ArrayList<String> mW = new ArrayList<>();
    private ArrayList<String> mV = new ArrayList<>();
    private ArrayList<String> mN = new ArrayList<>();
    private ArrayList<String> mTD = new ArrayList<>();
    private ArrayList<String> mR = new ArrayList<>();

    private ArrayList<String> mStationEndpointName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_forecast);

        name = findViewById(R.id.name);
        start = findViewById(R.id.start);
        finish = findViewById(R.id.end);
        transportation = findViewById(R.id.transport);
        destinations = findViewById(R.id.destinations);
        notifications = findViewById(R.id.notifications);
        viewMapButton = findViewById(R.id.view_on_map);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ferðin þín");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_stilla_logo_and_name_round);
        actionBar.setDisplayUseLogoEnabled(true);

        Trip clickedTrip = CalculatedTrip.get().getTrip();
        System.out.println("Nafn ferðarinnar sem ýtt var á: " + clickedTrip.getName());

        name.setText(clickedTrip.getName());
        start.setText(clickedTrip.getStart());
        finish.setText(clickedTrip.getFinish());
        transportation.setText(listToText(clickedTrip.getTransport()));
        destinations.setText(listToText(clickedTrip.getPlaces()));
        notifications.setText(booleanToText(clickedTrip.isNotify()));
        viewMapButton.setVisibility(View.VISIBLE);

        initListItems(clickedTrip.weatherForecast);

        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripForecastActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListItems(List<Forecast> weatherForecasts) {
        int n = weatherForecasts.size();

        if (n==0){
            Toast.makeText(getApplicationContext(),"Því miður tókst ekki að reikna veðurspá fyrir gefna ferðaáætlun",Toast.LENGTH_LONG).show();
            viewMapButton.setVisibility(View.INVISIBLE);
        }

        mTime.clear();
        mF.clear();
        mD.clear();
        mT.clear();
        mW.clear();
        mV.clear();
        mN.clear();
        mTD.clear();
        mR.clear();
        mStationEndpointName.clear();

        for (int i=0; i<n; i++) {
            mTime.add(weatherForecasts.get(i).getFtime());
            mF.add(Integer.toString(weatherForecasts.get(i).getF()));
            mD.add(weatherForecasts.get(i).getD());
            mT.add(Integer.toString(weatherForecasts.get(i).getT()));
            mW.add(weatherForecasts.get(i).getW());
            mV.add(weatherForecasts.get(i).getV());
            mN.add(weatherForecasts.get(i).getN());
            mTD.add(weatherForecasts.get(i).getTD());
            mR.add(weatherForecasts.get(i).getR());
            mStationEndpointName.add(weatherForecasts.get(i).getStationEndpointName());
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_trip_forecast);
        RecyclerViewAdaper_trip_forecast adapter = new RecyclerViewAdaper_trip_forecast(this, mTime,mF, mT, mD, mW,mN,mR,mTD,mStationEndpointName);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String booleanToText(boolean notify) {
        if(notify == false) {
            return "Þú færð ekki tilkynningar";
        } else {
            return "Þú færð tilkynningar";
        }
    }

    private String listToText(List<String> list) {
        StringBuilder text = new StringBuilder();
        for (int i=0; i<list.size(); i++) {
            text.append(list.get(i));
            if (i != list.size()-1) text.append(", ");
        }
        return text.toString();
    }
}
