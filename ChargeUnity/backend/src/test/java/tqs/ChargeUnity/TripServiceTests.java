package tqs.ChargeUnity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.ChargeUnity.model.Trip;
import tqs.ChargeUnity.repository.TripRepository;
import tqs.ChargeUnity.service.TripService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {

  @Mock private TripRepository tripRepository;

  @InjectMocks private TripService tripService;

  private Trip trip1;
  private Trip trip2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    trip1 = new Trip();
    trip1.setId(1);
    trip1.setStartCoordinates("38.7169,-9.1399");
    trip1.setEndCoordinates("40.6405,-8.6538");

    trip2 = new Trip();
    trip2.setId(2);
    trip2.setStartCoordinates("41.1496,-8.6109");
    trip2.setEndCoordinates("40.2110,-8.4292");
  }

  @Test
  void testFindAll() {
    when(tripRepository.findAll()).thenReturn(Arrays.asList(trip1, trip2));

    List<Trip> trips = tripService.findAll();

    assertEquals(2, trips.size());
    verify(tripRepository, times(1)).findAll();
  }

  @Test
  void testFindById_ExistingId() {
    when(tripRepository.findById(1)).thenReturn(Optional.of(trip1));

    Optional<Trip> result = tripService.findById(1);

    assertTrue(result.isPresent());
    assertEquals("38.7169,-9.1399", result.get().getStartCoordinates());
    verify(tripRepository).findById(1);
  }

  @Test
  void testFindById_NonExistingId() {
    when(tripRepository.findById(99)).thenReturn(Optional.empty());

    Optional<Trip> result = tripService.findById(99);

    assertFalse(result.isPresent());
    verify(tripRepository).findById(99);
  }

  @Test
  void testSave() {
    when(tripRepository.save(trip1)).thenReturn(trip1);

    Optional<Trip> result = tripService.save(trip1);

    assertTrue(result.isPresent());
    assertEquals("40.6405,-8.6538", result.get().getEndCoordinates());
    verify(tripRepository).save(trip1);
  }

  @Test
  void testUpdate_ValidTrip() {
    when(tripRepository.existsById(trip1.getId())).thenReturn(true);
    when(tripRepository.save(trip1)).thenReturn(trip1);

    Optional<Trip> result = tripService.update(trip1);

    assertTrue(result.isPresent());
    verify(tripRepository).save(trip1);
  }

  @Test
  void testUpdate_NonExistingTrip() {
    when(tripRepository.existsById(trip1.getId())).thenReturn(false);

    Optional<Trip> result = tripService.update(trip1);

    assertFalse(result.isPresent());
    verify(tripRepository, never()).save(any());
  }

  @Test
  void testDeleteById() {
    tripService.deleteById(1);
    verify(tripRepository, times(1)).deleteById(1);
  }
}
