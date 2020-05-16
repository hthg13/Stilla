package is.hi.lokaverkefni.stilla.stilla_backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class WeatherStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne//(fetch = FetchType.LAZY)
    //@JoinColumn(name ="TRIPOWNER_ID")
    private Trip weatherstationtrip;

    private String name;
    private String type;
    private String WMO_number;
    private String shortName;
    private String forecastArea;
    private String coordinates;
    private String hightOverSea;
    private String startOfMeasuring;
    private String owner;

    public WeatherStation() {
    }

    public WeatherStation(Trip trip, String name, String type, String WMO_number, String shortName, String forecastArea, String coordinates, String hightOverSea, String startOfMeasuring, String owner) {
        this.weatherstationtrip = trip;
        this.name = name;
        this.type = type;
        this.WMO_number = WMO_number;
        this.shortName = shortName;
        this.forecastArea = forecastArea;
        this.coordinates = coordinates;
        this.hightOverSea = hightOverSea;
        this.startOfMeasuring = startOfMeasuring;
        this.owner = owner;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWMO_number() {
        return WMO_number;
    }

    public void setWMO_number(String WMO_number) {
        this.WMO_number = WMO_number;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getForecastArea() {
        return forecastArea;
    }

    public void setForecastArea(String forecastArea) {
        this.forecastArea = forecastArea;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getHightOverSea() {
        return hightOverSea;
    }

    public void setHightOverSea(String hightOverSea) {
        this.hightOverSea = hightOverSea;
    }

    public String getStartOfMeasuring() {
        return startOfMeasuring;
    }

    public void setStartOfMeasuring(String startOfMeasuring) {
        this.startOfMeasuring = startOfMeasuring;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
