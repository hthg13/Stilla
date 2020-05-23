package com.example.stilla_app.Data.Model.MapsRelated;

import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<WeatherStation> getAtLeastFiveStations(List<WeatherStation> allStations, List<LatLng> allPointsOfTrip) {

        return null;
    }

    public static double getDistance(LatLng a, LatLng b) {
        return distance(a.latitude, a.longitude, b.latitude, b.longitude);
    }

    /**
     * Jason Winn
     * http://jasonwinn.org
     * Created July 10, 2013
     *
     * Description: Small class that provides approximate distance between
     * two points using the Haversine formula.
     *
     * Call in a static context:
     * Haversine.distance(47.6788206, -122.3271205,
     *                    47.6788206, -122.5271205)
     * --> 14.973190481586224 [km]
     *
     */
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static ArrayList<WeatherStation> findWeatherStationsOnRoad(ArrayList<String> places, ArrayList<WeatherStation> allStations) {

        return null;
    }

}
