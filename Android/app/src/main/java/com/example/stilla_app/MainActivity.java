package com.example.stilla_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stilla_app.Data.Model.TripRelated.Forecast;
import com.example.stilla_app.Data.Model.TripRelated.Forecasts;
import com.example.stilla_app.Data.Model.TripRelated.TextForecast;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.example.stilla_app.Data.Model.TripRelated.WeatherStation.getAllStationLatLng;

public class MainActivity extends AppCompatActivity {

    // parameters that must be passed to the getWeatherForStationId method
    private StillaAPI stillaAPI;
    private StillaAPI vedurstofanAPI;
    private List<WeatherStation> allStations = new ArrayList<>();
    private List<Forecast> mForecasts = new ArrayList<>();
    private List<Trip> mTripList = new ArrayList<>();
    private TextForecast mTextForecast;

    private String OP_W = "xml";
    private String TYPE = "forec";
    private String LANG = "is";
    private String VIEW = "xml";
    // parameters that are allowed to change
    private int StationId = 1;
    private String params = "F;D;T;W;V;N;TD;R";

    // parameters to create fake data //todo remove
    private Trip trip = new Trip();
    private String tripName = "ferðin mín";
    private List<String> transport = new ArrayList<>();
    private List<String> places = new ArrayList<>();
    private String start = "today";
    private String finish = "tomorrow";
    private boolean notify = true;
    private List<WeatherStation> weatherStations = new ArrayList<>();
    private List<Forecast> weatherForecast = new ArrayList<>();

    private Button mbutton;

    /*
    1. Búa til nýja ferð
        a. sækja upplýsingar til notanda
        b. búa til upplýsingar út frá a.
            - finna allar veðurstöðvar milli staðanna sem að notandi skilgreindi
            - veðurspánna fyrir allar veðurstöðvarnar
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbutton = (Button) findViewById(R.id.button);

        stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class); //do not remove
        getAllWeatherStations(); // initialize the allstations private variable list
        getAllTrips();

        stillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class); //do not remove
        getForecasts();
        getTextForecast();

        stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(allStations.get(0).getName());
                System.out.println(mForecasts.get(0).getFtime());
                System.out.println(mTripList.get(0).getName());
                System.out.println(mTextForecast.getTextNextDays().getDescription().getDescription());


                //initializing fake data // todo remove
                transport.add(0,"driving");
                transport.add(1, "walking");

                places.add(0, "Reykjavík");
                places.add(1, "Akureyri");
                places.add(2, "Höfn");

                weatherStations.add(allStations.get(0));
                weatherStations.add(allStations.get(1));

                weatherForecast.add(mForecasts.get(1));

                trip.setFinish(finish);
                trip.setStart(start);
                trip.setName(tripName);
                trip.setNotify(notify);
                trip.setPlaces(places);
                trip.setTransport(transport);
                trip.setWeatherForecasts(weatherForecast);
                trip.setWeatherStations(weatherStations);

                setNewTrip(trip);

                //TODO: gera sama fyrir veðurstöðvar og fyrir weatherforecast sem virkar núna

                getAllTrips();
                System.out.println(mTripList); //virkar! fæ allt rétt til baka :) :) :)

                System.out.println(allStations.get(0).getLatLng());
                List<LatLng> latLngList = mTripList.get(2).getAllStationCoordinates();
                System.out.println(latLngList);
                List<LatLng> allStationsLatLng = getAllStationLatLng(allStations);
                System.out.println(allStationsLatLng);


                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("allStations", new ArrayList<WeatherStation>(allStations));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void setNewTrip(Trip trip) {
        Call<Trip> call = stillaAPI.saveTrip(trip);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                System.out.println("success" + response.body());
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                System.out.println("failure: " + t);
            }
        });
    }

    private void getAllTrips() {
        Call<List<Trip>> call = stillaAPI.getAllTrips();
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                mTripList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                System.out.println("failure: " + t + call);
            }
        });
    }

    private void getAllWeatherStations() {
        Call<List<WeatherStation>> call = stillaAPI.getAllStations();
        call.enqueue(new Callback<List<WeatherStation>>() {
            @Override
            public void onResponse(Call<List<WeatherStation>> call, Response<List<WeatherStation>> response) {
                if(response.isSuccessful()) {
                    allStations.addAll(response.body());
                    System.out.println("found all weather stations");
                } else {
                    System.out.println("found no weather stations");
                }
            }

            @Override
            public void onFailure(Call<List<WeatherStation>> call, Throwable t) {
                System.out.println("failure for weatherstations " + t);
            }
        });

    }

    private void getTextForecast() {
        Call<TextForecast> call = stillaAPI.getWeatherTextNextDays();
        call.enqueue(new Callback<TextForecast>() {
            @Override
            public void onResponse(Call<TextForecast> call, Response<TextForecast> response) {
                mTextForecast = response.body();
            }
            @Override
            public void onFailure(Call<TextForecast> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });
    }

    private void getForecasts() {
        Call<Forecasts> call = stillaAPI.getWeatherForStationId(OP_W,TYPE,LANG,VIEW,StationId,params);
        call.enqueue(new Callback<Forecasts>() {
            @Override
            public void onResponse(Call<Forecasts> call, Response<Forecasts> response) {
                Forecasts temp = new Forecasts();
                temp = response.body();

                mForecasts.addAll(temp.getStation().getForecast());
            }

            @Override
            public void onFailure(Call<Forecasts> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });
    }
}
