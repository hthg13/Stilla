package com.example.stilla_app.Data.Model.TripRelated;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;
import com.example.stilla_app.Data.Model.MapsRelated.Directions;
import com.example.stilla_app.Data.Model.MapsRelated.RouteBoxer;
import com.example.stilla_app.Data.Model.Singeltons.AllStationsBase;
import com.example.stilla_app.Data.Model.Singeltons.AllTripsBase;
import com.example.stilla_app.Data.Model.Singeltons.CalculatedTrip;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;
import com.example.stilla_app.View.Activities.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.stilla_app.Data.Model.MapsRelated.Utils.getDistance;
import static com.example.stilla_app.Data.Model.TripRelated.WeatherStation.getAllStationLatLng;

public class CreateTripAlgo extends IntentService {

    private StillaAPI mGoogleApi;
    private StillaAPI mStillaAPI;
    private List<WeatherStation> allStations = new ArrayList<>();
    private List<WeatherStation> allStationsAvalable = new ArrayList<>();
    private MethodsAPI mMethodsAPI = new MethodsAPI();

    private ArrayList<Directions> mDirections = new ArrayList<>();
    private ArrayList<Integer> mDurationValues = new ArrayList<>();
    private List<RouteBoxer.LatLng> mRboxerLatLnglistAll = new ArrayList<>();
    private List<LatLng> mGoogleLatLngListAll = new ArrayList<>();
    private RouteBoxer mRouteBoxer = new RouteBoxer();
    private List<LatLng> mAllStationsLatLng = new ArrayList<>();
    private List<WeatherStation> mAllStationsToUse = new ArrayList<>();
    private List<Forecast> mAllForecasts = new ArrayList<>();
    private String GOOGLE_API_KEY = "AIzaSyDe762ykwpo7NedQysCHa0KtB3-WyZQAjE";

