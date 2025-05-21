package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.model.Charger;

import tqs.ChargeUnity.service.ChargerService;




@RestController
@RequestMapping("/api/{charger_Id}")
public class ChargerController {

    @Autowired
    private ChargerService chargerService;


    //get chargers by city

    @GetMapping
    //update charger status
    @PutMapping("/{chargerId}/status")
    public ResponseEntity<?> updateChargerStatus(@PathVariable int chargerId, @RequestParam String status) {
        try {
            Charger updatedCharger = chargerService.updateChargerStatus(chargerId, ChargerStatus.valueOf(status));
            return ResponseEntity.ok(updatedCharger);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
