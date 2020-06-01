package com.example.stilla_app.Data.Model.Singeltons;

import com.example.stilla_app.Data.Model.TripRelated.Forecasts;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllStationsBase {

    private List<WeatherStation> mAllStations = new ArrayList<>();
    private List<WeatherStation> mAvalableStations = new ArrayList<>();
    private static AllStationsBase mAllStationsBase;

    private AllStationsBase() {
        mAllStations = new ArrayList<>();
        mAvalableStations = new ArrayList<>();
    }

    public static AllStationsBase get() {
        if (mAllStationsBase == null) {
            mAllStationsBase = new AllStationsBase();
        }
        return mAllStationsBase;
    }

    public List<WeatherStation> getAllStations() {
        return mAllStations;
    }

    public void setAllStations(List<WeatherStation> allStations) {
        mAllStations = allStations;
    }

    public List<WeatherStation> getAvalableStations() {
        return mAvalableStations;
    }

    public void setAvalableStations(List<WeatherStation> avalableStations) {
        mAvalableStations = avalableStations;
    }
}
