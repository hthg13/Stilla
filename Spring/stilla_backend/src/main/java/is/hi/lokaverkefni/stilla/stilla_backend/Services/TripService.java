package is.hi.lokaverkefni.stilla.stilla_backend.Services;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.Trip;

import java.util.List;

public interface TripService {
    Trip save(Trip trip);
    void delete(Trip trip);
    List<Trip> findAll();
    Trip findById(long id);
}
