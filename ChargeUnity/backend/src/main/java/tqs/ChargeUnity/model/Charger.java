package tqs.ChargeUnity.model;

import tqs.ChargeUnity.enums.ChargerType;
import tqs.ChargeUnity.enums.ChargerStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Charger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    private ChargerStatus status;

    private ChargerType type;

    private Double pricePerKWh;

    public Charger() {
    }

    public Charger(int id, Station station, ChargerStatus status, ChargerType type, Double pricePerKWh) {
        this.id = id;
        this.station = station;
        this.status = status;
        this.type = type;
        this.pricePerKWh = pricePerKWh;
    }

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

    @Override
    public String toString() {
        return "Charger "+ id + " - Type: " + type + " - Status: " + status + 
                " - " + round(pricePerKWh, 2) + "€/kWh" + 
                    " - Station: " + station.getName() + ";";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Charger charger = (Charger) obj;
        return id == charger.id && station.equals(charger.station) && status == charger.status && type == charger.type && pricePerKWh.equals(charger.pricePerKWh);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + station.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + pricePerKWh.hashCode();
        return result;
    }

}
