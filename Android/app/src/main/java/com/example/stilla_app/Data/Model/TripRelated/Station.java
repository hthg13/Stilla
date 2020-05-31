package com.example.stilla_app.Data.Model.TripRelated;

import com.example.stilla_app.Data.Model.TripRelated.Forecast;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Root(name = "station", strict = false)
public class Station {

    @Attribute(name = "id")
    private long id;

    @Attribute(name = "valid", required = false)
    private boolean valid;

    @Element(name = "name", required = false)
    private String name;

    @Element(name = "atime", required = false)
    private String atime;

    @Element(name = "err", required = false)
    private String err;

    @Element(name = "link", data = true, required = false)
    private String link;

    @ElementList(inline = true, required = false)
    private List<Forecast> forecast;

    public long getId() {
        return id;
    }

    public boolean isValid() {
        return valid;
    }

    public String getName() {
        return name;
    }

    public String getAtime() {
        return atime;
    }

    public String getErr() {
        return err;
    }

    public String getLink() {
        return link;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

}

