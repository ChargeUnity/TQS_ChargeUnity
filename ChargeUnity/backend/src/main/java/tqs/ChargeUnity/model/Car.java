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

    public Car() {
    }

    @Override
    public String toString() {
        return "Car " + id + " - Driver: " + driver.getName() + "(" + driver.getId() + ");"
                + brand + " " + model + "; Battery Capacity: " + batteryCapacity + "kWh; Battery Level: "
                + batteryPercentage + "kWh; Consumption per Km: " + consumptionPerKm + "kWh/Km; Kilometers: " + kilometers + "km;";
    }
    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
    public Double getBatteryPercentage() {
        return batteryPercentage;
    }
    public void setBatteryPercentage(Double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }
    public Double getConsumptionPerKm() {
        return consumptionPerKm;
    }
    public void setConsumptionPerKm(Double consumptionPerKm) {
        this.consumptionPerKm = consumptionPerKm;
    }
    public Long getKilometers() {
        return kilometers;
    }
    public void setKilometers(Long kilometers) {
        this.kilometers = kilometers;
    }

}