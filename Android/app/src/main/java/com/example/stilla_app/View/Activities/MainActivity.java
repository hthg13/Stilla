package com.example.stilla_app.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
    private List<Trip> mTripListAfterInitialCall = new ArrayList<>();
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
    private ArrayList<Long> mTripIds = new ArrayList<>();

    // view things
    private Button createTripButton;
    private Button viewAllTripsButton;
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

        createTripButton = (Button) findViewById(R.id.button_new_trip);
        viewAllTripsButton = (Button) findViewById(R.id.button_show_all_trips);

        mTripList.clear();
        mTripList = mMethodsAPI.getTripList();
        mTripListAfterInitialCall.clear();

        // initialize api calls
        allStations = mMethodsAPI.getAllStations();
        mForecasts = mMethodsAPI.getForecasts(StationId, params);
        mTripListAfterInitialCall = mMethodsAPI.getTripList();

        // initialize recycler view
        Intent intent = getIntent();
        //Bundle bundle = getIntent().getExtras();
        //mTripList = bundle.getParcelableArrayList("allTrips");
        mTripList = intent.getParcelableArrayListExtra("allTrips");
        if (mTripList.size() == 0 || mTripList == null) {
            Toast toast = Toast.makeText(getApplicationContext(),"Þú hefur engar skráðar ferðir", Toast.LENGTH_LONG);
            toast.show();
        }
        initListItems(mTripList);

        createTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripActivity.class);
                startActivity(intent);

                /* MAPS ACTIVATION EXAMPLE
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("allStations", new ArrayList<WeatherStation>(allStations));
                intent.putExtras(bundle);
                startActivity(intent);
                */
            }
        });

        viewAllTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTripList.size() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Þú hefur engar skráðar ferðir", Toast.LENGTH_LONG);
                    toast.show();
                }
                initListItems(mTripListAfterInitialCall);
            }
        });
    }

    /**
     * HELPER METHODS
     */

    public void initRecyclerView(List<Trip> tripsList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter_Main adapter = new RecyclerViewAdapter_Main(mTripNames,mTripStarts,mTripEnds,mTripIds,tripsList,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    public void initListItems(List<Trip> tripsList) {
        int n = tripsList.size();

        mTripNames.clear();
        mTripStarts.clear();
        mTripEnds.clear();
        mTripIds.clear();

        for (int i=0; i<n; i++) {
            String currentName = tripsList.get(i).getName();
            String currentStart = tripsList.get(i).getStart();
            String currentEnd = tripsList.get(i).getFinish();
            long currentid = tripsList.get(i).getId();

            mTripNames.add(currentName);
            mTripStarts.add(currentStart);
            mTripEnds.add(currentEnd);
            mTripIds.add(currentid);
        }
        initRecyclerView(tripsList);
    }
}
