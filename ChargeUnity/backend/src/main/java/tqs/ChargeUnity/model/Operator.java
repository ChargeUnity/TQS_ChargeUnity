package tqs.ChargeUnity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@Entity
public class Operator extends User {

  @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Station> stations = new ArrayList<>();

  public Operator() {
    super();
  }

  public Operator(int id, String name, List<Station> stations) {
    super(id, name);
    this.stations = stations;
  }

  @Override
  public String toString() {
    String rStations = "";
    for (int i = 0; i < stations.size(); i++) {
      rStations += i + " - " + stations.get(i).getName();
      if (i != stations.size() - 1) {
        rStations += ", ";
      }
    }
    return "Operator " + super.toString() + " - Stations: [" + rStations + "];";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Operator operator = (Operator) obj;
    return super.equals(obj) && stations.equals(operator.stations);
  }

  @Override
  public int hashCode() {
    return super.hashCode() + 31 * stations.hashCode();
  }
}