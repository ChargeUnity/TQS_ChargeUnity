package tqs.ChargeUnity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingStatusTest {

    @Test
    void testFromString_ValidStatus() {
        assertEquals(BookingStatus.WAITING, BookingStatus.fromString("WAITING"));
        assertEquals(BookingStatus.CHARGING, BookingStatus.fromString("charging"));
        assertEquals(BookingStatus.COMPLETED, BookingStatus.fromString("Completed"));
        assertEquals(BookingStatus.CANCELLED, BookingStatus.fromString("CANcELLED"));
        assertEquals(BookingStatus.EXPIRED, BookingStatus.fromString("expired"));
    }

    @Test
    void testFromString_InvalidStatus() {
        assertEquals(BookingStatus.UNKNOWN, BookingStatus.fromString("INVALID"));
        assertEquals(BookingStatus.UNKNOWN, BookingStatus.fromString(""));
        assertEquals(BookingStatus.UNKNOWN, BookingStatus.fromString(null));
    }
}