package tqs.ChargeUnity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.dto.BookingRequestDTO;

@RestController
@RequestMapping("/bookings")
public class BookingController {

  @Autowired private BookingService bookingService;

  @GetMapping("/driver/{driverId}")
  public ResponseEntity<?> getBookingsByDriver(int driverId) {
    try {
      return ResponseEntity.ok(bookingService.getBookingsByDriver(driverId));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/charger/{chargerId}")
  public ResponseEntity<?> getBookingsByCharger(int chargerId) {
    try {
      return ResponseEntity.ok(bookingService.getBookingsByCharger(chargerId));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/create")
  public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO dto) {
    try {
      Booking created =
          bookingService.createBooking(
              dto.driverId(), dto.chargerId(), dto.startTime(), dto.endTime());
      return ResponseEntity.ok(created);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/{id}/start")
  public ResponseEntity<?> startCharging(int id) {
    try {
      Booking updated = bookingService.startCharging(id);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{id}/status")
  public ResponseEntity<?> getStatus(int id) {
    try {
      BookingStatus status = bookingService.getChargingStatus(id);
      return ResponseEntity.ok(status);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/{id}/stop")
  public ResponseEntity<?> stopCharging(int id) {
    try {
      Booking updated = bookingService.stopCharging(id);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/{id}/cancel")
  public ResponseEntity<?> cancelBooking(int id) {
    try {
      Booking updated = bookingService.cancelBooking(id);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
