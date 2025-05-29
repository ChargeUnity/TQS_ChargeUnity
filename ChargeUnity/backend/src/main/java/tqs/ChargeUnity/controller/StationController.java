package tqs.ChargeUnity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.service.OperatorService;
import tqs.ChargeUnity.service.StationService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/station")
public class StationController {

  private final StationService stationService;
  private OperatorService operatorService;

  public StationController(StationService stationService, OperatorService operatorService) {
    this.stationService = stationService;
    this.operatorService = operatorService;
  }

  @PostMapping
  public ResponseEntity<?> createStation(@RequestBody Map<String, Object> payload) {
    int operatorId = (int) payload.get("operatorId");
    Optional<Operator> operatorOpt = operatorService.findById(operatorId);

    if (operatorOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator not found");
    }

    Station station = new Station();
    station.setName((String) payload.get("name"));
    station.setCity((String) payload.get("city"));
    station.setAddress((String) payload.get("address"));
    station.setLatitude(String.valueOf(payload.get("latitude")));
    station.setLongitude(String.valueOf(payload.get("longitude")));

    Operator operator = operatorOpt.get();
    station.setOperator(operator);

    Station createdStation = stationService.addStation(station);

    return new ResponseEntity<>(createdStation, HttpStatus.CREATED);
  }

  @GetMapping
  public List<Station> getAllStations() {
    return stationService.getAllStations();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getStationById(@PathVariable int id) {
    Optional<Station> optionalStation = stationService.getStationById(id);
    if (optionalStation.isPresent()) {
      return ResponseEntity.ok(optionalStation.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Station not found");
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateStation(@PathVariable int id, @RequestBody Station updated) {
    try {
      Station station = stationService.updateStation(id, updated);
      return ResponseEntity.ok(station);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteStation(@PathVariable int id) {
    try {
      stationService.deleteStation(id);
      return ResponseEntity.ok("Station deleted successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @GetMapping("coordinates/{latitude}/{longitude}/{radius}")
  public ResponseEntity<?> getStationsByLocation(
      @PathVariable double latitude, @PathVariable double longitude, @PathVariable double radius) {
    List<Station> stations = stationService.getStationsByLocation(latitude, longitude, radius);
    return ResponseEntity.ok(stations);
  }

  @GetMapping("/city/{city}")
  public ResponseEntity<?> getStationsByCity(@PathVariable String city) {
    List<Station> stations = stationService.getStationsByCity(city);
    return ResponseEntity.ok(stations);
  }
}
