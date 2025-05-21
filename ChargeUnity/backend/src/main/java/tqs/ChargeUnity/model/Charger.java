package tqs.ChargeUnity.model;

import tqs.ChargeUnity.enums.ChargerType;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.config.Utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Charger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "station_id")
  @JsonBackReference
  private Station station;

  @OneToMany(mappedBy = "charger")
  private List<Booking> bookings = new ArrayList<>();

  private ChargerStatus status;

  private ChargerType type;

  private Double pricePerKWh;

  public Charger() {}

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
