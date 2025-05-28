package tqs.ChargeUnity.service;

import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.repository.BookingRepository;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.repository.DriverRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final DriverRepository driverRepository;
  private final ChargerRepository chargerRepository;

  public BookingService(
      BookingRepository bookingRepository,
      DriverRepository driverRepository,
      ChargerRepository chargerRepository) {
    this.bookingRepository = bookingRepository;
    this.driverRepository = driverRepository;
    this.chargerRepository = chargerRepository;
  }

  public Booking createBooking(
      int driverId, int chargerId, LocalDateTime startTime, LocalDateTime endTime) {
    Driver driver =
        driverRepository
            .findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found"));
    Charger charger =
        chargerRepository
            .findById(chargerId)
            .orElseThrow(() -> new RuntimeException("Charger not found"));

    if (!isTimeSlotAvailable(chargerId, startTime, endTime)) {
      throw new RuntimeException("Time slot not available");
    }

    // verify if charger status is Available
    if (charger.getStatus() != tqs.ChargeUnity.enums.ChargerStatus.AVAILABLE) {
      throw new RuntimeException("Charger is not available for booking");
    }

    List<Booking> driverBookings =
        bookingRepository.findOverlapingBookingsOfUser(driverId, startTime, endTime);
    if (!driverBookings.isEmpty()) {
      throw new RuntimeException("Duplicate booking: Driver already has a booking at this time");
    }

    double durationHours = java.time.Duration.between(startTime, endTime).toMinutes() / 60.0;
    double price = durationHours * charger.getPricePerKWh() * 10; // example pricing logic

    Booking booking = new Booking();
    booking.setDriver(driver);
    booking.setCharger(charger);
    booking.setStartTime(startTime);
    booking.setEndTime(endTime);
    booking.setStatus(BookingStatus.WAITING);
    booking.setPrice(price);

    bookingRepository.save(booking);
    return booking;
  }

  public boolean isTimeSlotAvailable(
      int chargerId, LocalDateTime startTime, LocalDateTime endTime) {
    List<Booking> bookings =
        bookingRepository.findOverlappingBookings(chargerId, startTime, endTime);

    return bookings.isEmpty();
  }

  public List<Booking> getBookingsByDriver(int driverId) {
    return bookingRepository.findByDriverId(driverId);
  }

  public Optional<Booking> getBookingById(int id) {
    return bookingRepository.findById(id);
  }

  public void cancelBooking(int bookingId, int driverId) {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    if (booking.getDriver().getId() != driverId) {
      throw new RuntimeException("Unauthorized: Booking doesn't belong to driver ID " + driverId);
    }

    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.save(booking);
  }

  public Booking startCharging(int bookingId) {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    if (booking.getStatus() != BookingStatus.WAITING) {
      throw new RuntimeException("Booking is not ready to start.");
    }

    booking.setStatus(BookingStatus.CHARGING);
    return bookingRepository.save(booking);
  }

  public BookingStatus getChargingStatus(int bookingId) {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    return booking.getStatus();
  }

  public Booking stopCharging(int bookingId) {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    if (booking.getStatus() != BookingStatus.CHARGING) {
      throw new RuntimeException("Booking is not currently charging.");
    }

    booking.setStatus(BookingStatus.COMPLETED);
    return bookingRepository.save(booking);
  }

  public List<Booking> getBookingsByCharger(int chargerId) {
    return bookingRepository.findByChargerId(chargerId);
  }

  public List<Booking> getBookingsByChargerAndDate(
      int chargerId, LocalDateTime start, LocalDateTime end) {
    return bookingRepository.findByChargerIdAndStartTimeBetween(chargerId, start, end);
  }

  public List<Booking> getOverlappingBookings(
      int chargerId, LocalDateTime start, LocalDateTime end) {
    return bookingRepository.findOverlappingBookings(chargerId, start, end);
  }

  public Booking cancelBooking(int bookingId) {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    if (booking.getStatus() == BookingStatus.CANCELLED) {
      throw new RuntimeException("Booking is already cancelled.");
    }

    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new RuntimeException("Booking is already completed.");
    }

    booking.setStatus(BookingStatus.CANCELLED);
    return bookingRepository.save(booking);
  }
}
