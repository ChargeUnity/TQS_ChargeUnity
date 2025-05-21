package tqs.ChargeUnity.enums;

public enum ChargerStatus {
  AVAILABLE,
  UNAVAILABLE,
  UNDER_MAINTENANCE,
  OUT_OF_SERVICE,
  UNKNOWN;

  public static ChargerStatus fromString(String status) {
    try {
      return ChargerStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      return UNKNOWN;
    }
  }
}
