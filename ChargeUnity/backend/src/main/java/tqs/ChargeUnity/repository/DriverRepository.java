package tqs.ChargeUnity.repository;

import tqs.ChargeUnity.model.Driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

  Optional<Driver> findByName(String name);
}
