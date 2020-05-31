package com.example.stilla_app.Data.Model.Singeltons;

import com.example.stilla_app.Data.Model.TripRelated.Trip;

public class CalculatedTrip {

    private Trip mTrip = new Trip();
    private static CalculatedTrip mCalculatedTrip;

    private CalculatedTrip() {
        //mTrip = new Trip();
    }

    public static CalculatedTrip get() {
        if (mCalculatedTrip == null) {
            mCalculatedTrip = new CalculatedTrip();
        }
        return mCalculatedTrip;
    }

    public Trip getTrip() {
        return mTrip;
    }

    public void setTrip(Trip trip) {
        mTrip = trip;
    }
}
