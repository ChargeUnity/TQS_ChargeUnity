package tqs.ChargeUnity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.service.ChargerService;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.repository.StationRepository;

@RestController
@RequestMapping("/api/v1/charger")
public class ChargerController {

  private final StationRepository stationRepository;
  private final ChargerRepository chargerRepository;

  private final ChargerService chargerService;

  public ChargerController(
      StationRepository stationRepository,
      ChargerRepository chargerRepository,
      ChargerService chargerService) {
    this.stationRepository = stationRepository;
    this.chargerRepository = chargerRepository;
    this.chargerService = chargerService;
  }

  @PostMapping
  public ResponseEntity<?> createCharger(
      @PathVariable int stationId, @RequestBody Charger charger) {

    Station station =
        stationRepository
            .findById(stationId)
            .orElseThrow(() -> new RuntimeException("Station not found"));

    charger.setStation(station);
    return ResponseEntity.status(HttpStatus.CREATED).body(chargerRepository.save(charger));
  }

  @GetMapping
  public ResponseEntity<?> getAllChargers() {
    return ResponseEntity.ok(chargerRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Charger> getChargerById(@PathVariable int id) {
      return chargerRepository
          .findById(id)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/station/{stationId}")
  public ResponseEntity<?> getChargersByStation(@PathVariable int stationId) {
    Station station =
        stationRepository
            .findById(stationId)
            .orElseThrow(() -> new RuntimeException("Station not found"));

    return ResponseEntity.ok(chargerRepository.findByStation(station));
  }

  @PatchMapping("/{chargerId}/status")
  public ResponseEntity<?> updateChargerStatus(
      @PathVariable int chargerId, @RequestParam String status) {
    try {
      Charger updatedCharger =
          chargerService.updateChargerStatus(chargerId, ChargerStatus.valueOf(status));
      return ResponseEntity.ok(updatedCharger);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
