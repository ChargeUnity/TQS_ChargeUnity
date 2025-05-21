package tqs.ChargeUnity.service;

import org.mockito.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.repository.BookingRepository;
import tqs.ChargeUnity.service.BookingService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        booking = new Booking();booking.setId(1);
        booking.setStatus(BookingStatus.WAITING);
    }

    @Test
    void testStartCharging_WithValidBooking() {
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking updatedBooking = bookingService.startCharging(1);

        assertNotNull(updatedBooking);
        assertEquals(BookingStatus.CHARGING, updatedBooking.getStatus());
        verify(bookingRepository).findById(1);
        verify(bookingRepository).save(booking);
    }

    @Test
    void testStartCharging_WithInvalidBookingStatus() {
        booking.setStatus(BookingStatus.CANCELLED);
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookingService.startCharging(1));
        assertEquals("Booking is not ready to start.", exception.getMessage());
        verify(bookingRepository).findById(1);
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void testStartCharging_WithNonExistingBooking() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookingService.startCharging(1));
        assertEquals("Booking not found", exception.getMessage());
        verify(bookingRepository).findById(1);
        verify(bookingRepository, never()).save(any());
    }
}

