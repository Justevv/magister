package forex.processing;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Simple Moving Average
 */
@Service
public class SimpleMovingAverage {
    public double calculate(List<Float> price, int period) throws Exception {

        // ie: if you want 50 SMA then you need 50 data points
        if (price.size() < period)
            throw new Exception("Not enough data points, given data size less then the indicated period");
        return NumberFormatter.round((price.subList(0, period).stream().mapToDouble(x -> x).sum()) / period);
    }

}