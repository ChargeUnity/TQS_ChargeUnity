package tqs.ChargeUnity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargerTypeTest {

  @Test
  void testFromString_ValidType() {
    assertEquals(ChargerType.FAST, ChargerType.fromString("FAST"));
    assertEquals(ChargerType.STANDARD, ChargerType.fromString("standard"));
    assertEquals(ChargerType.ECONOMY, ChargerType.fromString("Economy"));
  }

  @Test
  void testFromString_InvalidType() {
    assertEquals(ChargerType.UNKNOWN, ChargerType.fromString("INVALID"));
    assertEquals(ChargerType.UNKNOWN, ChargerType.fromString(""));
  }
}
