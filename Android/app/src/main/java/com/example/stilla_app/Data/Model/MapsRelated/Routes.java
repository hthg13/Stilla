package com.example.stilla_app.Data.Model.MapsRelated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Routes {

    private List<Legs> legs;
    private Polyline overview_polyline;

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public Polyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }
}
