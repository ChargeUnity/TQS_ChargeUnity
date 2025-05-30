package tqs.ChargeUnity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceTest {

  private DriverRepository driverRepository;
  private DriverService driverService;

  @BeforeEach
  void setUp() {
    driverRepository = mock(DriverRepository.class);
    driverService = new DriverService(driverRepository);
  }

  @Test
  @Requirement("CH-29")
  @DisplayName("Should return driver by ID if found")
  void testFindById_Found() {
    Driver driver = new Driver();
    driver.setId(1);
    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

    // This will fail until you implement findById correctly
    Optional<Driver> result = driverService.findById(1);

    assertTrue(result.isPresent());
    assertEquals(1, result.get().getId());
  }

  @Test
  @Requirement("CH-29")
  void testFindAllDrivers() {
    List<Driver> drivers = List.of(new Driver(), new Driver());
    when(driverRepository.findAll()).thenReturn(drivers);

    List<Driver> result = driverService.findAll();

    assertEquals(2, result.size());
    verify(driverRepository, times(1)).findAll();
  }

  @Test
  @Requirement("CH-29")
  void testFindByNameReturnsEmpty() {
    Optional<Driver> result = driverService.findByName("John Doe");
    assertTrue(result.isEmpty());
  }

  @Test
  @Requirement("CH-29")
  void testUpdateReturnsEmpty() {
    Driver driver = new Driver();
    Optional<Driver> result = driverService.update(driver);
    assertTrue(result.isEmpty());
  }

  @Test
  @Requirement("CH-29")
  void testDeleteById() {
    doNothing().when(driverRepository).deleteById(1);
    when(driverRepository.existsById(1)).thenReturn(true);

    driverService.deleteById(1);

    verify(driverRepository, times(1)).deleteById(1);
  }

  @Test
  @Requirement("CH-29")
  @DisplayName("Should return empty if driver by ID not found")
  void testFindById_NotFound() {
    when(driverRepository.findById(99)).thenReturn(Optional.empty());

    Optional<Driver> result = driverService.findById(99);

    assertFalse(result.isPresent());
    verify(driverRepository).findById(99);
  }

  @Test
  @Requirement("CH-29")
  @DisplayName("Should return driver by name")
  void testFindByName() {
    Driver driver = new Driver();
    driver.setName("Alice");
    when(driverRepository.findByName("Alice")).thenReturn(Optional.of(driver));

    Optional<Driver> result = driverService.findByName("Alice");

    assertTrue(result.isPresent());
    assertEquals("Alice", result.get().getName());
    verify(driverRepository).findByName("Alice");
  }

  @Test
  @Requirement("CH-29")
  @DisplayName("Should save a driver")
  void testSaveDriver() {
    Driver driver = new Driver();
    driver.setName("Bob");
    when(driverRepository.save(driver)).thenReturn(driver);

    // This will fail until you implement save correctly
    Optional<Driver> result = driverService.save(driver);

    assertTrue(result.isPresent());
    assertEquals("Bob", result.get().getName());
    verify(driverRepository).save(driver);
  }
}
