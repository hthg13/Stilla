package com.example.stilla_app.Data.Model.TripRelated;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Parcelable {

    @JsonProperty(required = false)
    @JsonAlias({"Nafn","name","nafn"})
    private String name;

    @JsonProperty(required = false)
    @JsonAlias({"Tegund", "type", "tegund", "Type"})
    private String type;

    @JsonProperty(required = false)
    @JsonAlias({"Stöðvanúmer", "id"})
    private long id;

    @JsonProperty(required = false)
    @JsonAlias({"WMO-númer", "WMO_number", "wmo_number"})
    private String WMO_number;

    @JsonProperty(required = false)
    @JsonAlias({"Skammstöfun", "shortName", "shortname"})
    private String shortName;

    @JsonProperty(required = false)
    @JsonAlias({"Spásvæði", "forecastArea", "forecastarea"})
    private String forecastArea;

    @JsonProperty(required = false)
    @JsonAlias({"Staðsetning", "coordinates", "staðsetning"})
    private String coordinates;

    @JsonProperty(required = false)
    @JsonAlias({"Hæð yfir sjó", "hightOverSea", "hæð yfir sjó", "hightoversea"})
    private String hightOverSea;

    @JsonProperty(required = false)
    @JsonAlias({"Upphaf veðurathuguna", "startOfMeasuring", "startofmeasuring", "upphaf veðurathuguna"})
    private String startOfMeasuring;

    @JsonProperty(required = false)
    @JsonAlias({"Eigandi stöðvar", "owner", "eigandi stöðvar"})
    private String owner;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public String getWMO_number() {
        return WMO_number;
    }

    public String getShortName() {
        return shortName;
    }

    public String getForecastArea() {
        return forecastArea;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getHightOverSea() {
        return hightOverSea;
    }

    public String getStartOfMeasuring() {
        return startOfMeasuring;
    }

    public String getOwner() {
        return owner;
    }

    public WeatherStation(String name, String type, long id, String WMO_number, String shortName, String forecastArea, String coordinates, String hightOverSea, String startOfMeasuring, String owner) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.WMO_number = WMO_number;
        this.shortName = shortName;
        this.forecastArea = forecastArea;
        this.coordinates = coordinates;
        this.hightOverSea = hightOverSea;
        this.startOfMeasuring = startOfMeasuring;
        this.owner = owner;
    }

    public WeatherStation() {
    }

    // TODO: IMPIMENT A LOOP THAT GOES THROUGH ALL STATIONS AND FETCHES ALL INFO ABOUT STATION WITH ID "id"
    public WeatherStation getStationWithId(int id, List<WeatherStations> allStations) {
        int n = allStations.size();
        WeatherStation newStation = null;
        WeatherStation currentStation;

        /*
        for(int i = 0; i < n; i++) {
            currentStation = allStations.get(i).getWeatherStation();
            if(allStations.get(i).getWeatherStation().id.equals(String.valueOf(i))) {
                newStation = new WeatherStation(
                        currentStation.name,
                        currentStation.type,
                        currentStation.id,
                        currentStation.WMO_number,
                        currentStation.shortName,
                        currentStation.forecastArea,
                        currentStation.coordinates,
                        currentStation.hightOverSea,
                        currentStation.startOfMeasuring,
                        currentStation.owner
                );
            }
        }
        */


        return newStation;
    }

    @JsonIgnore
    public LatLng getLatLng() {
        //"65°41.135', 18°06.014' (65,6856, 18,1002)"
        String s = this.coordinates;

        s = s.substring(s.indexOf("(") + 1);
        s = s.substring(0, s.indexOf(")"));

        String[] substrings = s.split(", ");

        String slat = substrings[0].replace(",",".");
        String slng = substrings[1].replace(",",".");

        slng = "-" + slng;

        double lat = Double.parseDouble(slat);
        double lng = Double.parseDouble(slng);

        return new LatLng(lat,lng);
    }

    @JsonIgnore
    public static List<LatLng> getAllStationLatLng(List<WeatherStation> allStations) {
        int n = allStations.size();
        List<LatLng> latLngList = new ArrayList<>();

        for (int i=0; i<n; i++) {
            WeatherStation currStation = allStations.get(i);

            latLngList.add(currStation.getLatLng());
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
        dest.writeString(this.coordinates);
        dest.writeString(this.forecastArea);
        dest.writeString(this.hightOverSea);
        dest.writeString(this.name);
        dest.writeString(this.owner);
        dest.writeString(this.shortName);
        dest.writeString(this.startOfMeasuring);
        dest.writeString(this.type);
        dest.writeString(this.WMO_number);
    }

    @JsonIgnore
    public WeatherStation(Parcel in) {
        this.id = in.readLong();
        this.coordinates = in.readString();
        this.forecastArea= in.readString();
        this.hightOverSea= in.readString();
        this.name= in.readString();
        this.owner= in.readString();
        this.shortName= in.readString();
        this.startOfMeasuring= in.readString();
        this.type= in.readString();
        this.WMO_number= in.readString();
    }

    @JsonIgnore
    public static final Parcelable.Creator<WeatherStation> CREATOR = new Parcelable.Creator<WeatherStation>()
    {
        public WeatherStation createFromParcel(Parcel in)
        {
            return new WeatherStation(in);
        }
        public WeatherStation[] newArray(int size)
        {
            return new WeatherStation[size];
        }
    };

}




