package tqs.ChargeUnity.repository;

import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.model.Operator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {
    Optional<Operator> findByName(String name);
}
