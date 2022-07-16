package forex.processing;

import java.util.ArrayList;
import java.util.List;

/**
 * Exponential Moving Average
 */
public class ExponentialMovingAverage {
    private final SimpleMovingAverage sma = new SimpleMovingAverage();

    public List<Float> calculate(List<Float> prices, int period) throws Exception {

        if (period >= prices.size())
            throw new Exception("Given period is bigger then given set of prices");

        float smoothingConstant = 2f / (period + 1);
        List<Float> periodEma = new ArrayList<>(prices.size() - period + 1);

        var slice = prices.subList(0, period);
        periodEma.add(NumberFormatter.round(sma.calculate(slice, period)));
        for (int i = 1; i < prices.size() - (period - 1); i++) {
            // Formula: (Close - EMA(previous day)) x multiplier + EMA(previous day)
            periodEma.add(NumberFormatter.round((prices.get(i + (period - 1)) - periodEma.get(i - 1)) * smoothingConstant + periodEma.get(i - 1)));
        }
        return periodEma;
    }

}