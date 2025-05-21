package tqs.ChargeUnity.model;

import lombok.Getter;
import lombok.Setter;
import tqs.ChargeUnity.enums.ChargerType;
import tqs.ChargeUnity.enums.ChargerStatus;
import tqs.ChargeUnity.config.Utils;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
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
    private Station station;

    @OneToMany(mappedBy = "charger")
    private List<Booking> bookings = new ArrayList<>();

    private ChargerStatus status;

    private ChargerType type;

    private Double pricePerKWh;

    public Charger() {
    }

    @Override
    public String toString() {
        return "Charger "+ id + " - Type: " + type + " - Status: " + status + 
                //" - " + round(pricePerKWh, 2) + "€/kWh" + 
                " - " + Utils.round(pricePerKWh, 2) + "€/kWh" +
                " - Station: " + station.getName() + ";";
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Station getStation() {
        return station;
    }
    public void setStation(Station station) {
        this.station = station;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public ChargerStatus getStatus() {
        return status;
    }
    public void setStatus(ChargerStatus status) {
        this.status = status;
    }
    public ChargerType getType() {
        return type;
    }
    public void setType(ChargerType type) {
        this.type = type;
    }
    public Double getPricePerKWh() {
        return pricePerKWh;
    }
    public void setPricePerKWh(Double pricePerKWh) {
        this.pricePerKWh = pricePerKWh;
    }



}
