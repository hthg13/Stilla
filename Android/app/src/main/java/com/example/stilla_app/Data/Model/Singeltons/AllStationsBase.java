package com.example.stilla_app.Data.Model.Singeltons;

import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;

import java.util.ArrayList;
import java.util.List;

public class AllStationsBase {

    private List<WeatherStation> mAllStations = new ArrayList<>();
    private static AllStationsBase mAllStationsBase;

    private AllStationsBase() {
        mAllStations = new ArrayList<>();
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
}
