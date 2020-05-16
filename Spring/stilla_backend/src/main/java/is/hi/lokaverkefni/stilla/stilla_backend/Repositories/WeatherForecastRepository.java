package is.hi.lokaverkefni.stilla.stilla_backend.Repositories;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, Long> {
    WeatherForecast save(WeatherForecast weatherForecast);
    void delete(WeatherForecast weatherForecast);
    List<WeatherForecast> findAll();
}
