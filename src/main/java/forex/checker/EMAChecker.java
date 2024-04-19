package forex.checker;

import forex.entity.Grid;
import forex.entity.SimpleGrid;
import forex.load.Price;
import forex.processing.ExponentialMovingAverage;
import forex.processing.GridService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

//@Service
@Data
@Builder
public class EMAChecker implements Callable<Boolean> {
    @Autowired
    private GridService gridService;
    private final List<Grid> grids = new ArrayList<>();
//    @Autowired
    private ExponentialMovingAverage exponentialMovingAverage;

    private int j;
    private Grid grid;
    private List<Price> priceList;

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


    private void buyOpen(Grid grid, float minPrice) {
        grid.getSteps().add(1);
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > minPrice) {
            grid.getSteps().add(6);
        }
        gridService.addWorkGrid(grid);
    }

    @Override
    public Boolean call() throws Exception {
        return checkEma(j, grid, priceList);
    }
}
