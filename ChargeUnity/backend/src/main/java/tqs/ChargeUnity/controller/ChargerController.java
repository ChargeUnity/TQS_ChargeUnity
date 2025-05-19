package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.service.ChargerService;
import tqs.ChargeUnity.service.StationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stations/{stationId}/chargers")
public class ChargerController {

}