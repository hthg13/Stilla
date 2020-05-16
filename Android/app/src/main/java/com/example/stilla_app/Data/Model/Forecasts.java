package com.example.stilla_app.Data.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "forecasts", strict = false)
public class Forecasts {

    @Element(name = "station")
    private Station station;

    public Station getStation() {
        return station;
    }
}


