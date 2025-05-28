package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

  @Test
  @Requirement("CH-28")
  void testStationToString() {
    Operator operator = new Operator();
    operator.setId(1);
    operator.setName("Alice");

    Station station = new Station();
    station.setId(1);
    station.setName("Main Station");
    station.setCity("New York");
    station.setAddress("123 Main St");
    station.setLatitude("40.7128");
    station.setLongitude("-74.0060");
    station.setOperator(operator);

    String expected =
        "Station 1: Main Station;" + "\nOperator: Alice;" + "\nAddress: 123 Main St, New York;";
    assertEquals(expected, station.toString());
  }
}
