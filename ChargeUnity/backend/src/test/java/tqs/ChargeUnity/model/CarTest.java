package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarToString() {
        Driver driver = new Driver();
        driver.setId(1);
        driver.setName("John Doe");

        Car car = new Car();
        car.setId(101);
        car.setDriver(driver);
        car.setBrand("Tesla");
        car.setModel("Model S");
        car.setBatteryCapacity(100.0);
        car.setBatteryPercentage(80.0);
        car.setConsumptionPerKm(0.2);
        car.setKilometers(50000L);

        String expected = "Car 101 - Driver: John Doe(1);Tesla Model S; Battery Capacity: 100.0kWh; Battery Level: 80.0kWh; Consumption per Km: 0.2kWh/Km; Kilometers: 50000km;";

        assertEquals(expected, car.toString());
    }

    @Test
    void testBatteryPercentageLimits() {
        Car car = new Car();

		// Test with a battery percentage greater than 100
        Exception exception = assertThrows(IllegalArgumentException.class, () -> car.setBatteryPercentage(120.0));
        assertEquals("Battery percentage must be between 0 and 100.", exception.getMessage());

		// Test with a negative battery percentage
		Exception exception2 = assertThrows(IllegalArgumentException.class, () -> car.setBatteryPercentage(-10.0));
		assertEquals("Battery percentage must be between 0 and 100.", exception2.getMessage());
    }

    @Test
    void testConsumptionPerKmBounds() {
        Car car = new Car();
		
		// Test with a consumption per Km negative
        Exception exception = assertThrows(IllegalArgumentException.class, () -> car.setConsumptionPerKm(-0.1));
        assertEquals("Consumption per Km must be positive.", exception.getMessage());

		// Test with a consumption per Km zero
		Exception exception2 = assertThrows(IllegalArgumentException.class, () -> car.setConsumptionPerKm(0.0));
		assertEquals("Consumption per Km must be positive.", exception2.getMessage());
    }

	@Test
	void testKilometersBounds() {
		Car car = new Car();

		// Test with negative kilometers
		Exception exception = assertThrows(IllegalArgumentException.class, () -> car.setKilometers(-100L));
		assertEquals("Kilometers must be positive.", exception.getMessage());
		
		// Test with zero kilometers
		Exception exception2 = assertThrows(IllegalArgumentException.class, () -> car.setKilometers(0L));
		assertEquals("Kilometers must be positive.", exception2.getMessage());
	}

	@Test
	void testBatteryCapacityBounds() {
		Car car = new Car();

		// Test with a battery capacity negative
		Exception exception = assertThrows(IllegalArgumentException.class, () -> car.setBatteryCapacity(-50.0));
		assertEquals("Battery capacity must be positive.", exception.getMessage());
		
		// Test with a battery capacity zero
		Exception exception2 = assertThrows(IllegalArgumentException.class, () -> car.setBatteryCapacity(0.0));
		assertEquals("Battery capacity must be positive.", exception2.getMessage());
	}

}