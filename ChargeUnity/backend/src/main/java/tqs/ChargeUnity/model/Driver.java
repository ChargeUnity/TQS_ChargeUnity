package tqs.ChargeUnity.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Driver extends User{

    @OneToMany(mappedBy = "driver")
    private List<Car> cars = new ArrayList<>();

    @OneToMany(mappedBy = "driver")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "driver")
    private List<Trip> trips = new ArrayList<>();

    private Double balance;

    public Driver() {
        super();
    }
    public Driver(int id, String name, String email, List<Car> cars, List<Booking> bookings,
            List<Trip> trips, Double balance) {
        super(id, name);
        this.cars = cars;
        this.bookings = bookings;
        this.trips = trips;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Driver " + super.toString() + " - Balance: " + balance + ";";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Driver driver = (Driver) obj;
        return super.equals(obj) && balance.equals(driver.balance);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 31 * balance.hashCode();
    }

}