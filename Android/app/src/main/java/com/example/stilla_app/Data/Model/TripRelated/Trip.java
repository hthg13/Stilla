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
public class Trip implements Parcelable {
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

    @ElementList(required = false)
    public List<String> googleDirectionList;

    public List<String> getGoogleDirectionList() {
        return this.googleDirectionList;
    }

    public List<LatLng> getGoogleDirectionListLatLong() {
        int n = googleDirectionList.size();

        List<LatLng> newgoogleList = new ArrayList<>();

        for(int i=0; i<n; i++) {
            String currentLatLng = googleDirectionList.get(i);
            currentLatLng = currentLatLng.substring(currentLatLng.indexOf("(") + 1);
            currentLatLng = currentLatLng.substring(0, currentLatLng.indexOf(")"));

            String[] latlng = currentLatLng.split(",");

            LatLng realLatLong = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));

            newgoogleList.add(realLatLong);
        }

        return newgoogleList;
    }

    public void setGoogleDirectionList(List<String> googleDirectionList) {
        this.googleDirectionList = googleDirectionList;
    }

    public Trip() {
    }

    public Trip(String name, String start, String finish, ArrayList<String> destinations, ArrayList<String> transportation, boolean notify) {
        this.name = name;
        this.start = start;
        this.finish = finish;
        this.places = destinations;
        this.transport = transportation;
        this.notify = notify;
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
    public int getDay() {
        String time = this.getStart();
        String[] splitTime = time.split("-");
        int day = Integer.parseInt(splitTime[0]);
        return day;
    }

    @JsonIgnore
    public int getMonth() {
        String time = this.getStart();
        String[] splitTime = time.split("-");
        int month = Integer.parseInt(splitTime[1]);
        return month;
    }

    @JsonIgnore
    public int getYear() {
        String time = this.getStart();
        String[] splitTime = time.split("-");
        String[] splitYear = splitTime[2].split(" ");
        int year = Integer.parseInt(splitYear[0]);
        return year;
    }

    @JsonIgnore
    public int getTime() {
        String time = this.getStart();
        String[] splitTime = time.split(" ");
        String clock = splitTime[1];
        String[] hourClock = clock.split(":");
        int hour = Integer.parseInt(hourClock[0]);
        return hour;
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
        dest.writeTypedList(this.weatherForecast);
        dest.writeStringList(this.googleDirectionList);
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
        this.weatherForecast = in.createTypedArrayList(Forecast.CREATOR);
        this.googleDirectionList = in.createStringArrayList();
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
