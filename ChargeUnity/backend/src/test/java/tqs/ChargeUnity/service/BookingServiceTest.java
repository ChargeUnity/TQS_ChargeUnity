package tqs.ChargeUnity.service;

import org.mockito.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.BookingRepository;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.repository.DriverRepository;

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
  @Requirement("CH-29")
  void testCreateBookingScenarios() {
    Driver driver = new Driver();
    driver.setId(1);
    Charger charger = new Charger();
    charger.setId(1);
    charger.setStatus(ChargerStatus.AVAILABLE);
    charger.setPricePerKWh(0.5);

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);

    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
    when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));

    // Scenario 1: Successful booking creation
    when(bookingRepository.findByChargerId(1)).thenReturn(new ArrayList<>());
    when(bookingRepository.findByDriverId(1)).thenReturn(new ArrayList<>());

    Booking booking = bookingService.createBooking(1, 1, startTime, endTime);

    assertNotNull(booking);
    assertEquals(BookingStatus.WAITING, booking.getStatus());
    verify(bookingRepository, times(1)).save(booking);

    // Scenario 2: Duplicate booking
    Booking existingBooking = new Booking();
    existingBooking.setStartTime(startTime.minusMinutes(30));
    existingBooking.setEndTime(endTime.plusMinutes(30));

    when(bookingRepository.findByDriverId(1)).thenReturn(List.of(existingBooking));
    when(bookingRepository.findOverlapingBookingsOfUser(1, startTime, endTime))
        .thenReturn(List.of(existingBooking));

    RuntimeException duplicateException =
        assertThrows(
            RuntimeException.class,
            () -> bookingService.createBooking(1, 1, startTime, endTime));

    assertEquals(
        "Duplicate booking: Driver already has a booking at this time", duplicateException.getMessage());

    // Scenario 3: Time slot not available
    when(bookingRepository.findOverlappingBookings(1, startTime, endTime))
        .thenReturn(List.of(existingBooking));

    RuntimeException timeSlotException =
        assertThrows(
            RuntimeException.class,
            () -> bookingService.createBooking(1, 1, startTime, endTime));

    assertEquals("Time slot not available", timeSlotException.getMessage());
  }

  @Test
  @Requirement("CH-29")
  void testCreateBookingWithTooLong() {
    Driver driver = new Driver();
    driver.setId(1);
    Charger charger = new Charger();
    charger.setId(1);
    charger.setStatus(ChargerStatus.AVAILABLE);
    charger.setPricePerKWh(0.5);

    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusDays(2); // Too long booking

    when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
    when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));

    RuntimeException tooLongException =
        assertThrows(
            RuntimeException.class,
            () -> bookingService.createBooking(1, 1, startTime, endTime));

    assertEquals("Booking duration cannot exceed 4 hours.", tooLongException.getMessage());
  }

  // tests related to booking retrieval
  @Test
  @Requirement("CH-29")
  void testGetBookingScenarios() {
    Booking booking = new Booking();

    // Scenario 1: Get bookings by driver
    when(bookingRepository.findByDriverId(1)).thenReturn(List.of(booking));
    List<Booking> bookingsByDriver = bookingService.getBookingsByDriver(1);
    assertEquals(1, bookingsByDriver.size());
    assertSame(booking, bookingsByDriver.get(0));

    // Scenario 2: Get booking by ID found
    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    Optional<Booking> bookingById = bookingService.getBookingById(1);
    assertTrue(bookingById.isPresent());
    assertSame(booking, bookingById.get());

    // Scenario 3: Get booking by ID not found
    when(bookingRepository.findById(1)).thenReturn(Optional.empty());
    Optional<Booking> bookingNotFound = bookingService.getBookingById(1);
    assertFalse(bookingNotFound.isPresent());
  }

  // tests related to booking cancellation
  @Test
  @Requirement("CH-29")
  void testCancelBookingScenarios() {
    Driver driver = new Driver();
    driver.setId(1);
    Booking booking = new Booking();
    booking.setDriver(driver);

    // Scenario 1: Successful cancellation
    booking.setStatus(BookingStatus.WAITING);
    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

    bookingService.cancelBooking(1, 1);
    assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    verify(bookingRepository).save(booking);

    // Scenario 2: Booking not found
    when(bookingRepository.findById(1)).thenReturn(Optional.empty());
    RuntimeException notFoundException =
        assertThrows(
            RuntimeException.class,
            () -> bookingService.cancelBooking(1, 1));

    assertEquals("Booking not found", notFoundException.getMessage());

    // Scenario 3: Unauthorized cancellation
    Driver unauthorizedDriver = new Driver();
    unauthorizedDriver.setId(2);
    booking.setDriver(unauthorizedDriver);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    RuntimeException unauthorizedException =
        assertThrows(
            RuntimeException.class,
            () -> bookingService.cancelBooking(1, 1));

    assertTrue(unauthorizedException.getMessage().contains("Unauthorized"));
  }

  // tests related to charging operations
  @Test
  @Requirement("CH-93")
  void testStartChargingSuccess() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.WAITING);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

    Booking result = bookingService.startCharging(1);
    assertEquals(BookingStatus.CHARGING, result.getStatus());
    verify(bookingRepository).save(booking);
  }

  @Test
  @Requirement("CH-93")
  void testStartChargingNotWaiting() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.COMPLETED);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> bookingService.startCharging(1));
    assertEquals("Booking is not ready to start.", ex.getMessage());
  }

  @Test
  @Requirement("CH-93")
  void testGetChargingStatus() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.CHARGING);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    BookingStatus status = bookingService.getChargingStatus(1);
    assertEquals(BookingStatus.CHARGING, status);
  }

  @Test
  @Requirement("CH-93")
  void testGetChargingStatusNotFound() {
    when(bookingRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> bookingService.getChargingStatus(1));
    assertEquals("Booking not found", ex.getMessage());
  }

  @Test
  @Requirement("CH-93")
  void testStopChargingSuccess() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.CHARGING);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

    Booking result = bookingService.stopCharging(1);
    assertEquals(BookingStatus.COMPLETED, result.getStatus());
    verify(bookingRepository).save(booking);
  }

  @Test
  @Requirement("CH-93")
  void testStopChargingNotCharging() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.WAITING);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> bookingService.stopCharging(1));
    assertEquals("Booking is not currently charging.", ex.getMessage());
  }

  @Test
  @Requirement("CH-93")
  void testGetBookingsByCharger() {
    Booking booking = new Booking();

    when(bookingRepository.findByChargerId(1)).thenReturn(List.of(booking));

    List<Booking> bookings = bookingService.getBookingsByCharger(1);
    assertEquals(1, bookings.size());
    assertSame(booking, bookings.get(0));
  }

  @Test
  @Requirement("CH-93")
  void testGetBookingsByChargerAndDate() {
    Booking booking = new Booking();
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = start.plusHours(2);

    when(bookingRepository.findByChargerIdAndStartTimeBetween(1, start, end))
        .thenReturn(List.of(booking));

    List<Booking> bookings = bookingService.getBookingsByChargerAndDate(1, start, end);
    assertEquals(1, bookings.size());
    assertSame(booking, bookings.get(0));
  }

  @Test
  @Requirement("CH-93")
  void testGetOverlappingBookings() {
    Booking booking = new Booking();
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = start.plusHours(2);

    when(bookingRepository.findOverlappingBookings(1, start, end)).thenReturn(List.of(booking));

    List<Booking> bookings = bookingService.getOverlappingBookings(1, start, end);
    assertEquals(1, bookings.size());
    assertSame(booking, bookings.get(0));
  }

  @Test
  @Requirement("CH-93")
  void testCancelBookingByIdSuccess() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.WAITING);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

    Booking result = bookingService.cancelBooking(1);
    assertEquals(BookingStatus.CANCELLED, result.getStatus());
    verify(bookingRepository).save(booking);
  }

  @Test
  @Requirement("CH-93")
  void testCancelBookingByIdAlreadyCancelled() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.CANCELLED);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> bookingService.cancelBooking(1));
    assertEquals("Booking is already cancelled.", ex.getMessage());
  }

  @Test
  @Requirement("CH-93")
  void testCancelBookingByIdAlreadyCompleted() {
    Booking booking = new Booking();
    booking.setStatus(BookingStatus.COMPLETED);

    when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> bookingService.cancelBooking(1));
    assertEquals("Booking is already completed.", ex.getMessage());
  }

  @Test
  @Requirement("CH-93")
  void testCancelBookingByIdNotFound() {

    when(bookingRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> bookingService.cancelBooking(1));
    assertEquals("Booking not found", ex.getMessage());
  }
}
