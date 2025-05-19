package tqs.ChargeUnity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.DriverRepository;

@Service
public class DriverService {

  private final DriverRepository driverRepository;

  public DriverService(DriverRepository driverRepository) {
    this.driverRepository = driverRepository;
  }

  public List<Driver> findAll() {
    return new ArrayList<>();
  }

  public Optional<Driver> findById(int id) {
    return Optional.empty();
  }

  public Optional<Driver> findByName(String name) {
    return Optional.empty();
  }

  public Optional<Driver> save(Driver driver) {
    return Optional.empty();
  }

  public Optional<Driver> update(Driver driver) {
    return Optional.empty();
  }

  public void deleteById(int id) {}
}
