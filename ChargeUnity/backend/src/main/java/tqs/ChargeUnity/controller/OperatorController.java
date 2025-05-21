package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.OperatorRepository;
import tqs.ChargeUnity.repository.StationRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/operator")
public class OperatorController {

    private final OperatorRepository operatorRepository;
    private final StationRepository stationRepository;

    @Autowired
    public OperatorController(OperatorRepository operatorRepository, StationRepository stationRepository) {
        this.operatorRepository = operatorRepository;
        this.stationRepository = stationRepository;
    }

    @PostMapping
    public ResponseEntity<?> createOperator(@RequestBody Operator operator) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operatorRepository.save(operator));
    }


    @GetMapping
    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOperatorById(@PathVariable int id) {
        Optional<Operator> optionalOperator = operatorRepository.findById(id);
        if (optionalOperator.isPresent()) {
            return ResponseEntity.ok(optionalOperator.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator not found");
        }
    }
    
}
