package tqs.ChargeUnity.service;

import org.mockito.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.BookingRepository;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.repository.DriverRepository;
import tqs.ChargeUnity.service.BookingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

  @Mock private BookingRepository bookingRepository;

  @Mock private DriverRepository driverRepository;

  @Mock private ChargerRepository chargerRepository;

  @InjectMocks private BookingService bookingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // tests related to booking creation
  @Test
  void testCreateBooking() {
    Driver driver = new Driver();
    driver.setId(1);
    Charger charger = new Charger();
    charger.setId(1);
    charger.setPricePerKWh(0.5);

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
    when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));
    when(bookingRepository.findByChargerId(1)).thenReturn(new ArrayList<>());
    when(bookingRepository.findByDriverId(1)).thenReturn(new ArrayList<>());

    Booking booking = bookingService.createBooking(1, 1, startTime, endTime);

    assertNotNull(booking);
    assertEquals(BookingStatus.WAITING, booking.getStatus());
    verify(bookingRepository, times(1)).save(booking);
  }

  @Test
  void testCreateBookingDuplicate() {
    Driver driver = new Driver();
    driver.setId(1);
    Charger charger = new Charger();
    charger.setId(1);
    charger.setPricePerKWh(0.5);

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    Booking existingBooking = new Booking();
    existingBooking.setStartTime(startTime.minusMinutes(30));
    existingBooking.setEndTime(endTime.plusMinutes(30));

    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
    when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));
    when(bookingRepository.findByChargerId(1)).thenReturn(new ArrayList<>());
    when(bookingRepository.findByDriverId(1)).thenReturn(List.of(existingBooking));
    when(bookingRepository.findOverlapingBookingsOfUser(1, startTime, endTime))
        .thenReturn(List.of(existingBooking));

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              bookingService.createBooking(1, 1, startTime, endTime);
            });

    assertEquals(
        "Duplicate booking: Driver already has a booking at this time", exception.getMessage());
  }

  @Test
  void testCreateBookingTimeSlotNotAvailable() {
    Driver driver = new Driver();
    driver.setId(1);
    Charger charger = new Charger();
    charger.setId(1);

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    // Simulate an existing booking that overlaps with the requested time slot
    Booking existingBooking = new Booking();
    existingBooking.setStartTime(startTime.minusMinutes(30));
    existingBooking.setEndTime(endTime.plusMinutes(30));

    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
    when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));
    when(bookingRepository.findOverlappingBookings(1, startTime, endTime))
        .thenReturn(List.of(existingBooking));

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              bookingService.createBooking(1, 1, startTime, endTime);
            });

    assertEquals("Time slot not available", exception.getMessage());
  }

  @Test
  void testCreateBookingDriverNotFound() {
    when(driverRepository.findById(1)).thenReturn(Optional.empty());

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              bookingService.createBooking(1, 1, startTime, endTime);
            });

    assertEquals("Driver not found", exception.getMessage());
  }

  @Test
  void testCreateBookingChargerNotFound() {
    Driver driver = new Driver();
    driver.setId(1);

    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
    when(chargerRepository.findById(1)).thenReturn(Optional.empty());

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              bookingService.createBooking(1, 1, startTime, endTime);
            });

    assertEquals("Charger not found", exception.getMessage());
  }

  // tests related to time slot availability
  @Test
  void testIsTimeSlotAvailableTrue() {
    when(bookingRepository.findByChargerId(1)).thenReturn(new ArrayList<>());
    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    boolean available = bookingService.isTimeSlotAvailable(1, startTime, endTime);
    assertTrue(available);
  }

  @Test
  void testIsTimeSlotAvailableFalse() {
    Booking booking = new Booking();
    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);
    booking.setStartTime(startTime.minusMinutes(30));
    booking.setEndTime(endTime.plusMinutes(30));

    when(bookingRepository.findOverlappingBookings(1, startTime, endTime))
        .thenReturn(List.of(booking));

    boolean available = bookingService.isTimeSlotAvailable(1, startTime, endTime);
    assertFalse(available);
  }

  // tests related to booking retrieval
  @Test
  void testGetBookingsByDriver() {
    Booking booking = new Booking();
    when(bookingRepository.findByDriverId(1)).thenReturn(List.of(booking));

    List<Booking> bookings = bookingService.getBookingsByDriver(1);
    assertEquals(1, bookings.size());
    assertSame(booking, bookings.get(0));
  }

  @Test
  void testGetBookingByIdFound() {
    Booking booking = new Booking();
    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    Optional<Booking> result = bookingService.getBookingById(1);
    assertTrue(result.isPresent());
    assertSame(booking, result.get());
  }

  @Test
  void testGetBookingByIdNotFound() {
    when(bookingRepository.findById(1)).thenReturn(Optional.empty());

    Optional<Booking> result = bookingService.getBookingById(1);
    assertFalse(result.isPresent());
  }

  // tests related to booking cancellation
  @Test
  void testCancelBookingSuccess() {
    Driver driver = new Driver();
    driver.setId(1);
    Booking booking = new Booking();
    booking.setDriver(driver);
    booking.setStatus(BookingStatus.WAITING);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

    bookingService.cancelBooking(1, 1);

    assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    verify(bookingRepository).save(booking);
  }

  @Test
  void testCancelBookingNotFound() {
    when(bookingRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              bookingService.cancelBooking(1, 1);
            });

    assertEquals("Booking not found", exception.getMessage());
  }

  @Test
  void testCancelBookingUnauthorized() {
    Driver driver = new Driver();
    driver.setId(2);
    Booking booking = new Booking();
    booking.setDriver(driver);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              bookingService.cancelBooking(1, 1);
            });

    assertTrue(exception.getMessage().contains("Unauthorized"));
  }
}
