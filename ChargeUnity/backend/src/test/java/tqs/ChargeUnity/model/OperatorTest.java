package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void testOperatorToString() {
        Station station = new Station();
        station.setId(1);
        station.setName("Main Station");

        Operator operator = new Operator();
        operator.setId(1);
        operator.setName("Alice");
        operator.setStation(station);

        String expected = "Operator 1 - Name: Alice - Station: Main Station;";
        assertEquals(expected, operator.toString());
    }

    @Test
    void testOperatorEquality() {
        Station station = new Station();
        station.setId(1);
        station.setName("Main Station");

        Operator operator1 = new Operator();
        operator1.setId(1);
        operator1.setName("Alice");
        operator1.setStation(station);

        Operator operator2 = new Operator();
        operator2.setId(1);
        operator2.setName("Alice");
        operator2.setStation(station);

        assertEquals(operator1, operator2);
        assertEquals(operator1.hashCode(), operator2.hashCode());
    }
}