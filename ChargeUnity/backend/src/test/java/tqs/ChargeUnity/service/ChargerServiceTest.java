package tqs.ChargeUnity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.repository.ChargerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChargerServiceTest {

  @Mock private ChargerRepository chargerRepository;

  @InjectMocks private ChargerService chargerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testGetChargerById() {
    Charger charger = new Charger();
    charger.setId(1);
    when(chargerRepository.findById(1)).thenReturn(Optional.of(charger));

    Optional<Charger> result = chargerService.getChargerById(1);

    assertTrue(result.isPresent());
    assertEquals(1, result.get().getId());
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testSaveCharger() {
    Charger charger = new Charger();
    when(chargerRepository.save(charger)).thenReturn(charger);

    Charger result = chargerService.saveCharger(charger);

    assertNotNull(result);
    verify(chargerRepository, times(1)).save(charger);
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testGetAllChargers() {
    List<Charger> chargers = List.of(new Charger(), new Charger());
    when(chargerRepository.findAll()).thenReturn(chargers);

    List<Charger> result = chargerService.getAllChargers();

    assertEquals(2, result.size());
    verify(chargerRepository, times(1)).findAll();
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testDeleteCharger() {
    int chargerId = 5;

    chargerService.deleteCharger(chargerId);

    verify(chargerRepository, times(1)).deleteById(chargerId);
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testGetChargersBySearch() {
    Station station = new Station();
    List<Charger> chargers = List.of(new Charger());
    when(chargerRepository.findByStation(station)).thenReturn(chargers);

    List<Charger> result = chargerService.getChargersBySearch(station);

    assertEquals(1, result.size());
    verify(chargerRepository, times(1)).findByStation(station);
  }

  // tests related to charger status
  @Test
  @Requirement("CH-28,CH-29")
  void testGetChargersByStatus() {
    ChargerStatus status = ChargerStatus.AVAILABLE;
    List<Charger> chargers = List.of(new Charger());
    when(chargerRepository.findByStatus(status)).thenReturn(chargers);

    List<Charger> result = chargerService.getChargersByStatus(status);

    assertEquals(1, result.size());
    verify(chargerRepository, times(1)).findByStatus(status);
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testUpdateChargerStatus_Success() {
    int chargerId = 10;
    ChargerStatus newStatus = ChargerStatus.UNAVAILABLE;
    Charger charger = new Charger();
    charger.setId(chargerId);
    charger.setStatus(ChargerStatus.AVAILABLE);

    when(chargerRepository.findById(chargerId)).thenReturn(Optional.of(charger));
    when(chargerRepository.save(any(Charger.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Charger updated = chargerService.updateChargerStatus(chargerId, newStatus);

    assertEquals(newStatus, updated.getStatus());
    verify(chargerRepository, times(1)).findById(chargerId);
    verify(chargerRepository, times(1)).save(charger);
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testUpdateChargerStatus_ChargerNotFound() {
    int chargerId = 99;
    ChargerStatus newStatus = ChargerStatus.AVAILABLE;

    when(chargerRepository.findById(chargerId)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              chargerService.updateChargerStatus(chargerId, newStatus);
            });

    assertTrue(exception.getMessage().contains("Charger not found with id: " + chargerId));
    verify(chargerRepository, times(1)).findById(chargerId);
    verify(chargerRepository, never()).save(any());
  }
}
