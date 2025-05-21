package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

  @Test
  void testOperatorToString() {
    Station station1 = new Station();
    station1.setId(1);
    station1.setName("Main Station");

    Station station2 = new Station();
    station2.setId(2);
    station2.setName("Secondary Station");

    List<Station> stations = new ArrayList<>();
    stations.add(station1);
    stations.add(station2);

    Operator operator = new Operator();
    operator.setId(1);
    operator.setName("Alice");
    operator.setStations(stations);

    String expected = "Operator 1 - Name: Alice - Stations: [0 - Main Station, 1 - Secondary Station];";
    assertEquals(expected, operator.toString());
  }

  @Test
  void testOperatorEquality() {
    Station station1 = new Station();
    station1.setId(1);
    station1.setName("Main Station");

    Station station2 = new Station();
    station2.setId(2);
    station2.setName("Secondary Station");

    List<Station> stations1 = new ArrayList<>();
    stations1.add(station1);
    stations1.add(station2);

    Operator operator1 = new Operator();
    operator1.setId(1);
    operator1.setName("Alice");
    operator1.setStations(stations1);

    Operator operator2 = new Operator();
    operator2.setId(1);
    operator2.setName("Alice");
    operator2.setStations(stations1);

    assertEquals(operator1, operator2);
    assertEquals(operator1.hashCode(), operator2.hashCode());
  }
}