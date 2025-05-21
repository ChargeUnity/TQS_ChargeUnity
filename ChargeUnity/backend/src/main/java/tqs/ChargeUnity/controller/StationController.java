package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.service.ChargerService;
import tqs.ChargeUnity.service.StationService;
@RestController
@RequestMapping("/api/stations")
public class StationController {

    @Autowired
    private ChargerService chargerService;

    @Autowired
    private StationService stationService;


    @GetMapping("/{stationId}")
    public ResponseEntity<?> getStationInfo(@PathVariable int stationId) {
        Optional<Station> stationOpt = stationService.getStationById(stationId);
        if (stationOpt.isPresent()) {
            return ResponseEntity.ok(stationOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Station with ID " + stationId + " not found.");
        }
    }
}
    
