package is.hi.lokaverkefni.stilla.stilla_backend.Entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.LatLng;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;



@Entity
@Table(name = "TRIP")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String name;

    @ElementCollection
    public List<String> transport = new ArrayList<>();

    public String start;

    public String finish;

    public boolean notify;

    @ElementCollection
    public List<String> places = new ArrayList<>();

    @OneToMany(mappedBy = "weatherstationtrip"/*, cascade = ALL, targetEntity = WeatherStation.class*/)
    //@JsonIgnoreProperties({"weatherstationtrip"})
    public List<WeatherStation> weatherStation = new ArrayList<>();

    @OneToMany(mappedBy = "weatherforecasttrip"/*, cascade = ALL, targetEntity = WeatherForecast.class*/)
    //@JsonIgnoreProperties({"weatherforecasttrip"})
    public List<WeatherForecast> weatherForecast = new ArrayList<>();

    @ElementCollection
    public List<String> googleDirectionList = new ArrayList<>();

    public List<String> getGoogleDirectionList() {
        return googleDirectionList;
    }

    public void setGoogleDirectionList(List<String> googleDirectionList) {
        this.googleDirectionList = googleDirectionList;
    }

    public Trip() {
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

    public List<WeatherStation> getWeatherStation() {
        return weatherStation;
    }

    public void setWeatherStation(List<WeatherStation> weatherStation) {
        this.weatherStation = weatherStation;
    }

    public List<WeatherForecast> getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(List<WeatherForecast> weatherForecast) {
        this.weatherForecast = weatherForecast;
    }
}
