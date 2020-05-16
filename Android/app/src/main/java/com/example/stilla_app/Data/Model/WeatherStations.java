package com.example.stilla_app.Data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherStations {

    @JsonProperty(value = "station", required = false)
    private WeatherStation station;

    public WeatherStation getWeatherStation() {
        return station;
    }

    public void setStation(WeatherStation station) {
        this.station = station;
    }
}
