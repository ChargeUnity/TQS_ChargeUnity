package tqs.ChargeUnity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.repository.OperatorRepository;

@Service
public class OperatorService {

  private final OperatorRepository operatorRepository;

  public OperatorService(OperatorRepository operatorRepository) {
    this.operatorRepository = operatorRepository;
  }

  public List<Operator> findAll() {
    return operatorRepository.findAll();
  }

  public Optional<Operator> findById(int id) {
    return operatorRepository.findById(id);
  }

  public Optional<Operator> findByName(String name) {
    return operatorRepository.findByName(name);
  }

  public Operator save(Operator operator) {
    return operatorRepository.save(operator);
  }

  public Optional<Operator> update(Operator operator) {
    return operatorRepository
        .findById(operator.getId())
        .map(
            existingOperator -> {
              existingOperator.setName(operator.getName());
              existingOperator.setStations(operator.getStations());
              return operatorRepository.save(existingOperator);
            });
  }

  public void deleteById(int id) {
    if (!operatorRepository.existsById(id)) {
      throw new RuntimeException("Operator with ID " + id + " not found.");
    }
    operatorRepository.deleteById(id);
  }
}
