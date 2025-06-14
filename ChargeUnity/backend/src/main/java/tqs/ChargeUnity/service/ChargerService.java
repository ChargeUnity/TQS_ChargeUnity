package tqs.ChargeUnity.service;

import org.springframework.stereotype.Service;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.repository.ChargerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChargerService {

  private final ChargerRepository chargerRepository;
  private final StationService stationService;

  public ChargerService(ChargerRepository chargerRepository, StationService stationService) {
    this.chargerRepository = chargerRepository;
    this.stationService = stationService;
  }

  public List<Charger> getAllChargers() {
    return chargerRepository.findAll();
  }

  public Optional<Charger> getChargerById(int id) {
    return chargerRepository.findById(id);
  }

  public Charger saveCharger(Charger charger) {
    return chargerRepository.save(charger);
  }

  public void deleteCharger(int id) {
    chargerRepository.deleteById(id);
  }

  public List<Charger> getChargersBySearch(Station station) {
    return chargerRepository.findByStation(station);
  }

  public List<Charger> getChargersByStatus(ChargerStatus status) {
    return chargerRepository.findByStatus(status);
  }

  public Charger updateChargerStatus(int id, ChargerStatus newStatus) {
    Optional<Charger> optionalCharger = chargerRepository.findById(id);
    if (optionalCharger.isPresent()) {
      Charger charger = optionalCharger.get();
      charger.setStatus(newStatus);
      return chargerRepository.save(charger);
    } else {
      throw new RuntimeException("Charger not found with id: " + id);
    }
  }

  public Object getChargersByStation(int stationId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getChargersByStation'");
  }

  public Optional<Station> getStationById(int stationId) {
    return stationService.getStationById(stationId);
  }
}
