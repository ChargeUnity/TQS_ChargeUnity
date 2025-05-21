package tqs.ChargeUnity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.ChargeUnity.model.Station;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Integer> {
  List<Station> findByCityIgnoreCase(String city);

  List<Station> findByLatitudeAndLongitude(String latitude, String longitude);

  List<Station> findByOperators_Id(int operatorId);
}
