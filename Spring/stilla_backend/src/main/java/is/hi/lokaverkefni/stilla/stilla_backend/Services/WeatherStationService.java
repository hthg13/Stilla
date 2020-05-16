package is.hi.lokaverkefni.stilla.stilla_backend.Services;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherStation;

import java.util.List;

public interface WeatherStationService {
    WeatherStation save(WeatherStation weatherStation);
    void delete(WeatherStation weatherStation);
    List<WeatherStation> findAll();
}
