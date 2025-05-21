package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.service.StationService;

import java.util.List;

@RestController
@RequestMapping("/{operator_id}")
public class OperatorController {

    @Autowired
    private StationService stationService;
    @GetMapping("/stations")
    public List<Station> getStationsByOperator(@RequestParam int operator_id) {
        return stationService.getStationsByOperator(operator_id);
    }


}