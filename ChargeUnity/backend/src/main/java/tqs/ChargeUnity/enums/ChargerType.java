package tqs.ChargeUnity.enums;

public enum ChargerType {
    FAST,
    STANDARD,
    ECONOMY,
    UNKNOWN;

    public static ChargerType fromString(String type) {
        try {
            return ChargerType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
