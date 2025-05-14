package tqs.ChargeUnity.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tqs.ChargeUnity.model.Trip;
import tqs.ChargeUnity.repository.TripRepository;

@Service
public class TripService {
    
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Optional<Trip> findById(int id) {
        return tripRepository.findById(id);
    }

    public Optional<Trip> save(Trip trip) {
        Trip saved = tripRepository.save(trip);
        return Optional.of(saved);
    }

    public Optional<Trip> update(Trip trip) {
        if (tripRepository.existsById(trip.getId())) {
            Trip updated = tripRepository.save(trip);
            return Optional.of(updated);
        } else {
            return Optional.empty();
        }
    }

    public void deleteById(int id) {
        tripRepository.deleteById(id);
    }
}