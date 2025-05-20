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

  public void setBatteryCapacity(Double batteryCapacity) {
    if (batteryCapacity == null || batteryCapacity <= 0.0) {
      throw new IllegalArgumentException("Battery capacity must be positive.");
    }
    this.batteryCapacity = batteryCapacity;
  }

  public void setBatteryPercentage(Double batteryPercentage) {
    if (batteryPercentage == null || batteryPercentage < 0.0 || batteryPercentage > 100.0) {
      throw new IllegalArgumentException("Battery percentage must be between 0 and 100.");
    }
    this.batteryPercentage = batteryPercentage;
  }

  public void setConsumptionPerKm(Double consumptionPerKm) {
    if (consumptionPerKm == null || consumptionPerKm <= 0.0) {
      throw new IllegalArgumentException("Consumption per Km must be positive.");
    }
    this.consumptionPerKm = consumptionPerKm;
  }

  public void setKilometers(Long kilometers) {
    if (kilometers == null || kilometers <= 0L) {
      throw new IllegalArgumentException("Kilometers must be positive.");
    }
    this.kilometers = kilometers;
  }

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
