package tqs.ChargeUnity.service;

import java.util.ArrayList;
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
    return new ArrayList<>();
  }

  public Optional<Operator> findById(int id) {
    return Optional.empty();
  }

  public Optional<Operator> findByName(String name) {
    return Optional.empty();
  }

  public Optional<Operator> save(Operator operator) {
    return Optional.empty();
  }

  public Optional<Operator> update(Operator operator) {
    return Optional.empty();
  }

  public void deleteById(int id) {}
}
