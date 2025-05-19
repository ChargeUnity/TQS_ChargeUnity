package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.service.ChargerService;

@RestController
@RequestMapping("/api/stations/{stationId}/chargers")
public class StationController {

    private final ChargerService chargerService;

    @Autowired
    public StationController(ChargerService chargerService) {
        this.chargerService = chargerService;
    }

    @PatchMapping("/{chargerId}/status")
    public ResponseEntity<Charger> updateChargerStatus(
            @PathVariable int stationId,
            @PathVariable int chargerId,
            @RequestParam ChargerStatus status) {
        try {
            Charger updatedCharger = chargerService.updateChargerStatus(chargerId, status);
            return ResponseEntity.ok(updatedCharger);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
