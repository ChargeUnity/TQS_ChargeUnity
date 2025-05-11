package tqs.ChargeUnity.service;

import java.util.ArrayList;
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
        return new ArrayList<>();
    }

    public Optional<Trip> findById(int id) {
        return Optional.empty();
    }

    public Optional<Trip> save(Trip trip) {
        return Optional.empty();
    }

    public Optional<Trip> update(Trip trip) {
        return Optional.empty();
    }

    public void deleteById(int id) {
    }
}
