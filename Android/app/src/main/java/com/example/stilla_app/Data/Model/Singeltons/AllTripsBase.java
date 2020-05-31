package com.example.stilla_app.Data.Model.Singeltons;

import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.Data.Network.StillaAPI;
import com.example.stilla_app.Data.Network.StillaClient;

import java.util.ArrayList;
import java.util.List;

public class AllTripsBase {

    private ArrayList<Trip> mTripsList;
    private static AllTripsBase mAllTripsBase;
    private MethodsAPI mMethodsAPI= new MethodsAPI();
    private StillaAPI mStillaAPI;

    private AllTripsBase() {
        mTripsList = new ArrayList<>(mMethodsAPI.getTripList());
    }

    public static AllTripsBase get() {
        if (mAllTripsBase == null) {
            mAllTripsBase = new AllTripsBase();
        }
        return mAllTripsBase;
    }

    public ArrayList<Trip> getTripsList() {
        return mTripsList;
    }

    public void setTripsList(ArrayList<Trip> tripsList) {
        mTripsList = tripsList;
    }
}
