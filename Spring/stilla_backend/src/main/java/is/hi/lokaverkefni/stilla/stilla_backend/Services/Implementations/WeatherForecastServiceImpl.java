package is.hi.lokaverkefni.stilla.stilla_backend.Services.Implementations;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherForecast;
import is.hi.lokaverkefni.stilla.stilla_backend.Repositories.WeatherForecastRepository;
import is.hi.lokaverkefni.stilla.stilla_backend.Services.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {
    WeatherForecastRepository weatherForecastRepository;

    @Autowired
    public WeatherForecastServiceImpl(WeatherForecastRepository repository) {
        this.weatherForecastRepository = repository;
    }

    @Override
    public WeatherForecast save(WeatherForecast weatherForecast) {
        return weatherForecastRepository.save(weatherForecast);
    }

    @Override
    public void delete(WeatherForecast weatherForecast) {
        weatherForecastRepository.delete(weatherForecast);
    }

    @Override
    public List<WeatherForecast> findAll() {
        return weatherForecastRepository.findAll();
    }
}
