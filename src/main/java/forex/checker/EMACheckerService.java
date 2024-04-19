package forex.checker;

import forex.entity.Grid;
import forex.load.Price;
import forex.processing.ExponentialMovingAverage;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Builder
public class EMACheckerService {
    @Autowired
    private ExponentialMovingAverage exponentialMovingAverage;

    public boolean checkEma(int j, Grid grid, List<Price> priceList) {
        var emaPeriods = List.of(34, 55, 89, 144, 233);
        for (int emaPeriod : emaPeriods) {
            if (j - emaPeriod >= 0 && checkEmaBase(emaPeriod, j, grid, priceList)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkEmaBase(int emaPeriod, int j, Grid grid, List<Price> priceList) {
        var m2 = priceList.subList(j - emaPeriod, j + grid.getBuyPulseCount() + 1);
        var arr = m2.stream().map(Price::getClosePrice).toList();

        try {
            var ema = exponentialMovingAverage.calculate(arr, emaPeriod);
            for (int i = 0; i < m2.size() - emaPeriod; i++) {
                if (m2.get(i + emaPeriod).getMaxPrice() >= ema.get(i) && m2.get(i + emaPeriod).getMinPrice() <= ema.get(i)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
