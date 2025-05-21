package tqs.ChargeUnity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.DriverRepository;
import tqs.ChargeUnity.service.DriverService;

import java.util.Arrays;
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
    @DisplayName("Should return all drivers")
    void testFindAllDrivers() {
        List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
        when(driverRepository.findAll()).thenReturn(drivers);

        // This will fail until you implement findAll correctly
        List<Driver> result = driverService.findAll();

        assertEquals(2, result.size());
        verify(driverRepository).findAll();
    }

    @Test
    @DisplayName("Should return driver by ID if found")
    void testFindById_Found() {
        Driver driver = new Driver();
        driver.setId(1);
        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        // This will fail until you implement findById correctly
        Optional<Driver> result = driverService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(driverRepository).findById(1);
    }

    @Test
    @DisplayName("Should return empty if driver by ID not found")
    void testFindById_NotFound() {
        when(driverRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Driver> result = driverService.findById(99);

        assertFalse(result.isPresent());
        verify(driverRepository).findById(99);
    }

    @Test
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

    @Test
    @DisplayName("Should update a driver")
    void testUpdateDriver() {
        Driver existingDriver = new Driver();
        existingDriver.setId(1);
        existingDriver.setName("Original Name");
        existingDriver.setBalance(100.0);

        Driver updatedDriver = new Driver();
        updatedDriver.setId(1);
        updatedDriver.setName("Updated Name");
        updatedDriver.setBalance(200.0);

        when(driverRepository.findById(1)).thenReturn(Optional.of(existingDriver));
        when(driverRepository.save(existingDriver)).thenReturn(existingDriver);

        Optional<Driver> result = driverService.update(updatedDriver);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        assertEquals(200.0, result.get().getBalance());
        verify(driverRepository).findById(1);
        verify(driverRepository).save(existingDriver);
    }

    @Test
    @DisplayName("Should delete driver by ID")
    void testDeleteById() {
        when(driverRepository.existsById(1)).thenReturn(true);
        doNothing().when(driverRepository).deleteById(1);

        driverService.deleteById(1);

        verify(driverRepository).existsById(1);
        verify(driverRepository).deleteById(1);
    }
}