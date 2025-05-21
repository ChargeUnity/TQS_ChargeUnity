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
    return operatorRepository.findAll().stream()
        .filter(op -> op.getName().equalsIgnoreCase(name))
        .findFirst();
  }

  public Operator save(Operator operator) {
    return operatorRepository.save(operator);
  }

  public Operator update(Operator operator) {
    if (!operatorRepository.existsById(operator.getId())) {
      throw new RuntimeException("Operator with ID " + operator.getId() + " not found.");
    }
    return operatorRepository.save(operator);
  }

  public void deleteById(int id) {
    if (!operatorRepository.existsById(id)) {
      throw new RuntimeException("Operator with ID " + id + " not found.");
    }
    operatorRepository.deleteById(id);
  }
}
