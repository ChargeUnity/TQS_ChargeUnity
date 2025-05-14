package tqs.ChargeUnity.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import tqs.ChargeUnity.model.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByDriverId(int driverId);


}
