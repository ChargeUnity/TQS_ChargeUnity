package tqs.ChargeUnity.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.service.OperatorService;
import tqs.ChargeUnity.service.StationService;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class StationControllerTest {

    @Mock
    private StationService stationService;

    @Mock
    private OperatorService operatorService;

    @InjectMocks
    private StationController stationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStation_success() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("operatorId", 1);
        payload.put("name", "StationA");
        payload.put("city", "CityX");
        payload.put("address", "AddrX");
        payload.put("latitude", 40.0);
        payload.put("longitude", -8.0);

        Operator operator = new Operator();
        operator.setId(1);

        when(operatorService.findById(1)).thenReturn(Optional.of(operator));
        when(stationService.existsByName("StationA")).thenReturn(false);

        Station created = new Station();
        created.setName("StationA");
        created.setOperator(operator);

        when(stationService.addStation(any(Station.class))).thenReturn(created);

        ResponseEntity<?> response = stationController.createStation(payload);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isInstanceOf(Station.class);
        verify(stationService).addStation(any(Station.class));
    }

    @Test
    void createStation_operatorNotFound() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("operatorId", 2);
        payload.put("name", "StationB");

        when(operatorService.findById(2)).thenReturn(Optional.empty());

        ResponseEntity<?> response = stationController.createStation(payload);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Operator not found");
        verify(stationService, never()).addStation(any());
    }

    @Test
    void createStation_duplicateName() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("operatorId", 1);
        payload.put("name", "StationC");

        Operator operator = new Operator();
        operator.setId(1);

        when(operatorService.findById(1)).thenReturn(Optional.of(operator));
        when(stationService.existsByName("StationC")).thenReturn(true);

        ResponseEntity<?> response = stationController.createStation(payload);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isEqualTo("Station with this name already exists");
        verify(stationService, never()).addStation(any());
    }

    @Test
    void getAllStations_returnsList() {
        List<Station> stations = Arrays.asList(new Station(), new Station());
        when(stationService.getAllStations()).thenReturn(stations);

        List<Station> result = stationController.getAllStations();

        assertThat(result).hasSize(2);
        verify(stationService).getAllStations();
    }

    @Test
    void getStationById_found() {
        Station station = new Station();
        station.setId(10);
        when(stationService.getStationById(10)).thenReturn(Optional.of(station));

        ResponseEntity<?> response = stationController.getStationById(10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(station);
    }

    @Test
    void getStationById_notFound() {
        when(stationService.getStationById(99)).thenReturn(Optional.empty());

        ResponseEntity<?> response = stationController.getStationById(99);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Station not found");
    }

    @Test
    void updateStation_success() {
        Station updated = new Station();
        updated.setName("Updated");

        Station returned = new Station();
        returned.setName("Updated");

        when(stationService.updateStation(5, updated)).thenReturn(returned);

        ResponseEntity<?> response = stationController.updateStation(5, updated);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(returned);
    }

    @Test
    void updateStation_notFound() {
        Station updated = new Station();
        when(stationService.updateStation(7, updated)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = stationController.updateStation(7, updated);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Station not found");
    }

    @Test
    void deleteStation_success() {
        doNothing().when(stationService).deleteStation(3);

        ResponseEntity<?> response = stationController.deleteStation(3);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Station deleted successfully");
    }

    @Test
    void deleteStation_notFound() {
        doThrow(new RuntimeException()).when(stationService).deleteStation(4);

        ResponseEntity<?> response = stationController.deleteStation(4);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Station not found");
    }

    @Test
    void getStationsByLocation_returnsList() {
        List<Station> stations = Arrays.asList(new Station(), new Station());
        when(stationService.getStationsByLocation(40.0, -8.0, 10.0)).thenReturn(stations);

        ResponseEntity<?> response = stationController.getStationsByLocation(40.0, -8.0, 10.0);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(stations);
    }

    @Test
    void getStationsByCity_returnsList() {
        List<Station> stations = Arrays.asList(new Station());
        when(stationService.getStationsByCity("Aveiro")).thenReturn(stations);

        ResponseEntity<List<Station>> response = stationController.getStationsByCity("Aveiro");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(stations);
    }
}