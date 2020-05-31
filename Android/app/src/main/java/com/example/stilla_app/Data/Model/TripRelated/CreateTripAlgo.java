package com.example.stilla_app.Data.Model.TripRelated;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.stilla_app.Data.Model.MapsRelated.Directions;
import com.example.stilla_app.Data.Model.MapsRelated.RouteBoxer;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.example.stilla_app.View.Activities.MainActivity;
import com.example.stilla_app.View.Activities.TripActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.stilla_app.Data.Model.MapsRelated.Utils.getDistance;
import static com.example.stilla_app.Data.Model.TripRelated.WeatherStation.getAllStationLatLng;

public class CreateTripAlgo extends IntentService {

    private StillaClient mStillaClient;
    private StillaAPI mGoogleApi;
    private StillaAPI mStillaAPI;
    private List<WeatherStation> allStations = new ArrayList<>();
    private MethodsAPI mMethodsAPI = new MethodsAPI();

    private ArrayList<Directions> mDirections = new ArrayList<>();
    private ArrayList<Integer> mDurationValues = new ArrayList<>();
    private List<RouteBoxer.LatLng> mRboxerLatLnglistAll = new ArrayList<>();
    private List<LatLng> mGoogleLatLngListAll = new ArrayList<>();
    private RouteBoxer mRouteBoxer = new RouteBoxer();
    private List<Polyline> mPolylinesList = new ArrayList<>();
    private List<LatLng> mAllStationsLatLng = new ArrayList<>();
    private List<WeatherStation> mAllStationsToUse = new ArrayList<WeatherStation>();
    private List<Forecast> mAllForecasts = new ArrayList<>();
    private List<Trip> mAllTrips = new ArrayList<>();

    String GOOGLE_API_KEY = "AIzaSyDe762ykwpo7NedQysCHa0KtB3-WyZQAjE";

    public CreateTripAlgo() {
        super("CreateTripAlgo");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("******************************HELLOOOOOO***********************************");

        String params = "F;D;T;W;V;N;TD;R";
        String OP_W = "xml";
        String TYPE = "forec";
        String LANG = "is";
        String VIEW = "xml";

        mGoogleApi = StillaClient.getGoogleDirectionsClient().create(StillaAPI.class);

        Trip trip = new Trip();
        List<WeatherStation> tripStations = new ArrayList<>();
        List<Forecast> tripForecast = new ArrayList<>();

        // set the basics that we already have
        trip.setName(intent.getStringExtra("tripName"));
        trip.setStart(intent.getStringExtra("tripStart"));
        trip.setFinish(intent.getStringExtra("tripFinish"));
        trip.setTransport(intent.getStringArrayListExtra("tripTransportation"));
        trip.setPlaces(intent.getStringArrayListExtra("tripDestinations"));
        trip.setNotify(intent.getBooleanExtra("tripNotify", false));

        ArrayList<String> destinations = intent.getStringArrayListExtra("tripDestinations");
        allStations = intent.getParcelableArrayListExtra("allStations");
        mAllTrips = intent.getParcelableArrayListExtra("allTrips");

        // for each of the "legs" between destinations calculate...
        for (int i = 0; i < destinations.size() - 1; i++) {
            final int j = i;

            String origin = destinations.get(i);
            String dest = destinations.get(i + 1);

            Call<Directions> call = mGoogleApi.getDirections(origin, dest, GOOGLE_API_KEY);
            try {
                mDirections.add(call.execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int n = mDirections.size();

            // get the durations between start and finish destinations
            mDurationValues.add(mDirections.get(n - 1).getRoutes().get(0).getLegs().get(0).getDuration().getValue());

            // get all the latlng positions on the directed path, mGoogleLatLngList will be used for polyline drawing
            mRboxerLatLnglistAll = mRouteBoxer.decodePath(mDirections.get(n - 1).getRoutes().get(0).getOverview_polyline().getPoints());
            mGoogleLatLngListAll = mRouteBoxer.rboxerlltogooglell(mRboxerLatLnglistAll);

            // latlng of all stations avalable
            mAllStationsLatLng = getAllStationLatLng(allStations);

            // find all the stations that are within 5 km from the path
            // todo change so that if the weather stations are fiewer than 5 repeat with range larger
            List<Double> distanceList = new ArrayList<>();
            List<Double> distanceListLess = new ArrayList<>();
            mStillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
            int m = mAllStationsLatLng.size();
            for (int k = 0; k < m; k++) {
                WeatherStation currentStation = allStations.get(k);
                for (int l = 0; l < n; l++) {
                    LatLng currLatLngMap = mGoogleLatLngListAll.get(l);
                    double distance = getDistance(currentStation.getLatLng(), currLatLngMap);
                    if (distance < 10) {
                        if (l > 0 && (mAllStationsToUse.get(i-1) != currentStation)) {
                            distanceListLess.add(distance);
                            mAllStationsToUse.add(currentStation);
                        }
                        if (l == 0) {
                            distanceListLess.add(distance);
                            mAllStationsToUse.add(currentStation);
                        }
                    }
                    distanceList.add(distance);
                }
            }

            // get all weather forecast for all the stations -> mAllStationsToUse
            m = mAllStationsToUse.size();
            mStillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
            for (int t = 0; t < m; t++) {
                int currentStationId = (int) mAllStationsToUse.get(t).getId();
                Call<Forecasts> call2 = mStillaAPI.getWeatherForStationId(OP_W, TYPE, LANG, VIEW, currentStationId, params);
                try {
                    Forecasts forecasts = call2.execute().body();
                    if (forecasts.getStation().getErr() == null) {
                        mAllForecasts.addAll(forecasts.getStation().getForecast());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // set destination name to all forecasts
                for (int y = 0; y < mAllForecasts.size(); y++) {
                    mAllForecasts.get(y).setDestinationName(mDirections.get(n - 1).getRoutes().get(0).getLegs().get(0).end_address);
                }
            }

            System.out.println("*****************************************************************");
            System.out.println("UPPHAFSSTAÐUR: " + mDirections.get(n - 1).getRoutes().get(0).getLegs().get(0).start_address);
            System.out.println("TÍMI MILLI STAÐA: " + mDirections.get(n - 1).getRoutes().get(0).getLegs().get(0).duration.getText());
            System.out.println("LOKASTAÐUR: " + mDirections.get(n - 1).getRoutes().get(0).getLegs().get(0).end_address);
            System.out.println();
            System.out.println("FJÖLDI VEÐURSTÖÐVA: " + mAllStationsToUse.size());
            System.out.println("HEILDARFJÖLDI VEÐURSTÖÐVA: " + allStations.size());
            System.out.println("HEILDARFJÖLDI VEÐURSPÁA: " + mAllForecasts.size());
            System.out.println("*****************************************************************");
        }

        // finish setting up the trip
        trip.setWeatherStations(mAllStationsToUse);
        trip.setWeatherForecasts(mAllForecasts);

        mMethodsAPI.setTrip(trip);
        mAllTrips = mMethodsAPI.getTripList();

        /*
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("trip", trip);
        intent2.putParcelableArrayListExtra("googleLine", new ArrayList<>(mGoogleLatLngListAll));
        intent2.putParcelableArrayListExtra("allTrips", new ArrayList<>(mAllTrips));
        startActivity(intent2);

         */
    }
}
