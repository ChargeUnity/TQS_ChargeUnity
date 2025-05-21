package tqs.ChargeUnity.model;

import tqs.ChargeUnity.enums.BookingStatus;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
  @JsonBackReference
  private Trip trip;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  @JsonBackReference
  private Driver driver;

  @ManyToOne
  @JoinColumn(name = "charger_id")
  @JsonBackReference
  private Charger charger;

  @ManyToOne
  @JoinColumn(name = "car_id")
  @JsonBackReference
  private Car car;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  private Double price;
  @Enumerated(EnumType.STRING)
  private BookingStatus status;

  public Booking() {}

  public void setStartTime(LocalDateTime startTime) {
    if (endTime != null && startTime.isAfter(endTime)) {
      throw new IllegalArgumentException("Start time must be before end time");
    }
    this.startTime = startTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    if (startTime != null && endTime.isBefore(startTime)) {
      throw new IllegalArgumentException("End time must be after start time");
    }
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "Booking "
        + id
        + " - Trip: "
        + trip.getId()
        + "\nDriver: "
        + driver.getName()
        + "\nCharger: "
        + charger.getId()
        + "\nStart Time: "
        + startTime
        + "\nEnd Time: "
        + endTime
        + "\nPrice: "
        + price
        + "\nStatus: "
        + status;
  }
}
    
