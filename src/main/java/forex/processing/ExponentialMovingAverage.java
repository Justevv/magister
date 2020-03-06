package forex.processing;

import java.util.Arrays;

/**
 * Exponential Moving Average
 */
public class ExponentialMovingAverage {
    private SimpleMovingAverage sma = new SimpleMovingAverage();

    public float[] calculate(double[] prices, int period) throws Exception {

        if (period >= prices.length)
            throw new Exception("Given period is bigger then given set of prices");

        float smoothingConstant = 2f / (period + 1);
        float[] periodEma = new float[prices.length];

        for (int i = (period - 1); i < prices.length; i++) {
            if (i == (period - 1)) {
                double[] slice = Arrays.copyOfRange(prices, 0, i + 1);
                float[] smaResults = sma.calculate(slice, period);
                periodEma[i] = smaResults[smaResults.length - 1];
            } else if (i > (period - 1)) {
                // Formula: (Close - EMA(previous day)) x multiplier + EMA(previous day)
                periodEma[i] = (float) ((prices[i] - periodEma[i - 1]) * smoothingConstant + periodEma[i - 1]);
            }
            periodEma[i] = NumberFormatter.round(periodEma[i]);
        }
        return periodEma;
    }

}