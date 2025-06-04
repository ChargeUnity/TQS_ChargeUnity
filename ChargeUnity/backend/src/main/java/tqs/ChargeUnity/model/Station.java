package tqs.ChargeUnity.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Station {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private String city;
  private String address;
  private String latitude;
  private String longitude;

  @ManyToOne
  @JsonBackReference
  private Operator operator;

  @OneToMany(mappedBy = "station", fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Charger> chargers = new ArrayList<>();
  
  public Station() {}

  @Override
  public String toString() {
    return "Station "
        + id
        + ": "
        + name
        + ";\nOperator: "
        + operator.getName()
        + ";\nAddress: "
        + address
        + ", "
        + city
        + ";";
  }
}
