package forex.processing;

import forex.load.Price;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GridGeneration {
    private Result result;
    private List<Grid> grids = new ArrayList<>();
    private List<Price> priceList;
    private List<Price> priceListM3;

    public List<Grid> getGrids() {
        return grids;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setPriceListM3(List<Price> priceListM3) {
        this.priceListM3 = priceListM3;
    }

    public void process(List<Price> priceList) {
        this.priceList = priceList;
        SimpleGrid simpleGrid = new SimpleGrid();
        for (int i = 0; i < priceList.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(priceList.get(i).getDateValue());
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {                       //во вторник ставим ожидание понедельника
                simpleGrid.setFirstDay(true);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && simpleGrid.isFirstDay()) {    //сброс в понедельник
                simpleGrid.setMinGrid(priceList.get(i).getMinPrice());
                simpleGrid.setMaxGrid(0);
                simpleGrid.setPulseCount(1);
                simpleGrid.setRollbackCount(0);
                simpleGrid.setFirstDay(false);
            }
            if (processGrid(simpleGrid, priceList.get(i))) {
                Grid grid = new Grid(calendar, simpleGrid.getMaxGrid(), simpleGrid.getMinGrid(), simpleGrid.getPulseCount(), simpleGrid.getRollbackCount());
                buy(grid, i);
                simpleGrid.setMinGrid(simpleGrid.getRecLow());  // reset
                simpleGrid.setPulseCount(1);
                i = i - simpleGrid.getRollbackCount();
                simpleGrid.setRollbackCount(0);
                simpleGrid.setMaxGrid(0);
            }
            result.processing(priceList.get(i));
        }
    }

    private boolean processM3(List<Price> priceList) {
        boolean work = false;
        SimpleGrid simpleGrid = new SimpleGrid();
        for (int i = 0; i < priceList.size(); i++) {
            if (processGrid(simpleGrid, priceList.get(i))) {
                work = true;
                break;
            }
        }
        return work;
    }

    private boolean processGrid(SimpleGrid grid, Price priceList) {
        boolean work = false;
        if (grid.getMinGrid() > priceList.getMinPrice()) { // create new min
            grid.setMinGrid(priceList.getMinPrice());
            grid.setMaxGrid(0);
            grid.setPulseCount(1);
            grid.setRollbackCount(0);
        } else if (grid.getMaxGrid() < priceList.getMaxPrice() || (grid.getMaxGrid() == priceList.getMaxPrice() && grid.getRollbackCount() <= 1)) {  // create new max
            grid.setMaxGrid(priceList.getMaxPrice());
            grid.setPulseCount(grid.getPulseCount() + 1);
            grid.setPulseCount(grid.getPulseCount() + grid.getRollbackCount());
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

    private void buy(Grid grid, int i) {
        boolean workM3 = false;
        if (grid.getSizeGrid() >= 300
//                && sizeGrid[transactionCount] <= 600
//                && buyPulseCount[transactionCount] >= 2
//                && buyPulseCount[transactionCount] <= 90
//                && ((grid.getBuyDataValue().get(Calendar.HOUR_OF_DAY) <= 17))
//                && ((priceList.get(i - rollbackCount).getDateValue().get(Calendar.HOUR_OF_DAY) >= 7))
//                && ((grid.getBuyDataValue().get(Calendar.HOUR_OF_DAY) >= 7 - (grid.getBuyRollbackCount() / 30)))
//                && ((priceList.get(i - rollbackCount).getDateValue().get(Calendar.HOUR_OF_DAY) <= 17))
        ) {
            grids.add(grid);
            for (int j = 0; j < priceListM3.size(); j++) {
                try {
                    if (priceListM3.get(j).getDateValue().after(priceList.get(i - grid.getBuyPulseCount() - grid.getBuyRollbackCount() - 2).getDateValue())) {
                        if (priceListM3.get(j).getMinPrice() == grid.getBuyMinGrid()) {
                            List<Price> m3 = priceListM3.subList(j, j + grid.getBuyPulseCount() + grid.getBuyRollbackCount());
                            workM3 = processM3(m3);
                            break;
                        }
                    }
                } catch (Exception e) {
//                    System.out.println(e);
                }
            }
            if (workM3) {
                buyOpen(grid, i);
            }
        }
    }

    private void buyOpen(Grid grid, int i) {
        grid.setStep(1);
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > priceList.get(i).getMinPrice()) {
            grid.setStep(6);
        }
        result.addWorkGrid(grid);
    }
}
