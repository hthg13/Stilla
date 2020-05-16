package is.hi.lokaverkefni.stilla.stilla_backend.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import is.hi.lokaverkefni.stilla.stilla_backend.Entities.Trip;
import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherForecast;
import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherStation;
import is.hi.lokaverkefni.stilla.stilla_backend.Services.TripService;
import is.hi.lokaverkefni.stilla.stilla_backend.Services.WeatherForecastService;
import is.hi.lokaverkefni.stilla.stilla_backend.Services.WeatherStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    private TripService tripService;
    private WeatherForecastService forecastService;
    private WeatherStationService stationService;

    @Autowired
    public HomeController(TripService tripService, WeatherForecastService forecastService, WeatherStationService stationService) {
        this.tripService = tripService;
        this.forecastService = forecastService;
        this.stationService = stationService;
    }

    /**
     * sets up the weather stations page of localhost
     * the object is a json string containing all information about all the weather stations in iceland
     */
    @RequestMapping(value = "/weather_stations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object getWeatherStations() {
        Resource resource = new ClassPathResource("json/weather_stations.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Creates a face trip and returns a Trip entitie
     */
    @RequestMapping(value = "/newTrip", method = RequestMethod.GET)
    public @ResponseBody Object makeTrip() {

        Trip trip = new Trip();

        List<String> places = new ArrayList<>();
        places.add(0, "Reykjavík");
        places.add(1, "Hafnafjörður");

        List<String> transport = new ArrayList<>();
        transport.add(0, "driving");
        transport.add(1, "walking");

        trip.setFinish("tomorrow");
        trip.setStart("today");
        trip.setName("ferðalagið mitt");
        trip.setNotify(false);
        trip.setPlaces(places);
        trip.setTransport(transport);
        trip.setWeatherStation(null);
        this.tripService.save(trip);

        WeatherForecast forecast = forecastService.save(new WeatherForecast(trip, "ftime", 5, "d", 2, "w", "v", "n", "TD", "r"));
        List<Trip> trips = tripService.findAll();

        return trip;
    }

    @RequestMapping(value = "/saveTrip", method = RequestMethod.POST)
    public Trip saveTrip(@RequestBody Trip trip, BindingResult result) {

        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error...");
        }

        Trip exists = tripService.findById(trip.getId());

        if(exists == null) {
            Trip tripReturn = tripService.save(trip);

            int numStations = trip.getWeatherStation().size();
            int numForecasts = trip.getWeatherForecast().size();

            if (numForecasts > 0) {
                for(int i=0; i<numForecasts; i++) {
                    WeatherForecast forecast = trip.getWeatherForecast().get(i);
                    forecastService.save(new WeatherForecast(trip, forecast.getFtime(), forecast.getF(),
                            forecast.getD(), forecast.getT(), forecast.getW(), forecast.getV(),
                            forecast.getN(), forecast.getTD(), forecast.getR()));
                }
            }

            if (numStations > 0) {
                for (int i=0; i<numStations; i++) {
                    WeatherStation station = trip.getWeatherStation().get(i);
                    stationService.save(new WeatherStation(trip, station.getName(), station.getType(),
                            station.getWMO_number(), station.getShortName(), station.getForecastArea(),
                            station.getCoordinates(), station.getHightOverSea(), station.getStartOfMeasuring(),
                            station.getOwner()));
                }

            }

            return tripReturn;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip unavailable");
        }
    }

    /*
    Returns all trips that have been created in this session
     */
    @RequestMapping(value = "/getTrips", method = RequestMethod.GET)
    public @ResponseBody Object tripGET() {
        List<Trip> trips = tripService.findAll();
        return trips;
    }
}
