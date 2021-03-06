package com.example.stilla_app.Data.Network;

import com.example.stilla_app.Data.Model.MapsRelated.Directions;
import com.example.stilla_app.Data.Model.TripRelated.Forecasts;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Model.TripRelated.TextForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StillaAPI {

    String BASE_URL_STILLA_API = "http://10.0.2.2:8080";
    String BASE_URL_VEDUR_API = "https://xmlweather.vedur.is";
    String GOOGLE_DIRECTIONS_API = "https://maps.googleapis.com/maps/api/directions/json";
    String GOOGLE_API_KEY = "AIzaSyDe762ykwpo7NedQysCHa0KtB3-WyZQAjE";


    // VEÐURSTOFAN ---------------------------------------------------------------------------------

    /**
     * @param op_w must be "xml"
     * @param type must be "forec"
     * @param lang must be "is"
     * @param view must be "xml"
     * @param ids must be an id of a weather station in Iceland
     * @param params
     * @return Forecast java object
     */
    @GET(BASE_URL_VEDUR_API + "/")
    Call<Forecasts> getWeatherForStationId(@Query("op_w") String op_w, @Query("type") String type, @Query("lang") String lang, @Query("view") String view, @Query("ids") int ids, @Query("params") String params, @Query("time") String time);

    /**
     * gets the text format of weather forecast for the whole country for the next week from call
     */
    @GET(BASE_URL_VEDUR_API + "/?op_w=xml&type=txt&lang=is&view=rss&ids=5")
    Call<TextForecast> getWeatherTextNextDays();

    // STILLA BACKEND ---------------------------------------------------------------------------------

    // gets all weather stations in a list from stilla server
    @GET(BASE_URL_STILLA_API + "/weather_stations")
    Call<List<WeatherStation>> getAllStations();

    // gets all trips
    @GET(BASE_URL_STILLA_API + "/getTrips")
    Call<List<Trip>> getAllTrips();

    // should post to the server a new trip from user interface
    @POST(BASE_URL_STILLA_API + "/saveTrip")
    Call<Trip> saveTrip(@Body Trip tip);

    // GOOGLE -----------------------------------------------------------------------------------------

    @GET(GOOGLE_DIRECTIONS_API)
    Call<Directions> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
