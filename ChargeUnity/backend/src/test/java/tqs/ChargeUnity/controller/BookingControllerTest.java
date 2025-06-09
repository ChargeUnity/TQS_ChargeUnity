package tqs.ChargeUnity.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.dto.BookingRequestDTO;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookingsByDriver_ReturnsList_WhenSuccess() {
        int driverId = 1;
        Booking booking = mock(Booking.class);
        when(bookingService.getBookingsByDriver(driverId)).thenReturn(List.of(booking));

        ResponseEntity<List<Booking>> response = bookingController.getBookingsByDriver(driverId);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly(booking);
        verify(bookingService).getBookingsByDriver(driverId);
    }

    @Test
    void getBookingsByDriver_ReturnsBadRequest_WhenException() {
        int driverId = 1;
        when(bookingService.getBookingsByDriver(driverId)).thenThrow(new RuntimeException());

        ResponseEntity<List<Booking>> response = bookingController.getBookingsByDriver(driverId);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getBookingsByCharger_ReturnsList_WhenSuccess() {
        int chargerId = 2;
        Booking booking = mock(Booking.class);
        when(bookingService.getBookingsByCharger(chargerId)).thenReturn(Arrays.asList(booking));

        ResponseEntity<List<Booking>> response = bookingController.getBookingsByCharger(chargerId);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly(booking);
        verify(bookingService).getBookingsByCharger(chargerId);
    }

    @Test
    void getBookingsByCharger_ReturnsBadRequest_WhenException() {
        int chargerId = 2;
        when(bookingService.getBookingsByCharger(chargerId)).thenThrow(new RuntimeException());

        ResponseEntity<List<Booking>> response = bookingController.getBookingsByCharger(chargerId);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void createBooking_ReturnsBooking_WhenSuccess() {
        BookingRequestDTO dto = mock(BookingRequestDTO.class);
        when(dto.driverId()).thenReturn(1);
        when(dto.chargerId()).thenReturn(2);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        when(dto.startTime()).thenReturn(start);
        when(dto.endTime()).thenReturn(end);

        Booking booking = mock(Booking.class);
        when(bookingService.createBooking(1, 2, start, end)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.createBooking(dto);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(booking);
        verify(bookingService).createBooking(1, 2, start, end);
    }

    @Test
    void createBooking_ReturnsBadRequest_WhenException() {
        BookingRequestDTO dto = mock(BookingRequestDTO.class);
        when(dto.driverId()).thenReturn(1);
        when(dto.chargerId()).thenReturn(2);
        when(dto.startTime()).thenReturn(LocalDateTime.now());
        when(dto.endTime()).thenReturn(LocalDateTime.now().plusHours(1));

        when(bookingService.createBooking(anyInt(), anyInt(), any(), any()))
                .thenThrow(new RuntimeException());

        ResponseEntity<Booking> response = bookingController.createBooking(dto);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getStatus_ReturnsStatus_WhenSuccess() {
        int id = 5;
        BookingStatus status = BookingStatus.WAITING;
        when(bookingService.getChargingStatus(id)).thenReturn(status);

        ResponseEntity<BookingStatus> response = bookingController.getStatus(id);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(status);
        verify(bookingService).getChargingStatus(id);
    }

    @Test
    void getStatus_ReturnsBadRequest_WhenException() {
        int id = 5;
        when(bookingService.getChargingStatus(id)).thenThrow(new RuntimeException());

        ResponseEntity<BookingStatus> response = bookingController.getStatus(id);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void startCharging_ReturnsBooking_WhenSuccess() {
        int id = 10;
        Booking booking = mock(Booking.class);
        when(bookingService.startCharging(id)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.startCharging(id);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(booking);
        verify(bookingService).startCharging(id);
    }

    @Test
    void startCharging_ReturnsBadRequest_WhenException() {
        int id = 10;
        when(bookingService.startCharging(id)).thenThrow(new RuntimeException());

        ResponseEntity<Booking> response = bookingController.startCharging(id);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void stopCharging_ReturnsBooking_WhenSuccess() {
        int id = 11;
        Booking booking = mock(Booking.class);
        when(bookingService.stopCharging(id)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.stopCharging(id);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(booking);
        verify(bookingService).stopCharging(id);
    }

    @Test
    void stopCharging_ReturnsBadRequest_WhenException() {
        int id = 11;
        when(bookingService.stopCharging(id)).thenThrow(new RuntimeException());

        ResponseEntity<Booking> response = bookingController.stopCharging(id);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void cancelBooking_ReturnsBooking_WhenSuccess() {
        int id = 12;
        Booking booking = mock(Booking.class);
        when(bookingService.cancelBooking(id)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.cancelBooking(id);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(booking);
        verify(bookingService).cancelBooking(id);
    }

    @Test
    void cancelBooking_ReturnsBadRequest_WhenException() {
        int id = 12;
        when(bookingService.cancelBooking(id)).thenThrow(new RuntimeException());

        ResponseEntity<Booking> response = bookingController.cancelBooking(id);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNull();
    }
}