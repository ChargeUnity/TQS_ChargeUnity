package tqs.ChargeUnity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.OperatorRepository;
import tqs.ChargeUnity.repository.StationRepository;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class StationControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private OperatorRepository operatorRepository;

  @Autowired private StationRepository stationRepository;

  private Operator operator;

  @BeforeEach
  void setup() {
    operator = new Operator();
    operator.setName("Test Operator");
    operator = operatorRepository.save(operator);
  }

  @Test
  void createAndGetStation() throws Exception {
    Map<String, Object> payload = new HashMap<>();
    payload.put("name", "Test Station");
    payload.put("city", "Aveiro");
    payload.put("address", "Rua Exemplo");
    payload.put("latitude", 40.6412);
    payload.put("longitude", -8.6536);
    payload.put("operatorId", operator.getId());

    String json = objectMapper.writeValueAsString(payload);

    String response =
        mockMvc
            .perform(post("/api/v1/station").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test Station"))
            .andReturn()
            .getResponse()
            .getContentAsString();

    int stationId = objectMapper.readTree(response).get("id").asInt();

    // Get by ID
    mockMvc
        .perform(get("/api/v1/station/" + stationId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Station"));

    // Get all
    mockMvc
        .perform(get("/api/v1/station"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(stationId));
  }

  @Test
  void updateAndDeleteStation() throws Exception {
    Station station = new Station();
    station.setName("Old Name");
    station.setCity("Aveiro");
    station.setAddress("Rua Antiga");
    station.setLatitude("40.0");
    station.setLongitude("-8.0");
    station.setOperator(operator);
    station = stationRepository.save(station);

    station.setName("New Name");

    String json = objectMapper.writeValueAsString(station);

    mockMvc
        .perform(
            put("/api/v1/station/" + station.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("New Name"));

    mockMvc
        .perform(delete("/api/v1/station/" + station.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("Station deleted successfully"));
  }

  @Test
  void deletingNonExistentStation() throws Exception {
    mockMvc
        .perform(delete("/api/v1/station/9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Station not found"));
  }

  @Test
  void invalidPutById() throws Exception {
    Map<String, Object> payload = new HashMap<>();
    payload.put("name", "Invalid Station");
    payload.put("city", "Porto");
    payload.put("address", "Rua Inválida");
    payload.put("latitude", 41.1496);
    payload.put("longitude", -8.6109);
    payload.put("operatorId", operator.getId());

    String json = objectMapper.writeValueAsString(payload);

    mockMvc
        .perform(put("/api/v1/station/9999").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Station with ID 9999 not found"));
  }

  @Test
  void getStationsByCoordinates() throws Exception {
    Station station = new Station();
    station.setName("Nearby Station");
    station.setCity("Porto");
    station.setAddress("Rua Norte");
    station.setLatitude("41.1496");
    station.setLongitude("-8.6109");
    station.setOperator(operator);
    stationRepository.save(station);

    mockMvc
        .perform(get("/api/v1/station/coordinates/41.15/-8.61/5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Nearby Station"));
  }

  @Test
  void getStationByCity() throws Exception {
    Station station = new Station();
    station.setName("City Station");
    station.setCity("Lisbon");
    station.setAddress("Rua Central");
    station.setLatitude("38.7169");
    station.setLongitude("-9.1399");
    station.setOperator(operator);
    stationRepository.save(station);

    mockMvc
        .perform(get("/api/v1/station/city/Lisbon"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("City Station"));
  }

  @Test
  void getStationByCityEmpty() throws Exception {
    mockMvc
        .perform(get("/api/v1/station/city/NonExistentCity"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }
}
