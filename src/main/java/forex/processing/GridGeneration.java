package forex.processing;

import forex.load.ConvertM1ToM2;
import forex.load.Price;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GridGeneration {
    private static final int SIZE = 4000;    //Размер массивов
    public float[] sizeGrid = new float[SIZE];  //Массив размера сетки
    public int transactionCount = 0;   //счетчик построкнных сеток
    public Calendar[] buyDataValue = new Calendar[SIZE];
    public float[] buyMaxGrid = new float[SIZE];
    public float[] buyMinGrid = new float[SIZE];
    public int[] buyPulseCount = new int[SIZE];
    public int[] buyRollbackCount = new int[SIZE];
    int step[] = new int[SIZE];

    public List<Grid> getGrids() {
        return grids;
    }

    private List<Grid> grids = new ArrayList<>();

    private ConvertM1ToM2 convertM1ToM2 = new ConvertM1ToM2();
    Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    private List<Price> priceList;
    private List<Price> priceListM3;

    public void setPriceListM3(List<Price> priceListM3) {
        this.priceListM3 = priceListM3;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public void process(List<Price> priceList) {
        this.priceList = priceList;
        float maxGrid = 0; //максимум сетки
        float minGrid = 2; //минимум сетки
        int pulseCount = 0; //счетчик импульсов
        int rollbackCount = 0;  //счетчик откатов
        boolean firstDay = true;         //флаг первого дня(для сброса в понедельник)
        boolean maxmax = false;            //флаг ожидания пробоя МО после повторения максимума
        int tempRec = 0;
        float recLow = 0;
        for (int i = 0; i < priceList.size(); i++) {
            if (priceList.get(i).getDateValue().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {                       //во вторник ставим ожидание понедельника
                firstDay = true;
            }
            if (priceList.get(i).getDateValue().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && firstDay) {    //сброс в понедельник
                minGrid = priceList.get(i).getMinPrice();
                maxGrid = 0;
                pulseCount = 1;
                rollbackCount = 0;
                firstDay = false;
            }
            if (minGrid > priceList.get(i).getMinPrice()) { // create new min
                minGrid = priceList.get(i).getMinPrice();
                maxGrid = 0;
                pulseCount = 1;
                rollbackCount = 0;
            } else {
                if (maxGrid < priceList.get(i).getMaxPrice() || (maxGrid == priceList.get(i).getMaxPrice() && rollbackCount <= 1)) {  // create new max
                    maxGrid = priceList.get(i).getMaxPrice();
                    pulseCount++;
                    pulseCount += rollbackCount;
                    recLow = 0;
                    rollbackCount = 0;
                    maxmax = false;
                } else if (maxGrid == priceList.get(i).getMaxPrice() && rollbackCount > 1) {
                    rollbackCount++;
                    maxmax = true;
                    tempRec = rollbackCount;
                } else {
                    rollbackCount++;
                    if (recLow == 0) {
                        recLow = priceList.get(i).getMinPrice();
                    } else {
                        if (maxmax) {
                            pulseCount = pulseCount + tempRec;
                            rollbackCount = rollbackCount - tempRec;
                            maxmax = false;
                        }
                    }
                }
                if (rollbackCount - pulseCount == 0) {
                    Grid grid = new Grid(priceList.get(i).getDateValue(), maxGrid, minGrid, pulseCount, rollbackCount);
                    buy(grid, i, rollbackCount);
                    minGrid = recLow;  // reset
                    pulseCount = 1;
                    i = i - rollbackCount;
                    rollbackCount = 0;
                    maxGrid = 0;
                }
            }
            result.processing(i);
        }
    }

    public boolean processM3(List<Price> priceList) {
        boolean work = false;
        float maxGrid = 0; //максимум сетки
        float minGrid = 2; //минимум сетки
        int pulseCount = 0; //счетчик импульсов
        int rollbackCount = 0;  //счетчик откатов
        boolean maxmax = false;            //флаг ожидания пробоя МО после повторения максимума
        int tempRec = 0;
        float recLow = 0;
        for (int i = 0; i < priceList.size(); i++) {
            if (minGrid > priceList.get(i).getMinPrice()) { // create new min
                minGrid = priceList.get(i).getMinPrice();
                maxGrid = 0;
                pulseCount = 1;
                rollbackCount = 0;
            } else {
                if (maxGrid < priceList.get(i).getMaxPrice() || (maxGrid == priceList.get(i).getMaxPrice() && rollbackCount <= 1)) {  // create new max
                    maxGrid = priceList.get(i).getMaxPrice();
                    pulseCount++;
                    pulseCount += rollbackCount;
                    recLow = 0;
                    rollbackCount = 0;
                    maxmax = false;
                } else if (maxGrid == priceList.get(i).getMaxPrice() && rollbackCount > 1) {
                    rollbackCount++;
                    maxmax = true;
                    tempRec = rollbackCount;
                } else {
                    rollbackCount++;
                    if (recLow == 0) {
                        recLow = priceList.get(i).getMinPrice();
                    } else {
                        if (maxmax) {
                            pulseCount = pulseCount + tempRec;
                            rollbackCount = rollbackCount - tempRec;
                            maxmax = false;
                        }
                    }
                }
                if (rollbackCount - pulseCount == 0) {
                    work = true;
                }
            }
        }
        return work;
    }

    private void buy(Grid grid, int i, int rollbackCount) {
        boolean workM3 = false;
        buyDataValue[transactionCount] = grid.getBuyDataValue();
        buyMaxGrid[transactionCount] = grid.getBuyMaxGrid();
        buyMinGrid[transactionCount] = grid.getBuyMinGrid();
        sizeGrid[transactionCount] = grid.getSizeGrid();
        buyPulseCount[transactionCount] = grid.getBuyPulseCount();
        buyRollbackCount[transactionCount] = grid.getBuyRollbackCount();
        if (grid.getSizeGrid() >= 300
//                && sizeGrid[transactionCount] <= 600
//                && buyPulseCount[transactionCount] >= 2
//                && buyPulseCount[transactionCount] <= 90
//                && ((grid.getBuyDataValue().get(Calendar.HOUR_OF_DAY) <= 17))
//                && ((priceList.get(i - rollbackCount).getDateValue().get(Calendar.HOUR_OF_DAY) >= 7))
//                && ((grid.getBuyDataValue().get(Calendar.HOUR_OF_DAY) >= 7 - (rollbackCount / 30)))
//                && ((priceList.get(i - rollbackCount).getDateValue().get(Calendar.HOUR_OF_DAY) <= 17))
        ) {
            grids.add(grid);
            for (int j = 0; j < priceListM3.size(); j++) {
                if (priceListM3.get(j).getDateValue().after(priceList.get(i - grid.getBuyPulseCount() - grid.getBuyRollbackCount() - 2).getDateValue())) {
                    if (priceListM3.get(j).getMinPrice() == grid.getBuyMinGrid()) {
                        List<Price> m3 = priceListM3.subList(j, j + grid.getBuyPulseCount() + grid.getBuyRollbackCount());
                        workM3 = processM3(m3);
                        break;
                    }
                }
            }
            if (workM3) {
                buyOpen(i);
            }
        }
    }

    private void buyOpen(int i) {
        step[transactionCount] = 1;
        if ((grids.get(transactionCount).getBuyMaxGrid() - grids.get(transactionCount).getBuyMinGrid()) * 0.382 + grids.get(transactionCount).getBuyMinGrid() > priceList.get(i).getMinPrice()) {
            step[transactionCount] = 6;
        }
        transactionCount++;
    }
}
