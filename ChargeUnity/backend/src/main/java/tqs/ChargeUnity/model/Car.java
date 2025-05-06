package tqs.ChargeUnity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

    private Double batteryCapacity;
    private Double batteryLevel;
    
    private Double consumptionPerKm;

    private Long kilometers;

    public Car() {
    }
    public Car(int id, Driver driver, String brand, String model, Double batteryCapacity, Double batteryLevel,
            Double consumptionPerKm, Long kilometers) {
        this.id = id;
        this.driver = driver;
        this.brand = brand;
        this.model = model;
        this.batteryCapacity = batteryCapacity;
        this.batteryLevel = batteryLevel;
        this.consumptionPerKm = consumptionPerKm;
        this.kilometers = kilometers;
    }

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

    public Double getBatteryLevel() {
        return batteryLevel;
    }
    public void setBatteryLevel(Double batteryLevel) {
        this.batteryLevel = batteryLevel;
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

    @Override
    public String toString() {
        return "Car " + id + " - Driver: " + driver.getName() + "(" + driver.getId() + ");"
                + brand + " " + model + "; Battery Capacity: " + batteryCapacity + "kWh; Battery Level: "
                + batteryLevel + "kWh; Consumption per Km: " + consumptionPerKm + "kWh/Km; Kilometers: " + kilometers + "km;";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return id == car.id && driver.equals(car.driver) && brand.equals(car.brand) && model.equals(car.model)
                && batteryCapacity.equals(car.batteryCapacity) && batteryLevel.equals(car.batteryLevel)
                && consumptionPerKm.equals(car.consumptionPerKm) && kilometers.equals(car.kilometers);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + driver.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + batteryCapacity.hashCode();
        result = 31 * result + batteryLevel.hashCode();
        result = 31 * result + consumptionPerKm.hashCode();
        result = 31 * result + kilometers.hashCode();
        return result;
    }
}