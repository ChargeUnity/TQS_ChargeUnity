package tqs.ChargeUnity.model;

import tqs.ChargeUnity.enums.BookingStatus;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    private Charger charger;

    //car_id
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double price;
    private BookingStatus status;

    public Booking() {
    }

    @Override
    public String toString() {
        return "Booking " + id + " - Trip: " + trip.getId() +
                "\nDriver: " + driver.getName() +
                "\nCharger: " + charger.getId() +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nPrice: " + price +
                "\nStatus: " + status;
    }
}
    
