package tqs.ChargeUnity.model;

import org.junit.jupiter.api.Test;

import app.getxray.xray.junit.customjunitxml.annotations.Requirement;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

  @Test
  @Requirement("CH-29")
  void testDriverToString() {
    Driver driver = new Driver();
    driver.setId(1);
    driver.setName("John Doe");
    driver.setBalance(100.0);

    String expected = "Driver 1 - Name: John Doe - Balance: 100.0;";
    assertEquals(expected, driver.toString());
  }

  @Test
  @Requirement("CH-29")
  void testDriverEquality() {
    Driver driver1 = new Driver();
    driver1.setId(1);
    driver1.setName("John Doe");
    driver1.setBalance(100.0);

    Driver driver2 = new Driver();
    driver2.setId(1);
    driver2.setName("John Doe");
    driver2.setBalance(100.0);

    assertEquals(driver1, driver2);
    assertEquals(driver1.hashCode(), driver2.hashCode());
  }
}
