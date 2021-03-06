package forex.processing;

import java.util.Arrays;

/**
 * Simple Moving Average
 */
public class SimpleMovingAverage {
    public float[]  calculate(double[] price, int period) throws Exception {

        // ie: if you want 50 SMA then you need 50 data points
        if (price.length < period)
            throw new Exception("Not enough data points, given data size less then the indicated period");

        float[] results = new float[price.length];
        int maxLength = price.length - period;
        for (int i = 0; i <= maxLength; i++) {
            results[(i + period - 1)] = NumberFormatter
                    .round((float) ((Arrays.stream(Arrays.copyOfRange(price, i, (i + period))).sum()) / period));
        }
        return results;
    }

}