package tqs.ChargeUnity.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

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
    public Driver(int id, String name, String email, boolean enabled, List<Car> cars, List<Booking> bookings,
            List<Trip> trips, Double balance) {
        super(id, name, enabled);
        this.cars = cars;
        this.bookings = bookings;
        this.trips = trips;
        this.balance = balance;
    }

    public List<Car> getCars() {
        return cars;
    }
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Trip> getTrips() {
        return trips;
    }
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }


}