package forex.processing;

import java.util.Arrays;

/**
 * Exponential Moving Average
 */
public class ExponentialMovingAverage {
    private float[] periodSma;
    private float smoothingConstant;
    private float[] periodEma;

    public float[] calculate(double[] prices, int period) throws Exception {

        if (period >= prices.length)
            throw new Exception("Given period is bigger then given set of prices");

        this.smoothingConstant = 2f / (period + 1);

        this.periodSma = new float[prices.length];
        this.periodEma = new float[prices.length];

        SimpleMovingAverage sma = new SimpleMovingAverage();

        for (int i = (period - 1); i < prices.length; i++) {
            double[] slice = Arrays.copyOfRange(prices, 0, i + 1);
            float[] smaResults = sma.calculate(slice, period).getSMA();
            this.periodSma[i] = smaResults[smaResults.length - 1];

            if (i == (period - 1)) {
                this.periodEma[i] = this.periodSma[i];
            } else if (i > (period - 1)) {
                // Formula: (Close - EMA(previous day)) x multiplier +
                // EMA(previous day)
                this.periodEma[i] = (float) ((prices[i] - periodEma[i - 1]) * this.smoothingConstant
                                        + this.periodEma[i - 1]);
            }

            this.periodEma[i] = NumberFormatter.round(this.periodEma[i]);
        }

        return this.periodEma;
    }

}