package com.example.stilla_app.Data.Model.TripRelated;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.android.gms.maps.model.LatLng;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class Trip implements Parcelable{
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

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.transport);
        dest.writeString(this.start);
        dest.writeString(this.finish);
        dest.writeByte((byte) (this.notify ? 1 : 0));
        dest.writeStringList(this.places);
        dest.writeTypedList(this.weatherStation);
    }

    @JsonIgnore
    public Trip(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.transport = in.createStringArrayList();
        this.start = in.readString();
        this.finish = in.readString();
        this.notify = in.readByte() != 0;
        this.places = in.createStringArrayList();
        this.weatherStation = in.createTypedArrayList(WeatherStation.CREATOR);
    }

    @JsonIgnore
    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}
