package tqs.ChargeUnity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.dto.BookingRequestDTO;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/driver/{driverId}")
  public ResponseEntity<java.util.List<Booking>> getBookingsByDriver(@PathVariable int driverId) {
    try {
      return ResponseEntity.ok(bookingService.getBookingsByDriver(driverId));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/charger/{chargerId}")
  public ResponseEntity<java.util.List<Booking>> getBookingsByCharger(@PathVariable int chargerId) {
    try {
      return ResponseEntity.ok(bookingService.getBookingsByCharger(chargerId));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO dto) {
	System.out.println("Received booking request: " + dto);
    try {
      Booking created =
          bookingService.createBooking(
              dto.driverId(), dto.chargerId(), dto.startTime(), dto.endTime());
      return ResponseEntity.ok(created);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}/status")
  public ResponseEntity<BookingStatus> getStatus(@PathVariable int id) {
    try {
      BookingStatus status = bookingService.getChargingStatus(id);
      return ResponseEntity.ok(status);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PatchMapping("/{id}/start")
  public ResponseEntity<Booking> startCharging(@PathVariable int id) {
    try {
      Booking updated = bookingService.startCharging(id);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PatchMapping("/{id}/stop")
  public ResponseEntity<Booking> stopCharging(@PathVariable int id) {
    try {
      Booking updated = bookingService.stopCharging(id);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PatchMapping("/{id}/cancel")
  public ResponseEntity<Booking> cancelBooking(@PathVariable int id) {
    try {
      Booking updated = bookingService.cancelBooking(id);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
