package is.hi.lokaverkefni.stilla.stilla_backend.Services;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherForecast;

import java.util.List;

public interface WeatherForecastService {
    WeatherForecast save(WeatherForecast weatherForecast);
    void delete(WeatherForecast weatherForecast);
    List<WeatherForecast> findAll();
}
