package tqs.ChargeUnity.model;

import lombok.Getter;
import lombok.Setter;
import tqs.ChargeUnity.enums.ChargerType;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.config.Utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Getter
@Setter
@Entity
public class Charger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "station_id")
  @JsonBackReference
  private Station station;

  @OneToMany(mappedBy = "charger", fetch = FetchType.EAGER)
  @JsonManagedReference("booking-charger")
  private List<Booking> bookings = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private ChargerStatus status;

  @Enumerated(EnumType.STRING)
  private ChargerType type;

  private Double pricePerKWh;

  public Charger() {}

  public void setPricePerKWh(double pricePerKWh) {
    if (pricePerKWh <= 0) {
      throw new IllegalArgumentException("Price per kWh must be positive.");
    }
    this.pricePerKWh = pricePerKWh;
  }

  @Override
  public String toString() {
    return "Charger "
        + id
        + " - Type: "
        + type
        + " - Status: "
        + status
        +
        // " - " + round(pricePerKWh, 2) + "€/kWh" +
        " - "
        + Utils.round(pricePerKWh, 2)
        + "€/kWh"
        + " - Station: "
        + station.getName()
        + ";";
  }
}
