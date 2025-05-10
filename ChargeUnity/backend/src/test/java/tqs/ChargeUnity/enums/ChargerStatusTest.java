package tqs.ChargeUnity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargerStatusTest {

    @Test
    void testFromString_ValidStatus() {
        assertEquals(ChargerStatus.AVAILABLE, ChargerStatus.fromString("AVAILABLE"));
        assertEquals(ChargerStatus.UNAVAILABLE, ChargerStatus.fromString("unavailable"));
        assertEquals(ChargerStatus.UNDER_MAINTENANCE, ChargerStatus.fromString("Under_Maintenance"));
        assertEquals(ChargerStatus.OUT_OF_SERVICE, ChargerStatus.fromString("OUT_OF_SERVICE"));
    }

    @Test
    void testFromString_InvalidStatus() {
        assertEquals(ChargerStatus.UNKNOWN, ChargerStatus.fromString("INVALID"));
        assertEquals(ChargerStatus.UNKNOWN, ChargerStatus.fromString(""));
    }
}