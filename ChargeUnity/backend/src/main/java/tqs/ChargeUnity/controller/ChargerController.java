package tqs.ChargeUnity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.enums.ChargerType; 
import tqs.ChargeUnity.service.ChargerService;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.repository.StationRepository;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/charger")
public class ChargerController {

  private final ChargerRepository chargerRepository;

  private final ChargerService chargerService;

  public ChargerController(
      ChargerRepository chargerRepository,
      ChargerService chargerService) {
    this.chargerRepository = chargerRepository;
    this.chargerService = chargerService;
  }

  @PostMapping
  public ResponseEntity<?> createCharger(
      @RequestBody Map<String, Object> requestBody) {

    Charger charger = new Charger();

    String stationId = (String) requestBody.get("stationId");
    Station station =
        chargerService
            .getStationById(Integer.parseInt(stationId))
            .orElseThrow(() -> new RuntimeException("Station not found"));
    charger.setStation(station);
    charger.setStatus(ChargerStatus.valueOf((String) requestBody.get("status")));
    charger.setType(ChargerType.valueOf((String) requestBody.get("chargerType")));
    Object priceObj = requestBody.get("pricePerKWh");
    if (priceObj instanceof Number) {
        charger.setPricePerKWh(((Number) priceObj).doubleValue());
    } else if (priceObj instanceof String) {
        charger.setPricePerKWh(Double.parseDouble((String) priceObj));
    } else {
        throw new RuntimeException("Invalid pricePerKWh type: " + priceObj);
    }


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
        chargerService
            .getStationById(stationId)
            .orElseThrow(() -> new RuntimeException("Station not found"));

    return ResponseEntity.ok(chargerRepository.findByStationId(station.getId()));
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
