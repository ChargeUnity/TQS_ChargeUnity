package tqs.ChargeUnity.dto;

import java.time.LocalDateTime;

public record BookingRequestDTO(
        int driverId,
        int chargerId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}
