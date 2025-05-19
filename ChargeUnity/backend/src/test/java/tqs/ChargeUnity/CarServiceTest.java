package tqs.ChargeUnity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
    @DisplayName("Should return all cars for a specific user (driver)")
    @Test
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertEquals(2, result.size());
        verify(carRepository).findAll();
    }
    @Test
    void testGetCarById_Found() {
        Car car = new Car();
        when(carRepository.findById(1)).thenReturn(Optional.of(car));

        Optional<Car> result = carService.getCarById(1);

        assertTrue(result.isPresent());
        assertEquals(car, result.get());
    }

    @Test
    void testGetCarsByDriverId() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.findByDriverId(5)).thenReturn(cars);

        List<Car> result = carService.getCarsByDriverId(5);

        assertEquals(2, result.size());
        verify(carRepository).findByDriverId(5);
    }

    @Test
    void testAddCarForDriver_Success() {
        Car car = new Car();
        Driver driver = new Driver();
        driver.setId(3);
        when(driverRepository.findById(3)).thenReturn(Optional.of(driver));
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.addCarForDriver(car, 3);

        assertEquals(driver, result.getDriver());
        verify(carRepository).save(car);
    }

    @Test
    void testAddCarForDriver_DriverNotFound() {
        Car car = new Car();
        when(driverRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.addCarForDriver(car, 999);
        });

        assertEquals("Driver not found with ID: 999", exception.getMessage());
    }

    @Test
    void testDeleteCar_Success() {
        Driver driver = new Driver();
        driver.setId(1);

        Car car = new Car();
        car.setId(10);
        car.setDriver(driver);

        when(carRepository.findById(10)).thenReturn(Optional.of(car));

        carService.deleteCar(10, 1);

        verify(carRepository).deleteById(10);
    }

    @Test
    void testDeleteCar_NotFound() {
        when(carRepository.findById(10)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.deleteCar(10, 1);
        });

        assertEquals("Car not found with ID: 10", exception.getMessage());
    }

    @Test
    void testDeleteCar_Unauthorized() {
        Driver driver = new Driver();
        driver.setId(1);

        Car car = new Car();
        car.setId(10);
        car.setDriver(driver);

        when(carRepository.findById(10)).thenReturn(Optional.of(car));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.deleteCar(10, 2); // different driver ID
        });

        assertEquals("Unauthorized: Car doesn't belong to driver ID 2", exception.getMessage());
    }
    @DisplayName("Should add a car to a driver when the driver exists")
    @Test
    void shouldAddCarToDriverWhenDriverExists() {
        // Arrange
        Car car = new Car();
        Driver driver = new Driver();
        driver.setId(3);
        driver.setName("John Doe");
        when(driverRepository.findById(3)).thenReturn(Optional.of(driver));
        when(carRepository.save(car)).thenReturn(car);

        // Act
        Car result = carService.addCarForDriver(car, 3);

        // Assert
        assertNotNull(result.getDriver());
        assertEquals(3, result.getDriver().getId());
        assertEquals("John Doe", result.getDriver().getName());
        verify(driverRepository).findById(3);
        verify(carRepository).save(car);
        verifyNoMoreInteractions(driverRepository, carRepository);
    }
}
