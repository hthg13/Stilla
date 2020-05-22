package com.example.stilla_app.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stilla_app.Data.Model.TripRelated.Forecast;
import com.example.stilla_app.Data.Model.TripRelated.Forecasts;
import com.example.stilla_app.Data.Model.TripRelated.TextForecast;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.example.stilla_app.R;
import com.example.stilla_app.View.Adapters.RecyclerViewAdapter_Main;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.stilla_app.Data.Model.TripRelated.WeatherStation.getAllStationLatLng;

public class MainActivity extends AppCompatActivity {

    // parameters that must be passed to the getWeatherForStationId method
    private StillaAPI stillaAPI;
    private MethodsAPI mMethodsAPI = new MethodsAPI();

    private List<WeatherStation> allStations = new ArrayList<>();
    private List<Forecast> mForecasts = new ArrayList<>();
    private List<Trip> mTripList = new ArrayList<>();
    private TextForecast mTextForecast;

    //private String OP_W = "xml";
    //private String TYPE = "forec";
    //private String LANG = "is";
    //private String VIEW = "xml";
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

    // for listview
    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripStarts = new ArrayList<>();
    private ArrayList<String> mTripEnds = new ArrayList<>();

    // view things
    private Button mbutton;
    private Button initButton;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Þínar ferðir");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_stilla_logo_and_name_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mbutton = (Button) findViewById(R.id.button);
        initButton = (Button) findViewById(R.id.button_new_trip);

        mTripList = mMethodsAPI.getTripList();
        initListItems(mTripList);

        //stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class); //do not remove
        //getAllWeatherStations(); // initialize the allstations private variable list
        allStations = mMethodsAPI.getAllStations();
        //getAllTrips();


        //stillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class); //do not remove
        //getForecasts();
        mForecasts = mMethodsAPI.getForecasts(StationId, params);
        //getTextForecast();


        //stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mTextForecast = mMethodsAPI.getTextForecast();
                System.out.println(allStations.get(0).getName());
                System.out.println(mForecasts.get(0).getFtime());
                System.out.println(mTripList.get(0).getName());
                //System.out.println(mTextForecast.getTextNextDays().getDescription().getDescription());

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

                //setNewTrip(trip);
                mMethodsAPI.setTrip(trip);

                System.out.println(allStations.get(0).getLatLng());
                List<LatLng> latLngList = mTripList.get(0).getAllStationCoordinates();
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

        initButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTripList = mMethodsAPI.getTripList();
                initListItems(mTripList);
            }
        });
    }

    /**
     * HELPER METHODS
     */

    public void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter_Main adapter = new RecyclerViewAdapter_Main(mTripNames,mTripStarts,mTripEnds,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initListItems(List<Trip> tripsList) {
        int n = tripsList.size();

        for (int i=0; i<n; i++) {
            String currentName = tripsList.get(i).getName();
            String currentStart = tripsList.get(i).getStart();
            String currentEnd = tripsList.get(i).getFinish();

            mTripNames.add(currentName);
            mTripStarts.add(currentStart);
            mTripEnds.add(currentEnd);
        }

        initRecyclerView();
    }


    /*
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
    */

}
