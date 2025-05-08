package tqs.ChargeUnity.enums;

public enum BookingStatus {
    WAITING,
    CHARGING,
    COMPLETED,
    CANCELLED,
    EXPIRED,
    UNKNOWN;

    public static BookingStatus fromString(String status) {
        for (BookingStatus bookingStatus : BookingStatus.values()) {
            if (bookingStatus.name().equalsIgnoreCase(status)) {
                return bookingStatus;
            }
        }
        return UNKNOWN;
    }
}
