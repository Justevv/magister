package forex.processing;

import forex.load.DataLoading;

import java.text.SimpleDateFormat;
import java.util.Date;

import static forex.processing.Result.processing;

public class GridGeneration {
    public static int size = 4000000;    //Размер массивов
    public static double[] sizeGrid = new double[size];  //Массив размера сетки
    public static double maxGrid = 0; //максимум сетки
    public static double minGrid = 2; //минимум сетки
    public static int pulseCount = 0; //счетчик импульсов
    public static int rollbackCount = 0;  //счетчик откатов
    public static int i = 0;  //счетчик входных данных
    public static int transactionCount = 0;   //счетчик построкнных сеток
    public static Date[] buyDataValue = new Date[size];
    public static Double[] buyMaxGrid = new Double[size];
    public static Double[] buyMinGrid = new Double[size];
    public static Integer[] buyPulseCount = new Integer[size];
    public static Integer[] buyRollbackCount = new Integer[size];
    public static boolean isFirstHigh = true;      //если первый high
    public static boolean isFirstRec = true;       //???????????
    public static boolean isReset = false;         //???????????
    public static boolean firstday = true;         //флаг первого дня(для сброса в понедельник)
    public static boolean buyka = false;             //флаг рабочей сетки
    public static boolean maxmax = false;            //флаг ожидания пробоя МО после повторения максимума
    public static int tempRec = 0;
    public static double recLow = 0;
    public static double bufMaxGrid = 0;
    public static double bufMinGrid = 0;
    public static int recNumber = 0;
    public static int m2 = 0;   //количество свечей
    public static int impCount = 0;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm z");
    public static int countDeal = 50000;
    public static int step[] = new int[countDeal];

    public static DataLoading d = new DataLoading();

    public static void process() {

        for (i = 0; i < d.maxI; i++) {
//            System.out.println(i);
            {
//                System.out.println(dateValue[i]+" "+i);
                if (d.dateValue[i].getDay() == 2)                           //во вторник ставим ожидание понедельника
                {
                    firstday = true;
                    //System.out.println(dateValue[i]);
                }
                if (d.dateValue[i].getDay() == 1 && firstday)                //сброс в понедельник
                {
                    minGrid = d.minPrice[i];
                    maxGrid = 0;
                    pulseCount = 1;
                    rollbackCount = 0;
                    firstday = false;
                    //System.out.println(dateValue[i]);
                }
            }
            if (minGrid > d.minPrice[i]) {
                newMin();
            } else {
                if (isFirstHigh) {
                    maxGrid = d.maxPrice[i];
                    pulseCount++;
                    isFirstHigh = false;
                } else {
                    if (maxGrid < d.maxPrice[i]) {
                        newMax();
                    } else if (maxGrid == d.maxPrice[i]) {
                        if (rollbackCount <= 1) {
                            newMax();
                        } else {
                            rollbackCount++;
                            maxmax = true;
                            tempRec = rollbackCount;
                        }
                        if (pulseCount - rollbackCount == 0) {
                            buy();
                            if (buyka == true) {
                                buyopen();
                                // System.out.println("buyopen");
                            }
                            reset();
                        }
                    } else {
                        rollbackCount++;
                        if (isFirstRec) {
                            recLow = d.minPrice[i];
                            isFirstRec = false;
                            recNumber = 1;
                        } else {
                            if (recLow > d.minPrice[i]) {
                                // recLow = minPrice[i];
                                recNumber = rollbackCount;
                            }
                            if (maxmax == true) {
                                pulseCount = pulseCount + tempRec;
                                rollbackCount = rollbackCount - tempRec;
                                maxmax = false;
                            }
                            if (rollbackCount - pulseCount == 0) {
                                buy();
                                if (buyka == true) {
                                    buyopen();
                                    // System.out.println("buyopen");
                                }
                                reset();
                            }
                        }
                    }
                }
            }
            processing();

            // i++;
//            if ((dateValue[i].getMinutes() % 2) != 0
//            && (dateValue[i].getHours()) > 2
//            && (dateValue[i].getHours()) < 22){
//            System.out.println(dateValue[i]);
//            System.out.println(m2);}
        }
    }

