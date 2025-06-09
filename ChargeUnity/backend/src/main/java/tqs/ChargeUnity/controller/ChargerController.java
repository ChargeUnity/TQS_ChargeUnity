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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/charger")
public class ChargerController {

  private final ChargerRepository chargerRepository;
  private final ChargerService chargerService;

  public ChargerController(ChargerRepository chargerRepository, ChargerService chargerService) {
    this.chargerRepository = chargerRepository;
    this.chargerService = chargerService;
  }

  @PostMapping
  public ResponseEntity<?> createCharger(@RequestBody Map<String, Object> requestBody) {
    Charger charger = new Charger();

    Object stationIdRaw = requestBody.get("stationId");
    if (stationIdRaw == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing stationId");
    }
    int stationId;
    if (stationIdRaw instanceof Integer) {
      stationId = (Integer) stationIdRaw;
    } else if (stationIdRaw instanceof String) {
      stationId = Integer.parseInt((String) stationIdRaw);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid stationId type");
    }

    Optional<Station> stationOpt = chargerService.getStationById(stationId);
    if (stationOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Station not found");
    }
    charger.setStation(stationOpt.get());

    String statusStr = (String) requestBody.get("status");
    if (statusStr == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing status");
    }
    charger.setStatus(ChargerStatus.valueOf(statusStr));

    String chargerTypeStr = (String) requestBody.get("chargerType");
    if (chargerTypeStr == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing chargerType");
    }
    charger.setType(ChargerType.valueOf(chargerTypeStr));

    Object priceObj = requestBody.get("pricePerKWh");
    if (priceObj == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing pricePerKWh");
    }
    double price;
    if (priceObj instanceof Number) {
      price = ((Number) priceObj).doubleValue();
    } else if (priceObj instanceof String) {
      price = Double.parseDouble((String) priceObj);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid pricePerKWh type");
    }
    charger.setPricePerKWh(price);

    return ResponseEntity.status(HttpStatus.CREATED).body(chargerRepository.save(charger));
  }

  @GetMapping
  public ResponseEntity<?> getAllChargers() {
    return ResponseEntity.ok(chargerRepository.findAll());
  }

  @GetMapping("/available")
  public ResponseEntity<?> getAvailableChargers() {
    return ResponseEntity.ok(chargerRepository.findByStatus(ChargerStatus.AVAILABLE));
  }

  @GetMapping("/filter")
  public ResponseEntity<?> filterChargers(
      @RequestParam(required = false) Double power, @RequestParam(required = false) String status) {
    // only supports status for now
    if (status != null) {
      return ResponseEntity.ok(chargerRepository.findByStatus(ChargerStatus.valueOf(status)));
    }
    return ResponseEntity.badRequest().body("No supported filter provided");
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getChargerById(@PathVariable int id) {
    return chargerRepository
        .findById(id)
        .<ResponseEntity<?>>map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Charger not found"));
  }

  @GetMapping("/station/{stationId}")
  public ResponseEntity<?> getChargersByStation(@PathVariable int stationId) {
    Optional<Station> stationOpt = chargerService.getStationById(stationId);
    if (stationOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Station not found");
    }
    return ResponseEntity.ok(chargerRepository.findByStationId(stationId));
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
