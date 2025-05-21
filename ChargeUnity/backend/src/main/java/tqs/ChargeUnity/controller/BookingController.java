package tqs.ChargeUnity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.dto.BookingRequestDTO;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

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

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO dto) {
        try {
            Booking created = bookingService.createBooking(
                    dto.driverId(),
                    dto.chargerId(),
                    dto.startTime(),
                    dto.endTime()
            );
            return ResponseEntity.ok(created);
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
    
    @PatchMapping("/{id}/start")
    public ResponseEntity<?> startCharging(int id) {
        try {
            Booking updated = bookingService.startCharging(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/stop")
    public ResponseEntity<?> stopCharging(int id) {
        try {
            Booking updated = bookingService.stopCharging(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(int id) {
        try {
            Booking updated = bookingService.cancelBooking(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

