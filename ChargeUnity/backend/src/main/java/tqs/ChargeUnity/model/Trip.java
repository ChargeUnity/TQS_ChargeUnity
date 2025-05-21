package tqs.ChargeUnity.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Trip {
    //main class for the trip



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "driver_id")
        private Driver driver;

        @OneToMany(mappedBy = "trip")
        private List<Booking> bookings = new ArrayList<>();

        private String startCoordinates;
        private String endCoordinates;

        private LocalDateTime startTime;
        private String durationSeconds;

        private Double distance;
        private Double price;


    public Trip() {
        }
        @Override
        public String toString () {
            return "Trip " + id + " - Driver: " + driver.getName() +
                    "\nStart Coordinates: " + startCoordinates +
                    "\nEnd Coordinates: " + endCoordinates +
                    "\nStart Time: " + startTime +
                    "\nDuration: " + durationSeconds +
                    "\nDistance: " + distance +
                    "\nPrice: " + price;
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
    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public String getStartCoordinates() {
        return startCoordinates;
    }
    public void setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
    }
    public String getEndCoordinates() {
        return endCoordinates;
    }
    public void setEndCoordinates(String endCoordinates) {
        this.endCoordinates = endCoordinates;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public String getDurationSeconds() {
        return durationSeconds;
    }
    public void setDurationSeconds(String durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

}