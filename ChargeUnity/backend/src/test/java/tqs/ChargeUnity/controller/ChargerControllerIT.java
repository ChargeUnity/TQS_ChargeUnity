
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

import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.repository.StationRepository;

import java.util.List;
  
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.enums.ChargerType;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ChargerControllerIT {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private ChargerRepository chargerRepository;

    @Autowired private StationRepository stationRepository;

    private Station station;

    @BeforeEach
    void setup() {
        station = new Station();
        station.setName("Main Station");
        station.setCity("Lisboa");
        station.setAddress("Rua Central");
        station.setLatitude("38.72");  // Corrigido para double
        station.setLongitude("-9.13"); // Corrigido para double
        station = stationRepository.save(station);
    }

    @Test
    void createAndGetCharger() throws Exception {
        Charger charger = new Charger();
        charger.setType(ChargerType.ECONOMY);  // Usar enum correto
        charger.setStatus(ChargerStatus.AVAILABLE);
        charger.setPricePerKWh(0.22);        // Usa pricePerKWh porque é o campo que tens
        charger.setStation(station);

        String json = objectMapper.writeValueAsString(charger);

        String response = mockMvc
                .perform(post("/api/v1/charger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("ECONOMY"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        int id = objectMapper.readTree(response).get("id").asInt();

        mockMvc
                .perform(get("/api/v1/charger/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("ECONOMY"))
                .andExpect(jsonPath("$.pricePerKWh").value(0.22));
    }

    @Test
    void getAllChargers() throws Exception {
        Charger c1 = new Charger();
        c1.setType(ChargerType.STANDARD);
        c1.setStatus(ChargerStatus.UNDER_MAINTENANCE);
        c1.setPricePerKWh(0.50);
        c1.setStation(station);

        Charger c2 = new Charger();
        c2.setType(ChargerType.STANDARD);
        c2.setStatus(ChargerStatus.AVAILABLE);
        c2.setPricePerKWh(0.22);
        c2.setStation(station);

        chargerRepository.saveAll(List.of(c1, c2));

        mockMvc
                .perform(get("/api/v1/charger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getChargerByInvalidId() throws Exception {
        mockMvc
                .perform(get("/api/v1/charger/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Charger not found"));
    }

    @Test
    void updateChargerStatus() throws Exception {
        Charger charger = new Charger();
        charger.setType(ChargerType.STANDARD);
        charger.setStatus(ChargerStatus.AVAILABLE);
        charger.setPricePerKWh(0.07);
        charger.setStation(station);
        charger = chargerRepository.save(charger);

        mockMvc
                .perform(put("/api/v1/charger/" + charger.getId() + "/status")
                        .param("status", "UNDER_MAINTENANCE"))
                .andExpect(status().isOk());

        Charger updated = chargerRepository.findById(charger.getId()).orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(ChargerStatus.UNDER_MAINTENANCE);
    }

    @Test
    void updateChargerStatusInvalidId() throws Exception {
        mockMvc
                .perform(put("/api/v1/charger/9999/status")
                        .param("status", "AVAILABLE"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Charger not found"));
    }

    @Test
    void getChargersByStation() throws Exception {
        Station anotherStation = new Station();
        anotherStation.setName("Another Station");
        anotherStation.setCity("Porto");
        anotherStation.setAddress("Rua X");
        anotherStation.setLatitude("41.15");
        anotherStation.setLongitude("-8.61");
        anotherStation = stationRepository.save(anotherStation);

        Charger c1 = new Charger();
        c1.setType(ChargerType.STANDARD);
        c1.setStatus(ChargerStatus.UNDER_MAINTENANCE);
        c1.setPricePerKWh(0.50);
        c1.setStation(station);

        Charger c2 = new Charger();
        c2.setType(ChargerType.STANDARD);
        c2.setStatus(ChargerStatus.AVAILABLE);
        c2.setPricePerKWh(0.22);
        c2.setStation(station);

        chargerRepository.saveAll(List.of(c1, c2));

        mockMvc
                .perform(get("/api/v1/charger/by-station/" + station.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].station.id").value(station.getId()));
    }

    @Test
    void getAvailableChargers() throws Exception {
        Charger c1 = new Charger();
        
        c1.setType(ChargerType.STANDARD);
        c1.setStatus(ChargerStatus.UNDER_MAINTENANCE);
        c1.setPricePerKWh(0.50);
        c1.setStation(station);

        Charger c2 = new Charger();
        c2.setType(ChargerType.STANDARD);
        c2.setStatus(ChargerStatus.AVAILABLE);
        c2.setPricePerKWh(0.22);
        c2.setStation(station);

        chargerRepository.saveAll(List.of(c1, c2));

        mockMvc
                .perform(get("/api/v1/charger/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }

    @Test
    void filterChargersByPowerAndStatus() throws Exception {
        Charger c1 = new Charger();
        c1.setType(ChargerType.FAST);
        c1.setStatus(ChargerStatus.AVAILABLE);
        c1.setPricePerKWh(0.22);
        c1.setStation(station);

        Charger c2 = new Charger();
        c2.setType(ChargerType.STANDARD);
        c2.setStatus(ChargerStatus.UNDER_MAINTENANCE);
        c2.setPricePerKWh(0.50);
        c2.setStation(station);

        chargerRepository.saveAll(List.of(c1, c2));

        mockMvc
                .perform(get("/api/v1/charger/filter")
                        .param("power", "0.22")
                        .param("status", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("TYPE2"));
    }
}
