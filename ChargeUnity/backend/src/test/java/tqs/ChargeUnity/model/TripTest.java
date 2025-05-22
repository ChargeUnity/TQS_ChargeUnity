package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {

  @Test
  void testTripToString() {
    Driver driver = new Driver();
    driver.setId(1);
    driver.setName("John Doe");

    Trip trip = new Trip();
    trip.setId(101);
    trip.setDriver(driver);
    trip.setStartCoordinates("40.7128,-74.0060");
    trip.setEndCoordinates("34.0522,-118.2437");
    trip.setStartTime(LocalDateTime.of(2023, 10, 1, 10, 0));
    trip.setDurationSeconds("3600");
    trip.setDistance(4500.0);
    trip.setPrice(100.0);

    String expected =
        "Trip 101 - Driver: John Doe\n"
            + "Start Coordinates: 40.7128,-74.0060\n"
            + "End Coordinates: 34.0522,-118.2437\n"
            + "Start Time: 2023-10-01T10:00\n"
            + "Duration: 3600\n"
            + "Distance: 4500.0\n"
            + "Price: 100.0";

    assertEquals(expected, trip.toString());
  }
}
