package tqs.ChargeUnity.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

  @Test
  void testRound() {
    assertEquals(3.14, Utils.round(3.14159, 2));
    assertEquals(3.142, Utils.round(3.14159, 3));
    assertEquals(3.0, Utils.round(3.0, 2));
  }

  @Test
  void testRoundWithNegativePlaces() {
    assertThrows(IllegalArgumentException.class, () -> Utils.round(3.14159, -1));
  }
}
