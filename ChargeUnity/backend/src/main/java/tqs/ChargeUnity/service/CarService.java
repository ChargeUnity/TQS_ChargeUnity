package tqs.ChargeUnity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.ChargeUnity.model.Car;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.CarRepository;
import tqs.ChargeUnity.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

  private final CarRepository carRepository;
  private final DriverRepository driverRepository;

  @Autowired
  public CarService(CarRepository carRepository, DriverRepository driverRepository) {
    this.carRepository = carRepository;
    this.driverRepository = driverRepository;
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  public Optional<Car> getCarById(int id) {
    return carRepository.findById(id);
  }

  public List<Car> getCarsByDriverId(int driverId) {
    return carRepository.findByDriverId(driverId);
  }

  public Car addCarForDriver(Car car, int driverId) {
    Driver driver =
        driverRepository
            .findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found with ID: " + driverId));

    car.setDriver(driver);
    return carRepository.save(car);
  }

  // should i add an update?
  public void deleteCar(int id, int driverId) {
    Optional<Car> carOpt = carRepository.findById(id);
    if (carOpt.isEmpty()) {
      throw new RuntimeException("Car not found with ID: " + id);
    }

    Car car = carOpt.get();
    if (car.getDriver().getId() != driverId) {
      throw new RuntimeException("Unauthorized: Car doesn't belong to driver ID " + driverId);
    }

    carRepository.deleteById(id);
  }
}
