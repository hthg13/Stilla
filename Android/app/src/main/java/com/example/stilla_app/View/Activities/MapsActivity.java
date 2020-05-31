package com.example.stilla_app.View.Activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.stilla_app.Data.Model.MapsRelated.Directions;
import com.example.stilla_app.Data.Model.MapsRelated.Location;
import com.example.stilla_app.Data.Model.MapsRelated.RouteBoxer;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.example.stilla_app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.stilla_app.Data.Model.MapsRelated.Utils.getDistance;
import static com.example.stilla_app.Data.Model.TripRelated.WeatherStation.getAllStationLatLng;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlacesClient placesClient;
    private StillaClient mStillaClient;
    private StillaAPI mGoogleApi;
    private List<WeatherStation> allStations = new ArrayList<>();

    String GOOGLE_API_KEY = "AIzaSyDe762ykwpo7NedQysCHa0KtB3-WyZQAjE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ferðin þín á korti");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_stilla_logo_and_name_round);
        actionBar.setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        allStations = bundle.getParcelableArrayList("allStations");

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(64.1465944, -21.9426321);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Iceland"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)      // Sets the center of the map to location user
                .zoom(5)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                //.tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleApi = StillaClient.getGoogleDirectionsClient().create(StillaAPI.class);
        String origin = "Reykjavík";
        String destination = "Stóri Núpur";
        getDirections(origin,destination);
    }

    private void getDirections(String origin, String destination) {
        Call<Directions> call = mGoogleApi.getDirections(origin,destination,GOOGLE_API_KEY);
        call.enqueue(new Callback<Directions>() {
            @Override
            public void onResponse(Call<Directions> call, Response<Directions> response) {
                Directions directions = response.body();
                List<Location> location = directions.getAllLocations();
                List<LatLng> latlng = directions.getAllLatLng();


                RouteBoxer rboxer = new RouteBoxer();
                List<RouteBoxer.LatLng> rboxerlist = rboxer.decodePath(directions.getRoutes().get(0).getOverview_polyline().getPoints());
                List<LatLng> googleList = rboxer.rboxerlltogooglell(rboxerlist);

                Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(googleList));
                //polyline.setColor(ContextCompat.getColor(getActivity));

                int n = googleList.size();
                for(int i=0;i<n;i++) {
                    //mMap.addMarker(new MarkerOptions().position(allStations.get(i).getLatLng()));
                }

                List<LatLng> allStationsLatLng = getAllStationLatLng(allStations);

                List<Double> distanceList = new ArrayList<>();
                List<Double> distanceListLess = new ArrayList<>();

                int m = allStationsLatLng.size();

                for (int i=0; i<m; i++) {
                    WeatherStation currentStation = allStations.get(i);
                    for (int j=0; j<n; j++) {
                        LatLng currLatLngMap = googleList.get(j);
                        double distance = getDistance(currentStation.getLatLng(), currLatLngMap);
                        if(distance < 1) {
                            distanceListLess.add(distance);
                            mMap.addMarker(new MarkerOptions().position(currentStation.getLatLng()));
                        }
                        distanceList.add(distance);
                    }
                }
            }

            @Override
            public void onFailure(Call<Directions> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}
