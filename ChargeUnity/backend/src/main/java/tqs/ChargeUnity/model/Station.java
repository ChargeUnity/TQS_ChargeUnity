package tqs.ChargeUnity.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
    private List<Charger> chargers = new ArrayList<>();

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Operator> operators = new ArrayList<>();

    public Station() {
    }

    public Station(int id, String name, String city, String address, String latitude, String longitude,
            List<Charger> chargers, List<Operator> operators) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargers = chargers;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Charger> getChargers() {
        return chargers;
    }
    public void setChargers(List<Charger> chargers) {
        this.chargers = chargers;
    }

    public List<Operator> getOperators() {
        return operators;
    }
    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

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

        return "Station " + id + ": " + name + ";" +
                "\nAddress: " + address + ", " + city + ";" +
                "\nOperators: " + operators.size() + ";" +
                "\nOperators List:\n" + rOperators +
                "\nChargers: " + chargers.size() + ";" +
                "\nChargers List:\n" + rChargers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Station station = (Station) obj;
        return id == station.id && name.equals(station.name) && city.equals(station.city) && address.equals(station.address)
                && latitude.equals(station.latitude) && longitude.equals(station.longitude);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }
}