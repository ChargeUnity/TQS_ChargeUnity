package tqs.ChargeUnity.controller;

import tqs.ChargeUnity.service.BookingService;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.dto.BookingRequestDTO;
import tqs.ChargeUnity.enums.BookingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
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
	void getBookingsByDriver_ReturnsOk() {
		int driverId = 1;
		List<Booking> bookings = List.of(mock(Booking.class));
		when(bookingService.getBookingsByDriver(driverId)).thenReturn(bookings);

		ResponseEntity<?> response = bookingController.getBookingsByDriver(driverId);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(bookings);
	}

	@Test
	void getBookingsByDriver_ReturnsBadRequestOnException() {
		int driverId = 1;
		when(bookingService.getBookingsByDriver(driverId)).thenThrow(new RuntimeException("error"));

		ResponseEntity<?> response = bookingController.getBookingsByDriver(driverId);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("error");
	}

	@Test
	void getBookingsByCharger_ReturnsOk() {
		int chargerId = 2;
		List<Booking> bookings = List.of(mock(Booking.class));
		when(bookingService.getBookingsByCharger(chargerId)).thenReturn(bookings);

		ResponseEntity<?> response = bookingController.getBookingsByCharger(chargerId);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(bookings);
	}

	@Test
	void getBookingsByCharger_ReturnsBadRequestOnException() {
		int chargerId = 2;
		when(bookingService.getBookingsByCharger(chargerId)).thenThrow(new RuntimeException("fail"));

		ResponseEntity<?> response = bookingController.getBookingsByCharger(chargerId);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("fail");
	}

	@Test
	void createBooking_ReturnsOk() {
		BookingRequestDTO dto = mock(BookingRequestDTO.class);
		when(dto.driverId()).thenReturn(1);
		when(dto.chargerId()).thenReturn(2);
		when(dto.startTime()).thenReturn(LocalDateTime.now());
		when(dto.endTime()).thenReturn(LocalDateTime.now().plusHours(1));
		Booking booking = mock(Booking.class);
		when(bookingService.createBooking(anyInt(), anyInt(), any(), any())).thenReturn(booking);

		ResponseEntity<?> response = bookingController.createBooking(dto);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(booking);
	}

	@Test
	void createBooking_ReturnsBadRequestOnException() {
		BookingRequestDTO dto = mock(BookingRequestDTO.class);
		when(dto.driverId()).thenReturn(1);
		when(dto.chargerId()).thenReturn(2);
		when(dto.startTime()).thenReturn(LocalDateTime.now());
		when(dto.endTime()).thenReturn(LocalDateTime.now().plusHours(1));
		when(bookingService.createBooking(anyInt(), anyInt(), any(), any()))
				.thenThrow(new RuntimeException("bad"));

		ResponseEntity<?> response = bookingController.createBooking(dto);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("bad");
	}

	@Test
	void getStatus_ReturnsOk() {
		int id = 5;
		BookingStatus status = BookingStatus.CHARGING;
		when(bookingService.getChargingStatus(id)).thenReturn(status);

		ResponseEntity<?> response = bookingController.getStatus(id);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(status);
	}

	@Test
	void getStatus_ReturnsBadRequestOnException() {
		int id = 5;
		when(bookingService.getChargingStatus(id)).thenThrow(new RuntimeException("not found"));

		ResponseEntity<?> response = bookingController.getStatus(id);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("not found");
	}

	@Test
	void startCharging_ReturnsOk() {
		int id = 10;
		Booking booking = mock(Booking.class);
		when(bookingService.startCharging(id)).thenReturn(booking);

		ResponseEntity<?> response = bookingController.startCharging(id);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(booking);
	}

	@Test
	void startCharging_ReturnsBadRequestOnException() {
		int id = 10;
		when(bookingService.startCharging(id)).thenThrow(new RuntimeException("fail"));

		ResponseEntity<?> response = bookingController.startCharging(id);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("fail");
	}

	@Test
	void stopCharging_ReturnsOk() {
		int id = 11;
		Booking booking = mock(Booking.class);
		when(bookingService.stopCharging(id)).thenReturn(booking);

		ResponseEntity<?> response = bookingController.stopCharging(id);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(booking);
	}

	@Test
	void stopCharging_ReturnsBadRequestOnException() {
		int id = 11;
		when(bookingService.stopCharging(id)).thenThrow(new RuntimeException("fail"));

		ResponseEntity<?> response = bookingController.stopCharging(id);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("fail");
	}

	@Test
	void cancelBooking_ReturnsOk() {
		int id = 12;
		Booking booking = mock(Booking.class);
		when(bookingService.cancelBooking(id)).thenReturn(booking);

		ResponseEntity<?> response = bookingController.cancelBooking(id);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(booking);
	}

	@Test
	void cancelBooking_ReturnsBadRequestOnException() {
		int id = 12;
		when(bookingService.cancelBooking(id)).thenThrow(new RuntimeException("fail"));

		ResponseEntity<?> response = bookingController.cancelBooking(id);

		assertThat(response.getStatusCode().value()).isEqualTo(400);
		assertThat(response.getBody()).isEqualTo("fail");
	}
}