package forex.processing;

import forex.load.ConvertM1ToM2;
import forex.load.Price;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GridGeneration {
    private static final int SIZE = 4000;    //Размер массивов
    private float maxGrid = 0; //максимум сетки
    private float minGrid = 2; //минимум сетки
    private int pulseCount = 0; //счетчик импульсов
    private int rollbackCount = 0;  //счетчик откатов
    public int i = 0;  //счетчик входных данных
    public float[] sizeGrid = new float[SIZE];  //Массив размера сетки
    public int transactionCount = 0;   //счетчик построкнных сеток
    public Calendar[] buyDataValue = new Calendar[SIZE];
    public float[] buyMaxGrid = new float[SIZE];
    public float[] buyMinGrid = new float[SIZE];
    public int[] buyPulseCount = new int[SIZE];
    public int[] buyRollbackCount = new int[SIZE];
    public int step[] = new int[SIZE];
    private boolean isFirstHigh = true;      //если первый high
    private boolean isFirstRec = true;       //???????????
    private boolean firstDay = true;         //флаг первого дня(для сброса в понедельник)
    private boolean maxmax = false;            //флаг ожидания пробоя МО после повторения максимума
    private int tempRec = 0;
    private float recLow = 0;
    private float bufMaxGrid = 0;
    private float bufMinGrid = 0;
    private int recNumber = 0;
    private int impCount = 0;

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

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public void process(List<Price> priceM1List) {
        priceList = convertM1ToM2.convert(priceM1List);
        for (i = 0; i < priceList.size(); i++) {
            {
                if (priceList.get(i).getDateValue().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)                           //во вторник ставим ожидание понедельника
                {
                    firstDay = true;
                }
                if (priceList.get(i).getDateValue().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && firstDay)                //сброс в понедельник
                {
                    minGrid = priceList.get(i).getMinPrice();
                    maxGrid = 0;
                    pulseCount = 1;
                    rollbackCount = 0;
                    firstDay = false;
                    //System.out.println(dateValue[i]);
                }
            }
            if (minGrid > priceList.get(i).getMinPrice()) {
                newMin();
            } else {
                if (isFirstHigh) {
                    maxGrid = priceList.get(i).getMaxPrice();
                    pulseCount++;
                    isFirstHigh = false;
                } else {
                    if (maxGrid < priceList.get(i).getMaxPrice()) {
                        newMax();
                    } else if (maxGrid == priceList.get(i).getMaxPrice()) {
                        if (rollbackCount <= 1) {
                            newMax();
                        } else {
                            rollbackCount++;
                            maxmax = true;
                            tempRec = rollbackCount;
                        }
                        if (pulseCount - rollbackCount == 0) {
                            buy();
                        }
                    } else {
                        rollbackCount++;
                        if (isFirstRec) {
                            recLow = priceList.get(i).getMinPrice();
                            isFirstRec = false;
                            recNumber = 1;
                        } else {
                            if (recLow > priceList.get(i).getMinPrice()) {
                                // recLow = minPrice[i];
                                recNumber = rollbackCount;
                            }
                            if (maxmax) {
                                pulseCount = pulseCount + tempRec;
                                rollbackCount = rollbackCount - tempRec;
                                maxmax = false;
                            }
                            if (rollbackCount - pulseCount == 0) {
                                buy();
                            }
                        }
                    }
                }
            }
            result.processing();
        }
    }

    public void newMax() {
        maxGrid = priceList.get(i).getMaxPrice();
        pulseCount++;
        pulseCount = pulseCount + rollbackCount;
        isFirstRec = true;
        rollbackCount = 0;
        maxmax = false;
    }

    public void newMin() {
        minGrid = priceList.get(i).getMinPrice();
        isFirstHigh = true;
        isFirstRec = true;
        pulseCount = 1;
        rollbackCount = 0;
    }

    public void reset() {
        bufMinGrid = minGrid;
        bufMaxGrid = maxGrid;
        minGrid = recLow;
        maxGrid = 0;
        impCount = rollbackCount + recNumber - 1;
        pulseCount = 1;
        i = i - rollbackCount;
        rollbackCount = 0;
    }

    public void buy() {
        Grid grid = new Grid(priceList.get(i).getDateValue(), maxGrid, minGrid, pulseCount, rollbackCount);
        buyDataValue[transactionCount] = priceList.get(i).getDateValue();
        buyMaxGrid[transactionCount] = maxGrid;
        buyMinGrid[transactionCount] = minGrid;
        sizeGrid[transactionCount] = (maxGrid - minGrid) * 100000;
        buyPulseCount[transactionCount] = pulseCount;
        buyRollbackCount[transactionCount] = rollbackCount;
        if (grid.getSizeGrid() >= 300
//                && sizeGrid[transactionCount] <= 600
//                && buyPulseCount[transactionCount] >= 2
//                && buyPulseCount[transactionCount] <= 90
//                && ((d.dateValue[i].getHours() <= 17))
//                && ((d.dateValue[i - rollbackCount].getHours() >= 7))
//                && ((d.dateValue[i].getHours() >= 7 - (rollbackCount / 30)))
//                && ((d.dateValue[i - rollbackCount].getHours() <= 17))
        ) {
            grids.add(grid);
            buyOpen();
        }
        reset();
    }

    public void buyOpen() {
        step[transactionCount] = 1;
        if ((grids.get(transactionCount).getBuyMaxGrid() - grids.get(transactionCount).getBuyMinGrid()) * 0.382 + grids.get(transactionCount).getBuyMinGrid() > priceList.get(i).getMinPrice()) {
            step[transactionCount] = 6;
        }
        transactionCount++;
    }
}
