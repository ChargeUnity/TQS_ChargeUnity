package tqs.ChargeUnity.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import tqs.ChargeUnity.model.Operator;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.OperatorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;

public class OperatorServiceTest {
  @Mock private OperatorRepository operatorRepository;

  @InjectMocks private OperatorService operatorService;

  private Operator sampleOperator;

  private Station sampleStation;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    sampleOperator = new Operator();
    sampleOperator.setId(1);
    sampleOperator.setName("Galp");

    sampleStation = new Station();
    sampleStation.setId(1);
    sampleStation.setName("Main Station");

    List<Station> stations = new ArrayList<>();
    stations.add(sampleStation);
    sampleOperator.setStations(stations);
  }

  @Test
  @Requirement("CH-33")
  void testFindById() {
    when(operatorRepository.findById(1)).thenReturn(Optional.of(sampleOperator));
    Optional<Operator> result = operatorService.findById(1);
    assertTrue(result.isPresent());
    assertEquals("Galp", result.get().getName());
  }

  @Test
  @Requirement("CH-33")
  void testFindByName() {
    when(operatorRepository.findByName("Galp")).thenReturn(Optional.of(sampleOperator));
    Optional<Operator> result = operatorService.findByName("Galp");
    assertTrue(result.isPresent());
  }

  @Test
  @Requirement("CH-33")
  void testSave() {
    when(operatorRepository.save(any(Operator.class))).thenReturn(sampleOperator);
    Optional<Operator> result = Optional.ofNullable(operatorService.save(sampleOperator));
    assertTrue(result.isPresent());
    assertEquals("Galp", result.get().getName());
  }

  @Test
  @Requirement("CH-33")
  void testUpdate() {
    Operator updated = new Operator();
    updated.setId(1);
    updated.setName("EDP");

    when(operatorRepository.findById(1)).thenReturn(Optional.of(sampleOperator));
    when(operatorRepository.save(any(Operator.class))).thenReturn(updated);

    Station newStation = new Station();
    newStation.setId(2);
    newStation.setName("New Station");

    List<Station> updatedStations = new ArrayList<>();
    updatedStations.add(newStation);
    updated.setStations(updatedStations);

    when(operatorRepository.findById(1)).thenReturn(Optional.of(sampleOperator));
    when(operatorRepository.save(any(Operator.class))).thenReturn(updated);

    Optional<Operator> result = operatorService.update(updated);
    assertTrue(result.isPresent());
    assertEquals("EDP", result.get().getName());
    assertEquals(1, result.get().getStations().size());
    assertEquals("New Station", result.get().getStations().get(0).getName());
  }

  @Test
  @Requirement("CH-33")
  public void testDeleteById() {
    when(operatorRepository.existsById(1)).thenReturn(true);
    doNothing().when(operatorRepository).deleteById(1);

    operatorService.deleteById(1);

    verify(operatorRepository, times(1)).existsById(1);
    verify(operatorRepository, times(1)).deleteById(1);
  }
}
