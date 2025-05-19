package tqs.ChargeUnity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  private Driver driver;

  private String brand;
  private String model;

  // in kWh
  private Double batteryCapacity;
  private Double batteryPercentage;

  private Double consumptionPerKm;

  private Long kilometers;

  public Car() {}

  @Override
  public String toString() {
    return "Car "
        + id
        + " - Driver: "
        + driver.getName()
        + "("
        + driver.getId()
        + ");"
        + brand
        + " "
        + model
        + "; Battery Capacity: "
        + batteryCapacity
        + "kWh; Battery Level: "
        + batteryPercentage
        + "kWh; Consumption per Km: "
        + consumptionPerKm
        + "kWh/Km; Kilometers: "
        + kilometers
        + "km;";
  }
}