    public static void newMax() {
        maxGrid = d.maxPrice[i];
        pulseCount++;
        pulseCount = pulseCount + rollbackCount;
        isFirstRec = true;
        rollbackCount = 0;
        maxmax = false;
    }

    public static void newMin() {
        minGrid = d.minPrice[i];
        isFirstHigh = true;
        isFirstRec = true;
        //  System.out.println(minPrice[i]);
        pulseCount = 1;
        rollbackCount = 0;
        // maxGrid = maxPrice[i];
    }

    public static void reset() {
        bufMinGrid = minGrid;
        bufMaxGrid = maxGrid;
//        System.out.println(dateValue[i - rollbackCount-1]);
//        System.out.println(dateValue[i - pulseCount - rollbackCount]);
//        System.out.println(bufMinGrid);
//        System.out.println(bufMaxGrid);
        minGrid = recLow;
        maxGrid = 0;
        impCount = rollbackCount + recNumber - 1;
        pulseCount = 1;
        i = i - rollbackCount;
        rollbackCount = 0;
    }

    public static void buy() {
        buyDataValue[transactionCount] = d.dateValue[i];
        buyMaxGrid[transactionCount] = maxGrid;
        buyMinGrid[transactionCount] = minGrid;
        sizeGrid[transactionCount] = (maxGrid - minGrid) * 100000;
        buyPulseCount[transactionCount] = pulseCount;
        buyRollbackCount[transactionCount] = rollbackCount;
        buyka = false;
//        System.out.println(i);
//        System.out.println(dateFormat.format(buyDataValue[transactionCount]) + " " +
//                buyMinGrid[transactionCount] + " " +
//                buyMaxGrid[transactionCount] + " " +
//                buyPulseCount[transactionCount] + " " +
//                buyRollbackCount[transactionCount] + " " +
//                transactionCount + " " +
//                String.format("%.0f", sizeGrid[transactionCount]) + " " +
//                step[transactionCount] + " " +
//                ((buyMaxGrid[transactionCount] - buyMinGrid[transactionCount]) * 0.382 + buyMinGrid[transactionCount]) + " " +
//                recLow);
        if (sizeGrid[transactionCount] >= 300
//                && sizeGrid[transactionCount] <= 600
//                && buyPulseCount[transactionCount] >= 2
//                && buyPulseCount[transactionCount] <= 90
//                && ((d.dateValue[i].getHours() <= 17))
//                && ((d.dateValue[i - rollbackCount].getHours() >= 7))
//                && ((d.dateValue[i].getHours() >= 7 - (rollbackCount / 30)))
//                && ((d.dateValue[i - rollbackCount].getHours() <= 17))
        ) {
            buyka = true;
        }
    }

    public static void buyopen() {
        step[transactionCount] = 1;
        if ((buyMaxGrid[transactionCount] - buyMinGrid[transactionCount]) * 0.382 + buyMinGrid[transactionCount] > d.minPrice[i]) {
            step[transactionCount] = 6;
        }
//        System.out.println(dateFormat.format(buyDataValue[transactionCount]) + " " +
//                buyMinGrid[transactionCount] + " " +
//                buyMaxGrid[transactionCount] + " " +
//                buyPulseCount[transactionCount] + " " +
//                buyRollbackCount[transactionCount] + " " +
//                transactionCount + " " +
//                String.format("%.0f", sizeGrid[transactionCount]) + " " +
//                step[transactionCount] + " " +
//                ((buyMaxGrid[transactionCount] - buyMinGrid[transactionCount]) * 0.382 + buyMinGrid[transactionCount]) + " " +
//                i);
        transactionCount++;
    }
}
