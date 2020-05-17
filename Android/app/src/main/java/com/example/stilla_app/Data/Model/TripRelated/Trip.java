package com.example.stilla_app.Data.Model.TripRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.android.gms.maps.model.LatLng;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class Trip {
    @Element
    private long id;
    @Element
    public String name;
    @ElementList(required = false)
    public List<String> transport;
    @Element
    public String start;
    @Element
    public String finish;
    @Element
    public boolean notify;
    @ElementList(required = false)
    public List<String> places;
    @ElementList(required = false)
    public List<WeatherStation> weatherStation;
    @ElementList(required = false)
    public List<Forecast> weatherForecast;

    public Trip() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTransport() {
        return transport;
    }

    public void setTransport(List<String> transport) {
        this.transport = transport;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public List<WeatherStation> getWeatherStations() {
        return weatherStation;
    }

    public void setWeatherStations(List<WeatherStation> weatherStations) {
        this.weatherStation = weatherStations;
    }

    public List<Forecast> getWeatherForecasts() {
        return weatherForecast;
    }

    public void setWeatherForecasts(List<Forecast> weatherForecasts) {
        this.weatherForecast = weatherForecasts;
    }

    @JsonIgnore
    public List<LatLng> getAllStationCoordinates() {
        List<LatLng> latLngList = new ArrayList<>();

        int n = this.weatherStation.size();

        for (int i=0; i<n; i++) {
            latLngList.add(this.weatherStation.get(i).getLatLng());
        }

        return latLngList;
    }
}
