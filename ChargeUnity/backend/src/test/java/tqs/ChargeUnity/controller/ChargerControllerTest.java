package tqs.ChargeUnity.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.enums.ChargerType;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.ChargerRepository;
import tqs.ChargeUnity.service.ChargerService;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChargerControllerTest {

    @Mock
    private ChargerRepository chargerRepository;

    @Mock
    private ChargerService chargerService;

    @InjectMocks
    private ChargerController chargerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- createCharger tests ---

    @Test
    void whenCreateChargerWithValidInput_thenReturnsCreatedCharger() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("stationId", 1);
        requestBody.put("status", "AVAILABLE");
        requestBody.put("chargerType", "FAST");
        requestBody.put("pricePerKWh", 0.25);

        Station station = new Station();
        Charger charger = new Charger();
        charger.setStation(station);
        charger.setStatus(ChargerStatus.AVAILABLE);
        charger.setType(ChargerType.FAST);
        charger.setPricePerKWh(0.25);

        when(chargerService.getStationById(1)).thenReturn(Optional.of(station));
        when(chargerRepository.save(any(Charger.class))).thenReturn(charger);

        ResponseEntity<?> response = chargerController.createCharger(requestBody);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isInstanceOf(Charger.class);
        Charger saved = (Charger) response.getBody();
        assertThat(saved.getStation()).isEqualTo(station);
        assertThat(saved.getStatus()).isEqualTo(ChargerStatus.AVAILABLE);
        assertThat(saved.getType()).isEqualTo(ChargerType.FAST);
        assertThat(saved.getPricePerKWh()).isEqualTo(0.25);
    }

    @Test
    void whenCreateChargerWithInvalidInput_thenReturnsBadRequest() {
        List<Map<String, Object>> invalidInputs = Arrays.asList(
            Map.of("status", "AVAILABLE", "chargerType", "FAST", "pricePerKWh", 0.25), // Missing stationId
            Map.of("stationId", 1, "chargerType", "FAST", "pricePerKWh", 0.25), // Missing status
            Map.of("stationId", 1, "status", "AVAILABLE", "pricePerKWh", 0.25), // Missing chargerType
            Map.of("stationId", 1, "status", "AVAILABLE", "chargerType", "FAST") // Missing pricePerKWh
        );

        for (Map<String, Object> requestBody : invalidInputs) {
            ResponseEntity<?> response = chargerController.createCharger(requestBody);
            assertThat(response.getStatusCode().value()).isEqualTo(400);
        }
    }

    @Test
    void whenCreateChargerWithInvalidTypes_thenReturnsBadRequest() {
        List<Map<String, Object>> invalidTypeInputs = Arrays.asList(
            Map.of("stationId", new Object(), "status", "AVAILABLE", "chargerType", "FAST", "pricePerKWh", 0.25), // Invalid stationId type
            Map.of("stationId", 1, "status", "INVALID_STATUS", "chargerType", "FAST", "pricePerKWh", 0.25), // Invalid status
            Map.of("stationId", 1, "status", "AVAILABLE", "chargerType", "INVALID_TYPE", "pricePerKWh", 0.25), // Invalid chargerType
            Map.of("stationId", 1, "status", "AVAILABLE", "chargerType", "FAST", "pricePerKWh", new Object()) // Invalid pricePerKWh type
        );

        for (Map<String, Object> requestBody : invalidTypeInputs) {
            ResponseEntity<?> response = chargerController.createCharger(requestBody);
            assertThat(response.getStatusCode().value()).isEqualTo(400);
        }
    }

    // --- getAllChargers ---

    @Test
    void whenGetAllChargers_thenReturnsList() {
        List<Charger> chargers = Arrays.asList(new Charger(), new Charger());
        when(chargerRepository.findAll()).thenReturn(chargers);

        ResponseEntity<?> response = chargerController.getAllChargers();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(chargers);
    }

    // --- getAvailableChargers ---

    @Test
    void whenGetAvailableChargers_thenReturnsAvailableList() {
        List<Charger> chargers = Arrays.asList(new Charger());
        when(chargerRepository.findByStatus(ChargerStatus.AVAILABLE)).thenReturn(chargers);

        ResponseEntity<?> response = chargerController.getAvailableChargers();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(chargers);
    }

    // --- filterChargers ---

    @Test
    void whenFilterChargersByStatus_thenReturnsFilteredList() {
        List<Charger> chargers = Arrays.asList(new Charger());
        when(chargerRepository.findByStatus(ChargerStatus.AVAILABLE)).thenReturn(chargers);

        ResponseEntity<?> response = chargerController.filterChargers(null, "AVAILABLE");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(chargers);
    }

    @Test
    void whenFilterChargersWithNoSupportedFilter_thenReturnsBadRequest() {
        ResponseEntity<?> response = chargerController.filterChargers(null, null);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("No supported filter provided");
    }

    // --- getChargerById ---

    @Test
    void whenGetChargerById_thenHandlesBothCases() {
        Charger charger = new Charger();
        when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));
        ResponseEntity<?> response = chargerController.getChargerById(1);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(charger);

        when(chargerRepository.findById(2)).thenReturn(Optional.empty());
        response = chargerController.getChargerById(2);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Charger not found");
    }

    // --- getChargersByStation ---

    @Test
    void whenGetChargersByStationExists_thenReturnsList() {
        Station station = new Station();
        List<Charger> chargers = Arrays.asList(new Charger());
        when(chargerService.getStationById(1)).thenReturn(Optional.of(station));
        when(chargerRepository.findByStationId(1)).thenReturn(chargers);

        ResponseEntity<?> response = chargerController.getChargersByStation(1);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(chargers);
    }

    @Test
    void whenGetChargersByStationNotFound_thenReturnsNotFound() {
        when(chargerService.getStationById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = chargerController.getChargersByStation(1);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Station not found");
    }

    // --- updateChargerStatus ---

    @Test
    void whenUpdateChargerStatusValid_thenReturnsUpdatedCharger() {
        Charger charger = new Charger();
        when(chargerService.updateChargerStatus(1, ChargerStatus.AVAILABLE)).thenReturn(charger);

        ResponseEntity<?> response = chargerController.updateChargerStatus(1, "AVAILABLE");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(charger);
    }

    @Test
    void whenUpdateChargerStatusThrowsException_thenReturnsBadRequest() {
        when(chargerService.updateChargerStatus(1, ChargerStatus.AVAILABLE))
                .thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = chargerController.updateChargerStatus(1, "AVAILABLE");

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("error");
    }

}
