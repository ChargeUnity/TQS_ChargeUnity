package tqs.ChargeUnity.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.service.DriverService;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

  private final DriverService driverService;
  private final BookingService bookingService;

  public DriverController(DriverService driverService, BookingService bookingService) {
    this.bookingService = bookingService;
    this.driverService = driverService;
  }

  @GetMapping
  public ResponseEntity<?> getAllDrivers() {
    return ResponseEntity.ok(driverService.getAllDrivers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Driver> getDriverById(@PathVariable int id) {
    return driverService
        .getDriverById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<?> createDriver(@RequestBody Driver driver) {
    return ResponseEntity.status(HttpStatus.CREATED).body(driverService.createDriver(driver));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Driver> updateDriver(@PathVariable int id, @RequestBody Driver driver) {
    return driverService
        .updateDriver(id, driver)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDriver(@PathVariable int id) {
    if (driverService.deleteDriver(id)) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found");
    }
  }

  @GetMapping("/{id}/bookings")
  public ResponseEntity<List<Booking>> getBookingsByDriver(@PathVariable int id) {
    List<Booking> bookings = bookingService.getBookingsByDriver(id);

    return Optional.ofNullable(bookings)
        .filter(list -> !list.isEmpty())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList()));
  }
}
