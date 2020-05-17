package com.example.stilla_app.Data.Model.MapsRelated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LatLng getLatLng() {
        LatLng latlng = new LatLng(this.lat,this.lng);
        return latlng;
    }
}
