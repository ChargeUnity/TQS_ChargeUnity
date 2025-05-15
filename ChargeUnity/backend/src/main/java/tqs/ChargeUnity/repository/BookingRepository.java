package tqs.ChargeUnity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.ChargeUnity.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Get all bookings made by a specific driver
    List<Booking> findByDriverId(Integer driverId);

    // Check for conflicting bookings on the same charger
    boolean existsByChargerIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Integer chargerId,
            LocalDateTime endTime,
            LocalDateTime startTime
    );

    // Optional: Retrieve all bookings for a charger between two dates (useful for operator dashboards)
    List<Booking> findByChargerIdAndStartTimeBetween(Integer chargerId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT b FROM Booking b WHERE b.charger.id = :chargerId AND b.startTime < :end AND b.endTime > :start")
    List<Booking> findOverlappingBookings(@Param("chargerId") int chargerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<Booking> findByChargerId(int chargerId);
}
