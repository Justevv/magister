package forex.processing;

import forex.entity.Grid;
import forex.entity.SimpleGrid;
import forex.load.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
public class GridGenerator {
    @Autowired
    private GridService gridService;
    private final List<Grid> grids = new ArrayList<>();
    @Autowired
    private ExponentialMovingAverage exponentialMovingAverage;

    public List<Grid> getGrids() {
        return grids;
    }

    public void process(List<Price> priceListM2, List<Price> priceListM3) {
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
                buy(grid, i, priceListM2, priceListM3);
                i -= simpleGrid.getRollbackCount();
                resetSimpleGrid(simpleGrid, simpleGrid.getRecLow());
            }
            if (i > iMax) {
                iMax = i;
                gridService.processing(priceListM2.get(i));
            }
        }
    }

    private void resetSimpleGrid(SimpleGrid simpleGrid, float minGrid) {
        simpleGrid.setMinGrid(minGrid);
        simpleGrid.setPulseCount(1);
        simpleGrid.setRollbackCount(0);
        simpleGrid.setMaxGrid(0);
    }

    private boolean processM3(List<Price> priceList) {
        SimpleGrid simpleGrid = new SimpleGrid();
        return priceList.stream().anyMatch(x -> processGrid(simpleGrid, x));
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

    private void buy(Grid grid, int i, List<Price> priceList, List<Price> priceListM3) {
        if (grid.getSizeGrid() >= 250
//                && grid.getSizeGrid() <= 600
//                && grid.getBuyPulseCount() >= 2
//                && grid.getBuyPulseCount() <= 90
//                && grid.getBuyDataValue().getHour() <= 17
//                && priceList.get(i - grid.getBuyRollbackCount()).getDateValue().getHour() >= 7
//                && grid.getBuyDataValue().getHour() >= 7 - (grid.getBuyRollbackCount() / 30)
//                && priceList.get(i - grid.getBuyRollbackCount()).getDateValue().getHour() <= 17
        ) {
            grids.add(grid);
            int m2Bar = i - grid.getBuyPulseCount() - grid.getBuyRollbackCount() - 2;
            if (m2Bar >= 0) {
                for (int j = (m2Bar) * 2 / 3; j < priceListM3.size(); j++) {
                    if (priceListM3.get(j).getDateValue().isAfter(priceList.get(m2Bar).getDateValue()) && priceListM3.get(j).getMinPrice() == grid.getBuyMinGrid()) {
                        List<Price> m3 = priceListM3.subList(j, j + grid.getBuyPulseCount() + grid.getBuyRollbackCount());
                        grid.setM3Ok(processM3(m3));
                        grid.getEmaIntersect().setM3(checkEma(j, grid, priceListM3));
                        grid.getEmaIntersect().setM2(checkEma(i, grid, priceList));
                        if (grid.isM3Ok() && grid.getEmaIntersect().isM3() && grid.getEmaIntersect().isM2()) {
                            buyOpen(grid, priceList.get(i).getMinPrice());
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean checkEma(int j, Grid grid, List<Price> priceList) {
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
}
