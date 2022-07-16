package forex.processing;

import java.util.Arrays;

/**
 * Simple Moving Average
 */
public class SimpleMovingAverage {
    public double calculate(double[] price, int period) throws Exception {

        // ie: if you want 50 SMA then you need 50 data points
        if (price.length < period)
            throw new Exception("Not enough data points, given data size less then the indicated period");
        return NumberFormatter.round((Arrays.stream(Arrays.copyOfRange(price, 0, (period))).sum()) / period);
    }

}