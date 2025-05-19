package tqs.ChargeUnity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.StationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StationService {

  @Autowired private StationRepository stationRepository;

  public List<Station> getAllStations() {
    return stationRepository.findAll();
  }

  public Optional<Station> getStationById(int id) {
    return stationRepository.findById(id);
  }

  public Station addStation(Station station) {
    return stationRepository.save(station);
  }

  public void deleteStation(int id) {
    if (!stationRepository.existsById(id)) {
      throw new RuntimeException("Station with ID " + id + " not found.");
    }
    stationRepository.deleteById(id);
  }

  public Station updateStation(int id, Station updated) {
    Station station =
        stationRepository.findById(id).orElseThrow(() -> new RuntimeException("Station not found"));

    station.setName(updated.getName());
    station.setAddress(updated.getAddress());
    station.setCity(updated.getCity());
    station.setLatitude(updated.getLatitude());
    station.setLongitude(updated.getLongitude());

    return stationRepository.save(station);
  }

  public List<Station> getStationsByCity(String city) {
    return stationRepository.findByCityIgnoreCase(city);
  }
}
