package tqs.ChargeUnity.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Charger> chargers = new ArrayList<>();

  @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Operator> operators = new ArrayList<>();

  public Station() {}

  @Override
  public String toString() {
    String rOperators = "";
    for (int i = 0; i < operators.size(); i++) {
      rOperators += i + " - " + operators.get(i).toString();
      if (i != operators.size() - 1) {
        rOperators += "\n";
      }
    }

    String rChargers = "";
    for (int i = 0; i < chargers.size(); i++) {
      rChargers += i + " - " + chargers.get(i).toString();
      if (i != chargers.size() - 1) {
        rChargers += "\n";
      }
    }

    return "Station "
        + id
        + ": "
        + name
        + ";"
        + "\nAddress: "
        + address
        + ", "
        + city
        + ";"
        + "\nOperators: "
        + operators.size()
        + ";"
        + "\nOperators List:\n"
        + rOperators
        + "\nChargers: "
        + chargers.size()
        + ";"
        + "\nChargers List:\n"
        + rChargers;
  }
}
