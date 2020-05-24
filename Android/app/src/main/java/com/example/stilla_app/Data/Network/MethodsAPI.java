package com.example.stilla_app.Data.Network;

import com.example.stilla_app.Data.Model.TripRelated.Forecast;
import com.example.stilla_app.Data.Model.TripRelated.Forecasts;
import com.example.stilla_app.Data.Model.TripRelated.TextForecast;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MethodsAPI {

    private StillaAPI stillaAPI;

    private List<WeatherStation> mAllStations = new ArrayList<>();
    private List<Forecast> mForecasts = new ArrayList<>();
    private List<Trip> mTripList = new ArrayList<>();
    private TextForecast mTextForecast = new TextForecast();

    // GETTERS AND SETTERS **********************************************************************************

    public List<WeatherStation> getAllStations() {
        stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);
        APIgetAllWeatherStations();
        return mAllStations;
    }

    public List<Forecast> getForecasts(int StationId,String params) {
        String OP_W = "xml";
        String TYPE = "forec";
        String LANG = "is";
        String VIEW = "xml";

        stillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
        APIgetAllForecasts(OP_W,TYPE,LANG,VIEW,StationId,params);
        return mForecasts;
    }

    public List<Trip> getTripList() {
        stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);
        APIgetAllTrips();
        return mTripList;
    }

    // TODO FIX THIS METHOD
    public TextForecast getTextForecast() {
        stillaAPI = StillaClient.getVedurstofaClient().create(StillaAPI.class);
        APIgetTextForecast();
        return mTextForecast;
    }

    public void setTrip(Trip trip) {
        stillaAPI = StillaClient.getStillaClient().create(StillaAPI.class);
        setNewTrip(trip);
    }

    // API CALL METHODS **********************************************************************************

    private void APIgetAllWeatherStations() {
        Call<List<WeatherStation>> call = stillaAPI.getAllStations();
        call.enqueue(new Callback<List<WeatherStation>>() {
            @Override
            public void onResponse(Call<List<WeatherStation>> call, Response<List<WeatherStation>> response) {
                if(response.isSuccessful()) {
                    mAllStations.addAll(response.body());
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

    private void APIgetAllTrips() {
        Call<List<Trip>> call = stillaAPI.getAllTrips();
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                System.out.println("found all the trips");
                mTripList.clear();
                mTripList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                System.out.println("failure: " + t + call);
            }
        });
    }

    private void APIgetAllForecasts(String OP_W,String TYPE,String LANG,String VIEW,int StationId,String params) {
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

    private void APIgetTextForecast() {
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
}
