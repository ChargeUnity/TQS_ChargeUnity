package tqs.ChargeUnity.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.ChargeUnity.model.Charger;
import tqs.ChargeUnity.model.Station;
import tqs.ChargeUnity.enums.ChargerStatus;

import java.util.List;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Integer> {

    List<Charger> findByStation(Station station);

    List<Charger> findByStatus(ChargerStatus status);

}

