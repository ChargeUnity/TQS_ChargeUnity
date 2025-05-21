package tqs.ChargeUnity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Entity
public class Operator extends User {

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
    
    public Operator() {
        super();
    }

    public Operator(int id, String name, Station station) {
        super(id, name);
        this.station = station;
    }

    @Override
    public String toString() {
        return "Operator " + super.toString() + " - Station: " + station.getName() + ";";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Operator operator = (Operator) obj;
        return super.equals(obj) && station.equals(operator.station);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 31 * station.hashCode();
    }

    public void setStation(Station station) {
        this.station = station;
    }
    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        super.setId(id);
    }
    public String getName() {
        return super.getName();
    }
    public void setName(String name) {
        super.setName(name);
    }

}
