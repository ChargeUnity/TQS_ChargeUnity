package tqs.ChargeUnity.config;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    
    /**
     * Rounds a double value to the specified number of decimal places.
     *
     * @param value  the double value to round
     * @param places the number of decimal places to round to
     * @return the rounded double value
     */
    public static Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
