package tqs.ChargeUnity.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  @JsonBackReference("driver-trips")
  private Driver driver;

  @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference("booking-trip")
  private List<Booking> bookings = new ArrayList<>();

  private String startCoordinates;
  private String endCoordinates;

  private LocalDateTime startTime;
  private String durationSeconds;

  private Double distance;
  private Double price;

  public Trip() {}

  @Override
  public String toString() {
    return "Trip "
        + id
        + " - Driver: "
        + driver.getName()
        + "\nStart Coordinates: "
        + startCoordinates
        + "\nEnd Coordinates: "
        + endCoordinates
        + "\nStart Time: "
        + startTime
        + "\nDuration: "
        + durationSeconds
        + "\nDistance: "
        + distance
        + "\nPrice: "
        + price;
  }
}
