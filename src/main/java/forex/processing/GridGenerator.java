package forex.processing;

import forex.checker.BuyCheckerService;
import forex.entity.Grid;
import forex.entity.SimpleGrid;
import forex.load.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
@Slf4j
public class GridGenerator {
    @Autowired
    private GridService gridService;
    @Autowired
    private BuyCheckerService buyChecker;

    public void process(List<Price> priceListM2, List<Price> priceListM3) {
        long startTime = System.currentTimeMillis();
        SimpleGrid simpleGrid = new SimpleGrid();
        var iMax = 0;
        for (int i = 1; i < priceListM2.size(); i++) {
            var localDateTime = priceListM2.get(i).getDateValue();

            if (localDateTime.getDayOfWeek() == DayOfWeek.MONDAY
                    && priceListM2.get(i - 1).getDateValue().getDayOfWeek() != DayOfWeek.MONDAY) {    //сброс в понедельник
                resetSimpleGrid(simpleGrid, priceListM2.get(i).getMinPrice());
            }
            if (processGrid(simpleGrid, priceListM2.get(i))) {
                Grid grid = new Grid(localDateTime, simpleGrid.getMaxGrid(), simpleGrid.getMinGrid(), simpleGrid.getPulseCount(), simpleGrid.getRollbackCount());
                buyChecker.checkBuy(grid, i, priceListM2, priceListM3);
                i -= simpleGrid.getRollbackCount();
                resetSimpleGrid(simpleGrid, simpleGrid.getRecLow());
            }
            if (i > iMax) {
                iMax = i;
                gridService.processing(priceListM2.get(i));
            }
        }
        log.info("generation grid execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
    }

    private void resetSimpleGrid(SimpleGrid simpleGrid, int minGrid) {
        simpleGrid.setMinGrid(minGrid);
        simpleGrid.setPulseCount(1);
        simpleGrid.setRollbackCount(0);
        simpleGrid.setMaxGrid(0);
    }

    private boolean processGrid(SimpleGrid grid, Price priceList) {
        boolean work = false;
        if (grid.getMinGrid() > priceList.getMinPrice()) { // create new min
            resetSimpleGrid(grid, priceList.getMinPrice());
        } else if (grid.getMaxGrid() < priceList.getMaxPrice() || (grid.getMaxGrid() == priceList.getMaxPrice() && grid.getRollbackCount() <= 1)) {  // create new max
            grid.setMaxGrid(priceList.getMaxPrice());
            grid.setPulseCount(grid.getPulseCount() + 1 + grid.getRollbackCount());
            grid.setRecLow(0);
            grid.setRollbackCount(0);
            grid.setMaxMax(false);
        } else if (grid.getMaxGrid() == priceList.getMaxPrice() && grid.getRollbackCount() > 1) {
            grid.setRollbackCount(grid.getRollbackCount() + 1);
            grid.setMaxMax(true);
            grid.setTempRec(grid.getRollbackCount());
        } else {
            grid.setRollbackCount(grid.getRollbackCount() + 1);
            if (grid.getRecLow() == 0) {
                grid.setRecLow(priceList.getMinPrice());
            } else if (grid.isMaxMax()) {
                grid.setPulseCount(grid.getPulseCount() + grid.getTempRec());
                grid.setRollbackCount(grid.getRollbackCount() - grid.getTempRec());
                grid.setMaxMax(false);
            }
        }
        if (grid.getRollbackCount() - grid.getPulseCount() == 0) {
            work = true;
        }
        return work;
    }

}
