package forex.processing;

import java.math.BigDecimal;

public class NumberFormatter {

    private NumberFormatter() {
    }

    public static float round(double value) {
        return NumberFormatter.round(value, 5);
    }

    public static float round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }

}
