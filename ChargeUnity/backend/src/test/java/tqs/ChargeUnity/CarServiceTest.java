package tqs.ChargeUnity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tqs.ChargeUnity.model.Car;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.CarRepository;
import tqs.ChargeUnity.repository.DriverRepository;
import tqs.ChargeUnity.service.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

  private CarRepository carRepository;
  private DriverRepository driverRepository;
  private CarService carService;

  @BeforeEach
  void setUp() {
    carRepository = mock(CarRepository.class);
    driverRepository = mock(DriverRepository.class);
    carService = new CarService(carRepository, driverRepository);
  }

  @Test
  @DisplayName("Should return all cars")
  void testGetAllCars() {
    List<Car> cars = Arrays.asList(new Car(), new Car());
    when(carRepository.findAll()).thenReturn(cars);

    List<Car> result = carService.getAllCars();

    assertEquals(2, result.size());
    verify(carRepository).findAll();
    verifyNoMoreInteractions(carRepository);
  }

  @Test
  @DisplayName("Should return car by ID if exists")
  void testGetCarById_Found() {
    Car car = new Car();
    when(carRepository.findById(1)).thenReturn(Optional.of(car));

    Optional<Car> result = carService.getCarById(1);

    assertTrue(result.isPresent());
    assertEquals(car, result.get());
    verify(carRepository).findById(1);
    verifyNoMoreInteractions(carRepository);
  }

  @Test
  @DisplayName("Should return empty optional when car ID not found")
  void testGetCarById_NotFound() {
    when(carRepository.findById(1)).thenReturn(Optional.empty());

    Optional<Car> result = carService.getCarById(1);

    assertFalse(result.isPresent());
    verify(carRepository).findById(1);
    verifyNoMoreInteractions(carRepository);
  }

  @Test
  @DisplayName("Should return cars by driver ID")
  void testGetCarsByDriverId() {
    List<Car> cars = Arrays.asList(new Car(), new Car());
    when(carRepository.findByDriverId(5)).thenReturn(cars);

    List<Car> result = carService.getCarsByDriverId(5);

    assertEquals(2, result.size());
    verify(carRepository).findByDriverId(5);
    verifyNoMoreInteractions(carRepository);
  }

  @Test
  @DisplayName("Should add a car to a driver when the driver exists")
  void testAddCarForDriver_Success() {
    Car car = new Car();
    Driver driver = new Driver();
    driver.setId(3);
    driver.setName("John Doe");

    when(driverRepository.findById(3)).thenReturn(Optional.of(driver));
    when(carRepository.save(car)).thenReturn(car);

    Car result = carService.addCarForDriver(car, 3);

    assertNotNull(result.getDriver());
    assertEquals(3, result.getDriver().getId());
    assertEquals("John Doe", result.getDriver().getName());
    verify(driverRepository).findById(3);
    verify(carRepository).save(car);
    verifyNoMoreInteractions(driverRepository, carRepository);
  }

  @Test
  @DisplayName("Should throw exception when driver not found while adding car")
  void testAddCarForDriver_DriverNotFound() {
    Car car = new Car();
    when(driverRepository.findById(999)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> carService.addCarForDriver(car, 999));

    assertEquals("Driver not found with ID: 999", exception.getMessage());
    verify(driverRepository).findById(999);
    verifyNoMoreInteractions(driverRepository, carRepository);
  }

  @Test
  @DisplayName("Should delete car if it belongs to the driver")
  void testDeleteCar_Success() {
    Driver driver = new Driver();
    driver.setId(1);

    Car car = new Car();
    car.setId(10);
    car.setDriver(driver);

    when(carRepository.findById(10)).thenReturn(Optional.of(car));

    carService.deleteCar(10, 1);

    verify(carRepository).findById(10);
    verify(carRepository).deleteById(10);
    verifyNoMoreInteractions(carRepository);
  }

  @Test
  @DisplayName("Should throw exception when car not found during deletion")
  void testDeleteCar_NotFound() {
    when(carRepository.findById(10)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> carService.deleteCar(10, 1));

    assertEquals("Car not found with ID: 10", exception.getMessage());
    verify(carRepository).findById(10);
    verifyNoMoreInteractions(carRepository);
  }

  @Test
  @DisplayName("Should throw exception when driver is not the car owner")
  void testDeleteCar_Unauthorized() {
    Driver driver = new Driver();
    driver.setId(1);

    Car car = new Car();
    car.setId(10);
    car.setDriver(driver);

    when(carRepository.findById(10)).thenReturn(Optional.of(car));

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> carService.deleteCar(10, 2));

    assertEquals("Unauthorized: Car doesn't belong to driver ID 2", exception.getMessage());
    verify(carRepository).findById(10);
    verifyNoMoreInteractions(carRepository);
  }
}
