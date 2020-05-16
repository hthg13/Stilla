package is.hi.lokaverkefni.stilla.stilla_backend.Repositories;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAll();
    Trip save(Trip trip);
    void delete(Trip trip);
    Trip findById(long id);
}
