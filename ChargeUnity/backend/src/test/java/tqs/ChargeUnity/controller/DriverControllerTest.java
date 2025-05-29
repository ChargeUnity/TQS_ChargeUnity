package tqs.ChargeUnity.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.service.DriverService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DriverControllerTest {

  @Mock private DriverService driverService;

  @Mock private BookingService bookingService;

  @InjectMocks private DriverController driverController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Requirement("CH-93")
  void getAllDrivers_ReturnsListOfDrivers() {
    List<Driver> drivers = Arrays.asList(new Driver(), new Driver());
    when(driverService.getAllDrivers()).thenReturn(drivers);

    ResponseEntity<?> response = driverController.getAllDrivers();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(drivers);
  }

  @Test
  @Requirement("CH-93")
  void getDriverById_Found_ReturnsDriver() {
    Driver driver = new Driver();
    when(driverService.getDriverById(1)).thenReturn(Optional.of(driver));

    ResponseEntity<Driver> response = driverController.getDriverById(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(driver);
  }

  @Test
  @Requirement("CH-93")
  void getDriverById_NotFound_ReturnsNotFound() {
    when(driverService.getDriverById(1)).thenReturn(Optional.empty());

    ResponseEntity<Driver> response = driverController.getDriverById(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNull();
  }

  @Test
  @Requirement("CH-93")
  void createDriver_ReturnsCreatedDriver() {
    Driver driver = new Driver();
    when(driverService.createDriver(driver)).thenReturn(driver);

    ResponseEntity<?> response = driverController.createDriver(driver);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isEqualTo(driver);
  }

  @Test
  @Requirement("CH-93")
  void updateDriver_Found_ReturnsUpdatedDriver() {
    Driver driver = new Driver();
    when(driverService.updateDriver(eq(1), any(Driver.class))).thenReturn(Optional.of(driver));

    ResponseEntity<Driver> response = driverController.updateDriver(1, driver);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(driver);
  }

  @Test
  @Requirement("CH-93")
  void updateDriver_NotFound_ReturnsNotFound() {
    Driver driver = new Driver();
    when(driverService.updateDriver(eq(1), any(Driver.class))).thenReturn(Optional.empty());

    ResponseEntity<Driver> response = driverController.updateDriver(1, driver);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNull();
  }

  @Test
  @Requirement("CH-93")
  void deleteDriver_Found_ReturnsNoContent() {
    when(driverService.deleteDriver(1)).thenReturn(true);

    ResponseEntity<?> response = driverController.deleteDriver(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
  }

  @Test
  @Requirement("CH-93")
  void deleteDriver_NotFound_ReturnsNotFound() {
    when(driverService.deleteDriver(1)).thenReturn(false);

    ResponseEntity<?> response = driverController.deleteDriver(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("Driver not found");
  }

  @Test
  @Requirement("CH-93")
  void getBookingsByDriver_Found_ReturnsBookings() {
    List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
    when(bookingService.getBookingsByDriver(1)).thenReturn(bookings);

    ResponseEntity<List<Booking>> response = driverController.getBookingsByDriver(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(bookings);
  }

  @Test
  @Requirement("CH-93")
  void getBookingsByDriver_NotFound_ReturnsNotFoundWithEmptyList() {
    when(bookingService.getBookingsByDriver(1)).thenReturn(Collections.emptyList());

    ResponseEntity<List<Booking>> response = driverController.getBookingsByDriver(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEmpty();
  }
}
