package tqs.ChargeUnity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

  public Station() {}

  @Override
  public String toString() {
    return "Station " + id + ": " + name + 
    ";\nOperator: " + operator.getName() + 
    ";\nAddress: " + address + ", " + city + ";";
  }
}
