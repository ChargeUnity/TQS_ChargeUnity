package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.enums.ChargerType;

import static org.junit.jupiter.api.Assertions.*;

class ChargerTest {

  @Test
  @Requirement("CH-28,CH-29")
  void testChargerToString() {
    Station station = new Station();
    station.setId(1);
    station.setName("Main Station");

    Charger charger = new Charger();
    charger.setId(101);
    charger.setStation(station);
    charger.setStatus(ChargerStatus.AVAILABLE);
    charger.setType(ChargerType.FAST);
    charger.setPricePerKWh(0.25);

    String expected =
        "Charger 101 - Type: FAST - Status: AVAILABLE - 0.25€/kWh - Station: Main Station;";

    assertEquals(expected, charger.toString());
  }

  @Test
  @Requirement("CH-28,CH-29")
  void testPricePerKWhBounds() {
    Charger charger = new Charger();

    // Test with a negative price
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> charger.setPricePerKWh(-0.1));
    assertEquals("Price per kWh must be positive.", exception.getMessage());

    // Test with zero price
    Exception exception2 =
        assertThrows(IllegalArgumentException.class, () -> charger.setPricePerKWh(0.0));
    assertEquals("Price per kWh must be positive.", exception2.getMessage());
  }
}
