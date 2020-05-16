package is.hi.lokaverkefni.stilla.stilla_backend.Services.Implementations;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherStation;
import is.hi.lokaverkefni.stilla.stilla_backend.Repositories.WeatherStationRepository;
import is.hi.lokaverkefni.stilla.stilla_backend.Services.WeatherStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherStationServiceImpl implements WeatherStationService {
    WeatherStationRepository weatherStationRepository;

    @Autowired
    public WeatherStationServiceImpl(WeatherStationRepository repository) {
        this.weatherStationRepository = repository;
    }

    @Override
    public WeatherStation save(WeatherStation weatherStation) {
        return weatherStationRepository.save(weatherStation);
    }

    @Override
    public void delete(WeatherStation weatherStation) {
        weatherStationRepository.delete(weatherStation);
    }

    @Override
    public List<WeatherStation> findAll() {
        return weatherStationRepository.findAll();
    }


}
