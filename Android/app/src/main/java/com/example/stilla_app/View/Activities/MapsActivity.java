package com.example.stilla_app.View.Activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.stilla_app.Data.Model.MapsRelated.Directions;
import com.example.stilla_app.Data.Model.MapsRelated.Location;
import com.example.stilla_app.Data.Model.MapsRelated.RouteBoxer;
import com.example.stilla_app.Data.Model.Singeltons.AllStationsBase;
import com.example.stilla_app.Data.Model.Singeltons.CalculatedTrip;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.example.stilla_app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

        allStations = AllStationsBase.get().getAllStations();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(64.1465944, -21.9426321);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Iceland"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)      // Sets the center of the map to location user
                .zoom(5)             // Sets the zoom
                .build();            // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        Trip trip = CalculatedTrip.get().getTrip();

        Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(trip.getGoogleDirectionListLatLong()));

        /*
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        for (int i=0; i<AllStationsBase.get().getAvalableStations().size(); i++){
            mMap.addMarker(new MarkerOptions()
                    .position(AllStationsBase.get().getAvalableStations().get(i).getLatLng())
                    .icon(bitmapDescriptor));
        }
        */

        BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        for (int i = 0; i<trip.getWeatherStations().size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(trip.getWeatherStations().get(i).getLatLng())
                    .title(trip.getWeatherStations().get(i).getName())
                    .icon(bitmapDescriptor2));
        }
    }
}
