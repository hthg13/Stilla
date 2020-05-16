package is.hi.lokaverkefni.stilla.stilla_backend.Services.Implementations;

import is.hi.lokaverkefni.stilla.stilla_backend.Entities.Trip;
import is.hi.lokaverkefni.stilla.stilla_backend.Repositories.TripRepository;
import is.hi.lokaverkefni.stilla.stilla_backend.Services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository repository) {
        this.tripRepository = repository;
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip findById(long id) {
        return tripRepository.findById(id);
    }
}
