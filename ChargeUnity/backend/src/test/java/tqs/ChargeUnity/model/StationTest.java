package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    @Test
    void testStationToString() {
        Operator operator1 = new Operator();
        operator1.setId(1);
        operator1.setName("Alice");

        Operator operator2 = new Operator();
        operator2.setId(2);
        operator2.setName("Bob");

        Charger charger1 = new Charger();
        charger1.setId(101);
		charger1.setPricePerKWh(2.0);
		
        Charger charger2 = new Charger();
        charger2.setId(102);
		charger2.setPricePerKWh(4.0);

        Station station = new Station();
        station.setId(1);
        station.setName("Main Station");
        station.setCity("New York");
        station.setAddress("123 Main St");
        station.setLatitude("40.7128");
        station.setLongitude("-74.0060");

		operator1.setStation(station);
		operator2.setStation(station);
		charger1.setStation(station);
		charger2.setStation(station);

        List<Operator> operators = new ArrayList<>();
        operators.add(operator1);
        operators.add(operator2);
        station.setOperators(operators);

        List<Charger> chargers = new ArrayList<>();
        chargers.add(charger1);
        chargers.add(charger2);
        station.setChargers(chargers);

        String expected = """
Station 1: Main Station;
Address: 123 Main St, New York;
Operators: 2;
Operators List:
0 - Operator 1 - Name: Alice - Station: Main Station;
1 - Operator 2 - Name: Bob - Station: Main Station;
Chargers: 2;
Chargers List:
0 - Charger 101 - Type: null - Status: null - 2.0€/kWh - Station: Main Station;
1 - Charger 102 - Type: null - Status: null - 4.0€/kWh - Station: Main Station;""";

        assertEquals(expected, station.toString());
    }
}