package com.example.stilla_app.Data.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

//@JsonRootName(value = "station")
public class WeatherStation {
    /*@JsonProperty(value = "Nafn", required = false)
    private String name;
    @JsonProperty(value = "Tegund", required = false)
    private String type;
    @JsonProperty(value = "Stöðvanúmer", required = false)
    private long id;
    @JsonProperty(value = "WMO-númer", required = false)
    private String WMO_number;
    @JsonProperty(value = "Skammstöfun", required = false)
    private String shortName;
    @JsonProperty(value = "Spásvæði", required = false)
    private String forecastArea;
    @JsonProperty(value = "Staðsetning", required = false)
    private String coordinates;
    @JsonProperty(value = "Hæð yfir sjó", required = false)
    private String hightOverSea;
    @JsonProperty(value = "Upphaf veðurathuguna", required = false)
    private String startOfMeasuring;
    @JsonProperty(value = "Eigandi stöðvar", required = false)
    private String owner;

     */
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

}