    public CreateTripAlgo() {
        super("CreateTripAlgo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String params = "F;D;T;W;V;N;TD;R";
        String OP_W = "xml";
        String TYPE = "forec";
        String LANG = "is";
        String VIEW = "xml";

        mGoogleApi = StillaClient.getGoogleDirectionsClient().create(StillaAPI.class);

        Trip trip = CalculatedTrip.get().getTrip();
        ArrayList<String> destinations = new ArrayList<>(trip.getPlaces());
        allStations = AllStationsBase.get().getAllStations();

        // for each of the "legs" between destinations calculate
        for (int i = 0; i < destinations.size() - 1; i++) {

            String origin = destinations.get(i);
            String dest = destinations.get(i + 1);

            Call<Directions> call = mGoogleApi.getDirections(origin, dest, GOOGLE_API_KEY);
            ArrayList<Directions> directions = new ArrayList<>();
            try {
                directions.add(call.execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int n = directions.size();

            // get the durations between start and finish destinations
            mDurationValues.add(directions.get(n - 1).getRoutes().get(0).getLegs().get(0).getDuration().getValue());

            // get all the latlng positions on the directed path, mGoogleLatLngList will be used for polyline drawing
            mRboxerLatLnglistAll = (mRouteBoxer.decodePath(directions.get(n - 1).getRoutes().get(0).getOverview_polyline().getPoints()));
            mGoogleLatLngListAll.addAll(mRouteBoxer.rboxerlltogooglell(mRboxerLatLnglistAll));

            List<LatLng> useList = mRouteBoxer.rboxerlltogooglell(mRboxerLatLnglistAll);

            // latlng of all stations avalable
            mAllStationsLatLng = getAllStationLatLng(allStations);

            // find all the stations that are within 5 km from the path
            // todo change so that if the weather stations are fiewer than 5 repeat with range larger
            mStillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
            for (int k = 0; k < mAllStationsLatLng.size(); k++) {
                WeatherStation currentStation = allStations.get(k);
                for (int l = 0; l < useList.size(); l++) {
                    LatLng currLatLngMap = useList.get(l);
                    double distance = getDistance(currentStation.getLatLng(), currLatLngMap);
                    if (distance < 1) {
                        /*
                        if (l > 0 && (mAllStationsToUse.get(i-1) != currentStation)) {
                            mAllStationsToUse.add(currentStation);
                            System.out.println(k + ". nafn veðurstöðvar sem er bætt við ferðina: " + currentStation.getName());
                        } else if (l == 0) {
                            mAllStationsToUse.add(currentStation);
                            System.out.println(k + ". nafn veðurstöðvar sem er bætt við ferðina: " + currentStation.getName());
                        }

                         */
                        mAllStationsToUse.add(currentStation);
                        System.out.println(k + ". nafn veðurstöðvar sem er bætt við ferðina: " + currentStation.getName());
                    }
                }
            }

            // get all weather forecast for all the stations -> mAllStationsToUse
            int m = mAllStationsToUse.size();
            mStillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
            for (int t = 0; t < m; t++) {
                int currentStationId = (int) mAllStationsToUse.get(t).getId();
                Call<Forecasts> call2 = mStillaAPI.getWeatherForStationId(OP_W, TYPE, LANG, VIEW, currentStationId, params);
                try {
                    Forecasts forecasts = call2.execute().body();
                    if (forecasts.getStation().getErr() == null) {
                        for (int z=0; z<forecasts.getStation().getForecast().size(); z++) {
                            String[] startpoints = directions.get(n - 1).getRoutes().get(0).getLegs().get(0).start_address.split(",");
                            String[] endpoints = directions.get(n - 1).getRoutes().get(0).getLegs().get(0).end_address.split(",");
                            forecasts.getStation().getForecast().get(z).setStationEndpointName(startpoints[0] + " - " + endpoints[0]);
                        }
                        mAllForecasts.addAll(forecasts.getStation().getForecast());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("*****************************************************************");
            System.out.println("UPPHAFSSTAÐUR: " + directions.get(n - 1).getRoutes().get(0).getLegs().get(0).start_address);
            System.out.println("TÍMI MILLI STAÐA: " + directions.get(n - 1).getRoutes().get(0).getLegs().get(0).duration.getText());
            System.out.println("LOKASTAÐUR: " + directions.get(n - 1).getRoutes().get(0).getLegs().get(0).end_address);
            System.out.println("FJÖLDI VEÐURSTÖÐVA: " + mAllStationsToUse.size());
            System.out.println("HEILDARFJÖLDI VEÐURSPÁA: " + mAllForecasts.size());
            System.out.println("*****************************************************************");

            // todo this was added
            mDirections.addAll(directions);
        }

        // finish setting up the trip
        trip.setWeatherStations(mAllStationsToUse);
        trip.setWeatherForecasts(mAllForecasts);

        List<String> googleLatLngListString = new ArrayList<>();
        for (int i=0;i<mGoogleLatLngListAll.size();i++) {
            googleLatLngListString.add(mGoogleLatLngListAll.get(i).toString());
        }

        trip.setGoogleDirectionList(new ArrayList<>(googleLatLngListString));

        // set the trip to the singelton class calculated trip for later use
        CalculatedTrip.get().setTrip(trip);
        setNewTrip(trip);

        // start activity MainActivity and add a flag because this class is not an activity
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);
    }

    private void setNewTrip(Trip trip) {
        mStillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);
        Call<Trip> call = mStillaAPI.saveTrip(trip);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                Trip trip = response.body();
                Toast.makeText(getApplicationContext(),"Tókst að vista ferðina",Toast.LENGTH_LONG).show();
                AllTripsBase.get().setTripsList(new ArrayList<>(mMethodsAPI.getTripList()));
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Tókst því miður ekki að vista ferðina, reyndu aftur",Toast.LENGTH_LONG).show();
                System.out.println("FAILURE: " + t);
            }
        });
    }
}
