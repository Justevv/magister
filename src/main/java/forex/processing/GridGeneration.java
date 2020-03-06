package forex.processing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import forex.load.Price;

public class GridGeneration {
    private Result result;
    private List<Grid> grids = new ArrayList<>();
    private List<Price> priceList;
    private List<Price> priceListM1;
    private List<Price> priceListM3;
    ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage();

    public List<Grid> getGrids() {
        return grids;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setPriceListM1(List<Price> priceListM1) {
        this.priceListM1 = priceListM1;
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
        if (grid.getSizeGrid() >= 300
//                && grid.getSizeGrid() <= 600
//                && grid.getBuyPulseCount() >= 2
//                && grid.getBuyPulseCount() <= 90
//                && ((grid.getBuyDataValue().get(Calendar.HOUR_OF_DAY) <= 17))
//                && ((priceList.get(i - rollbackCount).getDateValue().get(Calendar.HOUR_OF_DAY) >= 7))
//                && ((grid.getBuyDataValue().get(Calendar.HOUR_OF_DAY) >= 7 - (grid.getBuyRollbackCount() / 30)))
//                && ((priceList.get(i - rollbackCount).getDateValue().get(Calendar.HOUR_OF_DAY) <= 17))
        ) {
            grids.add(grid);
            int m2Bar = i - grid.getBuyPulseCount() - grid.getBuyRollbackCount() - 2;
            if (m2Bar >= 0) {
                for (int j = (m2Bar) * 2 / 3; j < priceListM3.size(); j++) {
                    if (priceListM3.get(j).getDateValue().after(priceList.get(m2Bar).getDateValue()) && priceListM3.get(j).getMinPrice() == grid.getBuyMinGrid()) {
                        List<Price> m3 = priceListM3.subList(j, j + grid.getBuyPulseCount() + grid.getBuyRollbackCount());
                        if (processM3(m3) && checkEma(j, grid, priceListM3) && checkEma(i, grid, priceList)) {
                            buyOpen(grid, i);
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean checkEma(int j, Grid grid, List<Price> priceList) {
        int[] emaPeriods = {34, 55, 89, 144, 233};
        boolean intersectionEma = false;
        for (int emaPeriod : emaPeriods) {
            if (j - emaPeriod >= 0 && checkEmaBase(emaPeriod, j, grid, priceList)) {
                intersectionEma = true;
                break;
            }
        }
        return intersectionEma;
    }

    private boolean checkEmaBase(int emaPeriod, int j, Grid grid, List<Price> priceList) {
        boolean intersection = false;
        List<Price> m2 = priceList.subList(j - emaPeriod, j + grid.getBuyPulseCount());
        double[] arr = new double[m2.size()];
        float[] ema = null;
        for (int i = 0; i < m2.size(); i++) {
            arr[i] = m2.get(i).getClosePrice();
        }
        try {
            ema = exponentialMovingAverage.calculate(arr, emaPeriod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < m2.size(); i++) {
            if (priceList.get(j + i).getMaxPrice() >= ema[i] && priceList.get(j + i).getMinPrice() <= ema[i]) {
                intersection = true;
                break;
            }
        }
        return intersection;
    }


    private void buyOpen(Grid grid, int i) {
        grid.setStep(1);
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > priceList.get(i).getMinPrice()) {
            grid.setStep(6);
        }
        result.addWorkGrid(grid);
    }
}
