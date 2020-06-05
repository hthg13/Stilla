package com.example.stilla_app.Data.Model.TripRelated;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
    private List<LatLng> mAllStationsLatLngToUse = new ArrayList<>();

    private boolean hasError = false;

    private String params = "F;D;T;W;V;N;TD;R";
    private String OP_W = "xml";
    private String TYPE = "forec";
    private String LANG = "is";
    private String VIEW = "xml";

    private String GOOGLE_API_KEY = "AIzaSyDe762ykwpo7NedQysCHa0KtB3-WyZQAjE";

    public CreateTripAlgo() {
        super("CreateTripAlgo");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent) {
        mGoogleApi = StillaClient.getGoogleDirectionsClient().create(StillaAPI.class);

        Trip trip = CalculatedTrip.get().getTrip();
        ArrayList<String> destinations = new ArrayList<>(trip.getPlaces());
        allStations = AllStationsBase.get().getAllStations();

        hasError = false;

        // for each of the "legs" between destinations calculate
        for (int i = 0; i < destinations.size() - 1; i++) {

            String origin = destinations.get(i);
            String dest = destinations.get(i + 1);

            Call<Directions> call = mGoogleApi.getDirections(origin, dest, GOOGLE_API_KEY);
            ArrayList<Directions> directions = new ArrayList<>();
            try {
                Directions dir = call.execute().body();
                if (dir.getRoutes().size() == 0) {
                    hasError = true;
                    Toast.makeText(getApplicationContext(),"Tókst því miður ekki að vista ferðina, villa í áfangastöðum",Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this, MainActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                }
                directions.add(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!hasError) {
                int n = directions.size();

                // get the durations between start and finish destinations
                mDurationValues.add(directions.get(n - 1).getRoutes().get(0).getLegs().get(0).getDuration().getValue());

                // get all the latlng positions on the directed path, mGoogleLatLngList will be used for polyline drawing
                mRboxerLatLnglistAll = (mRouteBoxer.decodePath(directions.get(n - 1).getRoutes().get(0).getOverview_polyline().getPoints()));
                mGoogleLatLngListAll.addAll(mRouteBoxer.rboxerlltogooglell(mRboxerLatLnglistAll));

                List<LatLng> useList = mRouteBoxer.rboxerlltogooglell(mRboxerLatLnglistAll);

                // latlng of all stations avalable
                mAllStationsLatLng = getAllStationLatLng(allStations);

                // find all the stations that are within 1 km from the path
                List<WeatherStation> stationsToUseForThisLeg = new ArrayList<>();
                List<LatLng> latlngToUseThisLeg = new ArrayList<>();
                mStillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
                for (int k = 0; k < useList.size(); k++) {
                    LatLng currLatLngMap = useList.get(k);
                    for (int l = 0; l < mAllStationsLatLng.size(); l++) {
                        WeatherStation currentStation = allStations.get(l);
                        double distance = getDistance(currentStation.getLatLng(), currLatLngMap);
                        if (distance < 1) {
                            stationsToUseForThisLeg.add(currentStation);
                            latlngToUseThisLeg.add(currLatLngMap);
                        }
                    }
                }

                // clean up the list of stations
                List<WeatherStation> newStationsToUseThisLeg = new ArrayList<>(new HashSet<>(stationsToUseForThisLeg));

                List<Forecast> foracastsThisLeg = calculateForecast(newStationsToUseThisLeg, useList, directions, i, trip, n);

                System.out.println("UPPHAFSSTAÐUR: " + directions.get(n - 1).getRoutes().get(0).getLegs().get(0).start_address);
                System.out.println("TÍMI MILLI STAÐA: " + directions.get(n - 1).getRoutes().get(0).getLegs().get(0).duration.getText());
                System.out.println("LOKASTAÐUR: " + directions.get(n - 1).getRoutes().get(0).getLegs().get(0).end_address);
                System.out.println("FJÖLDI VEÐURSTÖÐVA: " + stationsToUseForThisLeg.size());
                System.out.println("HEILDARFJÖLDI VEÐURSPÁA: " + foracastsThisLeg.size());
                System.out.println("*****************************************************************");

                // todo this was added
                mDirections.addAll(directions);
                mAllStationsToUse.addAll(newStationsToUseThisLeg);
                mAllForecasts.addAll(foracastsThisLeg);
                mAllStationsLatLngToUse.addAll(latlngToUseThisLeg);
            }
        }

        if(!hasError) {

            ArrayList<WeatherStation> cleanList = new ArrayList<>(new HashSet<>(mAllStationsToUse));
            mAllStationsToUse.clear();
            mAllStationsToUse.addAll(cleanList);

            for (int k = 0; k < mAllStationsToUse.size(); k++) {
                Log.d("veðurstöðvar", "nafn: " + mAllStationsToUse.get(k).getId() + " " + mAllStationsToUse.get(k).getName());
            }

            // set the googleLatlnglist to the correct format ie string format instead of latlng
            List<String> googleLatLngListString = new ArrayList<>();
            for (int i = 0; i < mGoogleLatLngListAll.size(); i++) {
                googleLatLngListString.add(mGoogleLatLngListAll.get(i).toString());
                //googleLatLngListString.add(mAllStationsLatLngToUse.get(i).toString());
            }

            // finish setting up the trip
            trip.setWeatherStations(mAllStationsToUse);
            trip.setWeatherForecasts(mAllForecasts);
            trip.setGoogleDirectionList(new ArrayList<>(googleLatLngListString));

            // set the trip to the singelton class calculated trip for later use
            CalculatedTrip.get().setTrip(trip);
            setNewTrip(trip);

            // start activity MainActivity and add a flag because this class is not an activity
            Intent intent2 = new Intent(this, MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }
    }

    public int durationOfCurrentLeg = 0;
    public int startHour;
    public int endHour;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Forecast> calculateForecast(List<WeatherStation> newStationsToUseThisLeg, List<LatLng> useList, ArrayList<Directions> directions, int i, Trip trip, int n) {
        int m = newStationsToUseThisLeg.size();
        List<Forecast> allForecastsToUseThisLeg = new ArrayList<>();

        int startYear = trip.getYear();
        int startMonth = trip.getMonth();
        int startDay = trip.getDay();
        startHour = trip.getTime();

        durationOfCurrentLeg += mDurationValues.get(mDurationValues.size()-1); // in minutes

        int hours = (durationOfCurrentLeg / 3600) + 1;
        int minutes = (durationOfCurrentLeg % 3600) / 60;

        if (minutes > 30) {
            hours = hours + 1;
        }

        if (i == 0) {
            startHour = trip.getTime();
            endHour = (startHour + hours)%25;
        } else {
            int temp = startHour;
            startHour = endHour;
            endHour = (temp + hours)%25;
        }

        Calendar tripCalStart = new GregorianCalendar(startYear,startMonth-1,startDay);
        tripCalStart.set(Calendar.HOUR_OF_DAY, startHour);
        int tripDayOfYear = tripCalStart.get(Calendar.DAY_OF_YEAR);

        Calendar tripCalFinish = new GregorianCalendar(tripCalStart.get(Calendar.YEAR), tripCalStart.get(Calendar.MONTH), tripCalStart.get(Calendar.DAY_OF_MONTH));
        tripCalFinish.set(Calendar.HOUR_OF_DAY, startHour);
        int tripEndDayOfYear = tripCalFinish.get(Calendar.DAY_OF_YEAR);

        int maxDay = LocalDate.now().plusDays(10).getDayOfYear();
        int minDay = LocalDate.now().getDayOfYear();

        Log.d("forecastBug", "tripDayOfYear: " + tripDayOfYear);
        Log.d("forecastBug", "tripEndDayOfYear: " + tripEndDayOfYear);
        Log.d("forecastBug", "startHour: " + startHour);
        Log.d("forecastBug", "endHour: " + endHour);
        Log.d("forecastBug", "mDurationValues: " + mDurationValues.get(mDurationValues.size()-1));
        Log.d("forecastBug", "durationOfCurrentLeg: " + durationOfCurrentLeg);

        mStillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
        for (int t = 0; t < m; t++) {
            int currentStationId = (int) newStationsToUseThisLeg.get(t).getId();
            Call<Forecasts> call2 = mStillaAPI.getWeatherForStationId(OP_W, TYPE, LANG, VIEW, currentStationId, params, "1h");
            try {
                Forecasts forecasts = call2.execute().body();
                if (forecasts.getStation().getErr() == null) {

                    for (int j=0; j<forecasts.getStation().getForecast().size(); j++) {
                        Forecast currentForecast = forecasts.getStation().getForecast().get(j);

                        Calendar forecCal = new GregorianCalendar(currentForecast.getYear(), currentForecast.getMonth() - 1, currentForecast.getDay());
                        int forecDayOfYear = forecCal.get(Calendar.DAY_OF_YEAR);

                        if (tripDayOfYear > maxDay) { // remove the forecasts that are out of bounds of max day
                            break;
                        } else if (tripDayOfYear < minDay) { // remove forecasts that are ot of bounds of min day
                            break;
                        } else if (forecDayOfYear < tripDayOfYear) {
                            // do nothing wait til forecast matches the trip
                        } else if (forecDayOfYear == tripDayOfYear && currentForecast.getTime() < startHour) {
                            // do nothing wait til forecast matches the trip
                        } else if (forecDayOfYear > tripEndDayOfYear) {
                            break;
                        } else if (forecDayOfYear == tripEndDayOfYear && currentForecast.getTime() > endHour) {
                            break;
                        } else {
                            allForecastsToUseThisLeg.add(currentForecast);
                        }
                    }
                    addDestinationPointToForecast(directions,forecasts,n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allForecastsToUseThisLeg;
    }

    private void addDestinationPointToForecast(ArrayList<Directions> directions, Forecasts forecasts, int n) {
        for (int z=0; z<forecasts.getStation().getForecast().size(); z++) {
            String[] startpoints = directions.get(n - 1).getRoutes().get(0).getLegs().get(0).start_address.split(",");
            String[] endpoints = directions.get(n - 1).getRoutes().get(0).getLegs().get(0).end_address.split(",");
            forecasts.getStation().getForecast().get(z).setStationEndpointName(startpoints[0] + " - " + endpoints[0] + ":" + "\n" + forecasts.getStation().getName());
        }
    }

    private void setNewTrip(Trip trip) {
        mStillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);
        Call<Trip> call = mStillaAPI.saveTrip(trip);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                Trip trip = response.body();
                Toast.makeText(getApplicationContext(),"Tókst að vista ferðina",Toast.LENGTH_SHORT).show();
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
