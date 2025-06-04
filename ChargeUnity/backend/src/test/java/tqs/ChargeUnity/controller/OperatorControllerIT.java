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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OperatorControllerIT {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private OperatorRepository operatorRepository;

    @Autowired private StationRepository stationRepository;

    private Operator operator;

    @BeforeEach
    void setup() {
        operator = new Operator();
        operator.setName("Integration Operator");
        operator = operatorRepository.save(operator);
    }

    @Test
    void createAndFetchOperator() throws Exception {
        Operator newOperator = new Operator();
        newOperator.setName("New Test Operator");

        String json = objectMapper.writeValueAsString(newOperator);

        String response = mockMvc
                .perform(post("/api/v1/operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Test Operator"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        int id = objectMapper.readTree(response).get("id").asInt();

        mockMvc
                .perform(get("/api/v1/operator/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Test Operator"));
    }

    @Test
    void getAllOperators() throws Exception {
        mockMvc
                .perform(get("/api/v1/operator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Integration Operator"));
    }

    @Test
    void getOperatorByInvalidId() throws Exception {
        mockMvc
                .perform(get("/api/v1/operator/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Operator not found"));
    }

    @Test
    void getStationsByOperator() throws Exception {
        Station station1 = new Station();
        station1.setName("Station A");
        station1.setCity("Aveiro");
        station1.setAddress("Rua A");
        station1.setLatitude("40.6");
        station1.setLongitude("-8.6");
        station1.setOperator(operator);

        Station station2 = new Station();
        station2.setName("Station B");
        station2.setCity("Porto");
        station2.setAddress("Rua B");
        station2.setLatitude("41.1");
        station2.setLongitude("-8.6");
        station2.setOperator(operator);

        stationRepository.saveAll(List.of(station1, station2));

        mockMvc
                .perform(get("/api/v1/operator/" + operator.getId() + "/station"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Station A"))
                .andExpect(jsonPath("$[1].name").value("Station B"));
    }

    @Test
    void getStationsForInvalidOperator() throws Exception {
        mockMvc
                .perform(get("/api/v1/operator/9999/station"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Operator not found"));
    }
}
