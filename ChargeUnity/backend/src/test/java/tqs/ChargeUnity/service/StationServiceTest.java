package tqs.ChargeUnity.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.repository.StationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

public class StationServiceTest {

  @Mock private StationRepository stationRepository;

  @Mock private ChargerService chargerService;

  @Mock private OperatorService operatorService;

  @InjectMocks private StationService stationService;

  private Station sampleStation;

  private Operator sampleOperator;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    sampleStation = new Station();
    sampleStation.setId(1);
    sampleStation.setName("Main Station");
    sampleStation.setCity("New York");
    sampleStation.setAddress("123 Main St");
    sampleStation.setLatitude("40.7128");
    sampleStation.setLongitude("-74.0060");

    sampleOperator = new Operator();
    sampleOperator.setId(1);
    sampleOperator.setName("Galp");
    sampleStation.setOperator(sampleOperator);
  }

  @Test
  public void testGetAllStations() {
    when(stationRepository.findAll()).thenReturn(List.of(sampleStation));
    List<Station> result = stationService.getAllStations();
    assertEquals(1, result.size());
    assertEquals("Main Station", result.get(0).getName());
  }

  @Test
  public void testGetStationById() {
    when(stationRepository.findById(1)).thenReturn(Optional.of(sampleStation));
    Optional<Station> result = stationService.getStationById(1);
    assertTrue(result.isPresent());
    assertEquals("Main Station", result.get().getName());
  }

  @Test
  public void testAddStation() {
    when(stationRepository.save(any(Station.class))).thenReturn(sampleStation);
    Station result = stationService.addStation(sampleStation);
    assertNotNull(result);
    assertEquals("Main Station", result.getName());
  }

  @Test
  public void testDeleteStation() {
    when(stationRepository.existsById(1)).thenReturn(true);
    doNothing().when(stationRepository).deleteById(1);
    stationService.deleteStation(1);
    verify(stationRepository, times(1)).existsById(1);
    verify(stationRepository, times(1)).deleteById(1);
  }

  @Test
  public void testDeleteStationThrowsExceptionWhenNotFound() {
    when(stationRepository.existsById(1)).thenReturn(false);
    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> stationService.deleteStation(1));
    assertEquals("Station with ID 1 not found.", exception.getMessage());
  }

  @Test
  public void testUpdateStation() {
    Station updatedStation = new Station();
    updatedStation.setName("Updated Station");
    updatedStation.setCity("Los Angeles");
    updatedStation.setAddress("456 New Address");
    updatedStation.setLatitude("34.0522");
    updatedStation.setLongitude("-118.2437");

    when(stationRepository.findById(1)).thenReturn(Optional.of(sampleStation));
    when(stationRepository.save(any(Station.class))).thenReturn(updatedStation);

    Station result = stationService.updateStation(1, updatedStation);
    assertNotNull(result);
    assertEquals("Updated Station", result.getName());
    assertEquals("Los Angeles", result.getCity());
  }

  @Test
  public void testUpdateStationThrowsExceptionWhenNotFound() {
    when(stationRepository.findById(1)).thenReturn(Optional.empty());
    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> stationService.updateStation(1, sampleStation));
    assertEquals("Station not found", exception.getMessage());
  }

  @Test
  public void testGetStationsByCity() {
    when(stationRepository.findByCityIgnoreCase("New York")).thenReturn(List.of(sampleStation));
    List<Station> result = stationService.getStationsByCity("New York");
    assertEquals(1, result.size());
    assertEquals("Main Station", result.get(0).getName());
  }

  @Test
  public void testGetStationsByOperator() {
    when(stationRepository.findByOperatorId(1)).thenReturn(List.of(sampleStation));
    List<Station> result = stationService.getStationsByOperator(1);
    assertEquals(1, result.size());
    assertEquals("Main Station", result.get(0).getName());
  }

  @Test
  public void testGetStationsByLocation() {
    Station stationWithinRadius = new Station();
    stationWithinRadius.setId(1);
    stationWithinRadius.setName("Station Within Radius");
    stationWithinRadius.setLatitude("40.7128");
    stationWithinRadius.setLongitude("-74.0060");

    Station stationOutsideRadius = new Station();
    stationOutsideRadius.setId(2);
    stationOutsideRadius.setName("Station Outside Radius");
    stationOutsideRadius.setLatitude("34.0522");
    stationOutsideRadius.setLongitude("-118.2437");

    when(stationRepository.findAll())
        .thenReturn(List.of(stationWithinRadius, stationOutsideRadius));

    List<Station> result = stationService.getStationsByLocation(40.7128, -74.0060, 10);

    assertEquals(1, result.size());
    assertEquals("Station Within Radius", result.get(0).getName());
    assertEquals(1, result.get(0).getId());
  }
}
