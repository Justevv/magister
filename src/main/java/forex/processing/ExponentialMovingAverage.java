package forex.processing;

import java.util.Arrays;

/**
 * Exponential Moving Average
 */
public class ExponentialMovingAverage {

    private double[] prices;
    private int period;

    private double[] periodSma;
    private double smoothingConstant;
    private double[] periodEma;

    public double[] calculate(double[] prices, int period) throws Exception {

        if (period >= prices.length)
            throw new Exception("Given period is bigger then given set of prices");

        this.prices = prices;
        this.period = period;

        this.smoothingConstant = 2d / (this.period + 1);

        this.periodSma = new double[this.prices.length];
        this.periodEma = new double[this.prices.length];

        SimpleMovingAverage sma = new SimpleMovingAverage();

        for (int i = (period - 1); i < this.prices.length; i++) {
            double[] slice = Arrays.copyOfRange(this.prices, 0, i + 1);
            double[] smaResults = sma.calculate(slice, this.period).getSMA();
            this.periodSma[i] = smaResults[smaResults.length - 1];

            if (i == (period - 1)) {
                this.periodEma[i] = this.periodSma[i];
            } else if (i > (period - 1)) {
                // Formula: (Close - EMA(previous day)) x multiplier +
                // EMA(previous day)
                this.periodEma[i] = (this.prices[i] - periodEma[i - 1]) * this.smoothingConstant
                        + this.periodEma[i - 1];
            }

            this.periodEma[i] = NumberFormatter.round(this.periodEma[i]);
        }

        return this.periodEma;
    }

}