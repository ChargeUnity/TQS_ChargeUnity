package tqs.ChargeUnity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.StationRepository;

@Service
public class StationService {
    
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<Station> findAll() {
        return new ArrayList<>();
    }

    public Optional<Station> findById(int id) {
        return Optional.empty();
    }

    public Optional<Station> findByName(String name) {
        return Optional.empty();
    }

    public Optional<Station> save(Station station) {
        return Optional.empty();
    }

    public Optional<Station> update(Station station) {
        return Optional.empty();
    }

    public void deleteById(int id) {
    }
}
