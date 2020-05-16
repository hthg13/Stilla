package is.hi.lokaverkefni.stilla.stilla_backend.Repositories;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherStationRepository extends JpaRepository<WeatherStation, Long> {
    WeatherStation save(WeatherStation weatherStation);
    void delete(WeatherStation weatherStation);
    List<WeatherStation> findAll();
}
