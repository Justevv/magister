package forex.processing;

import forex.load.ConvertM1ToM2;
import forex.load.PriceM1;
import forex.load.PriceM2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GridGeneration {
    public static int size = 4000000;    //Размер массивов
    public double maxGrid = 0; //максимум сетки
    public double minGrid = 2; //минимум сетки
    public int pulseCount = 0; //счетчик импульсов
    public int rollbackCount = 0;  //счетчик откатов
    public int i = 0;  //счетчик входных данных
    public int transactionCount = 0;   //счетчик построкнных сеток
    public double[] sizeGrid = new double[size];  //Массив размера сетки
        public Date[] buyDataValue = new Date[size];
    public Double[] buyMaxGrid = new Double[size];
    public Double[] buyMinGrid = new Double[size];
    public Integer[] buyPulseCount = new Integer[size];
    public Integer[] buyRollbackCount = new Integer[size];
    public boolean isFirstHigh = true;      //если первый high
    public boolean isFirstRec = true;       //???????????
    public boolean firstday = true;         //флаг первого дня(для сброса в понедельник)
    public boolean maxmax = false;            //флаг ожидания пробоя МО после повторения максимума
    public int tempRec = 0;
    public double recLow = 0;
    public double bufMaxGrid = 0;
    public double bufMinGrid = 0;
    public int recNumber = 0;
    public int impCount = 0;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm z");
    public int countDeal = 50000;
    public int step[] = new int[countDeal];

    public List<Grid> getGrids() {
        return grids;
    }

    private List<Grid> grids = new ArrayList<>();

    private ConvertM1ToM2 convertM1ToM2 = new ConvertM1ToM2();
    Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    private List<PriceM2> priceM2List;

    public List<PriceM2> getPriceM2List() {
        return priceM2List;
    }

    public void setPriceM2List(List<PriceM2> priceM2List) {
        this.priceM2List = priceM2List;
    }

    public void process(List<PriceM1> priceM1List) {
        priceM2List = convertM1ToM2.convert(priceM1List);
        for (i = 0; i < priceM2List.size(); i++) {
            {
                if (priceM2List.get(i).getDateValue().getDay() == 2)                           //во вторник ставим ожидание понедельника
                {
                    firstday = true;
                }
                if (priceM2List.get(i).getDateValue().getDay() == 1 && firstday)                //сброс в понедельник
                {
                    minGrid = priceM2List.get(i).getMinPrice();
                    maxGrid = 0;
                    pulseCount = 1;
                    rollbackCount = 0;
                    firstday = false;
                    //System.out.println(dateValue[i]);
                }
            }
            if (minGrid > priceM2List.get(i).getMinPrice()) {
                newMin();
            } else {
                if (isFirstHigh) {
                    maxGrid = priceM2List.get(i).getMaxPrice();
                    pulseCount++;
                    isFirstHigh = false;
                } else {
                    if (maxGrid < priceM2List.get(i).getMaxPrice()) {
                        newMax();
                    } else if (maxGrid == priceM2List.get(i).getMaxPrice()) {
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
                            recLow = priceM2List.get(i).getMinPrice();
                            isFirstRec = false;
                            recNumber = 1;
                        } else {
                            if (recLow > priceM2List.get(i).getMinPrice()) {
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
        maxGrid = priceM2List.get(i).getMaxPrice();
        pulseCount++;
        pulseCount = pulseCount + rollbackCount;
        isFirstRec = true;
        rollbackCount = 0;
        maxmax = false;
    }

    public void newMin() {
        minGrid = priceM2List.get(i).getMinPrice();
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
        Grid grid = new Grid(priceM2List.get(i).getDateValue(), maxGrid, minGrid, pulseCount, rollbackCount);
        buyDataValue[transactionCount] = priceM2List.get(i).getDateValue();
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
            buyopen();
        }
        reset();
    }

    public void buyopen() {
        step[transactionCount] = 1;
        if ((grids.get(transactionCount).getBuyMaxGrid() - grids.get(transactionCount).getBuyMinGrid()) * 0.382 + grids.get(transactionCount).getBuyMinGrid() > priceM2List.get(i).getMinPrice()) {
            step[transactionCount] = 6;
        }
        transactionCount++;
    }
}
