package tqs.ChargeUnity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDriverById() {
        Driver driver = new Driver();
        driver.setId(1);
        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        Optional<Driver> result = driverService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testSaveDriver() {
        Driver driver = new Driver();
        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverService.save(driver);

        assertNotNull(result);
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void testFindAllDrivers() {
        List<Driver> drivers = List.of(new Driver(), new Driver());
        when(driverRepository.findAll()).thenReturn(drivers);

        List<Driver> result = driverService.findAll();

        assertEquals(2, result.size());
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    void testFindByNameReturnsEmpty() {
        Optional<Driver> result = driverService.findByName("John Doe");
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateReturnsEmpty() {
        Driver driver = new Driver();
        Optional<Driver> result = driverService.update(driver);
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteById() {
        doNothing().when(driverRepository).deleteById(1);

        driverService.deleteById(1);

        verify(driverRepository, times(1)).deleteById(1);
    }

}
