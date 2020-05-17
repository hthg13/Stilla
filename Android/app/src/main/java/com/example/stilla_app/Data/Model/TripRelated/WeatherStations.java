package com.example.stilla_app.Data.Model.TripRelated;

import com.fasterxml.jackson.annotation.JsonProperty;

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
