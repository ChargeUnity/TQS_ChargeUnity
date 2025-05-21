package tqs.ChargeUnity.config;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

  /**
   * Rounds a double value to the specified number of decimal places.
   *
   * @param value the double value to round
   * @param places the number of decimal places to round to
   * @return the rounded double value
   */
  public static Double round(Double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  /**
   * Checks if a point is within a given radius using the Haversine formula.
   *
   * @param stationLatitude the latitude of the station (as String)
   * @param stationLongitude the longitude of the station (as String)
   * @param targetLatitude the latitude of the target location
   * @param targetLongitude the longitude of the target location
   * @param radius the radius (in kilometers)
   * @return true if the point is within the radius, false otherwise
   */
  public static boolean isWithinRadius(
      String stationLatitude, String stationLongitude, 
      double targetLatitude, double targetLongitude, 
      double radius) {

    double stationLat = Double.parseDouble(stationLatitude);
    double stationLon = Double.parseDouble(stationLongitude);

    final double EARTH_RADIUS_KM = 6371.0;

    double latDistance = Math.toRadians(targetLatitude - stationLat);
    double lonDistance = Math.toRadians(targetLongitude - stationLon);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
               + Math.cos(Math.toRadians(stationLat)) * Math.cos(Math.toRadians(targetLatitude))
               * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = EARTH_RADIUS_KM * c;

    return distance <= radius;
  }

}