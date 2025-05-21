package tqs.ChargeUnity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tqs.ChargeUnity.model.Booking;
import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.enums.BookingStatus;
import tqs.ChargeUnity.repository.BookingRepository;
import tqs.ChargeUnity.repository.DriverRepository;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.dto.BookingRequestDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BookingControllerIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private BookingRepository bookingRepository;
  @Autowired private DriverRepository driverRepository;
  @Autowired private ChargerRepository chargerRepository;

  private Driver driver;
  private Charger charger;

  @BeforeEach
  void setup() {
    driver = new Driver();
    driver.setName("Test Driver");
    driver.setBalance(100.0);
    driver = driverRepository.save(driver);

    charger = new Charger();
    charger.setPricePerKWh(0.5);
    charger = chargerRepository.save(charger);
  }

  @Test
  void createBooking_andGetByDriver_andGetStatus() throws Exception {
    BookingRequestDTO dto =
        new BookingRequestDTO(
            driver.getId(), charger.getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    String json = objectMapper.writeValueAsString(dto);

    // output the JSON string for debugging
    System.out.println("JSON: " + json);

    // Create booking
    String response =
        mockMvc
            .perform(post("/bookings").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.charger.id").value(charger.getId()))
            .andReturn()
            .getResponse()
            .getContentAsString();

    Long bookingId = objectMapper.readTree(response).get("id").asLong();

    // Get bookings by driver
    mockMvc
        .perform(get("/bookings/driver/" + driver.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(bookingId));

    // Get status
    String resp =
        mockMvc
            .perform(get("/bookings/" + bookingId + "/status"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertThat(resp).isEqualTo("\"WAITING\"");
  }

  @Test
  void startStopCancelBooking() throws Exception {
    Booking booking = new Booking();
    booking.setDriver(driver);
    booking.setCharger(charger);
    booking.setStartTime(LocalDateTime.now());
    booking.setEndTime(LocalDateTime.now().plusHours(1));
    booking.setStatus(BookingStatus.WAITING);
    booking = bookingRepository.save(booking);

    // Start charging
    mockMvc
        .perform(patch("/bookings/" + booking.getId() + "/start"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("CHARGING"));

    // Stop charging
    mockMvc
        .perform(patch("/bookings/" + booking.getId() + "/stop"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("COMPLETED"));

    // Cancel booking (should fail if already completed, but let's test the endpoint)
    mockMvc
        .perform(patch("/bookings/" + booking.getId() + "/cancel"))
        .andExpect(status().isBadRequest());
  }
}
