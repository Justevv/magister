package forex.processing;

import forex.load.DataLoading;

import java.util.ArrayList;

public class Result {
    public static GridGeneration g = new GridGeneration();
    public static DataLoading d = new DataLoading();
    public static int tempTransactionCount = 0;
    //   public static int transactionCount=0;
    public static int countDeal = 50000;
    //public static int step[] = new int[countDeal];
    public static double fibo38[] = new double[countDeal];
    public static double fibo61[] = new double[countDeal];
    public static double fibo161[] = new double[countDeal];
    public static int cancell[] = new int[countDeal];
    public static int end[] = new int[countDeal];
    public static int classic[] = new int[countDeal];
    public static int MOFibo38[] = new int[countDeal];
    public static int MPFibo38[] = new int[countDeal];
    public static int MPFibo61[] = new int[countDeal];
    public static int SLclassic[] = new int[countDeal];
    public static int ACopen[] = new int[countDeal];
    public static int SLFibo0[] = new int[countDeal];
    public static int SLFibo38[] = new int[countDeal];
    public static int ACclassic[] = new int[countDeal];
    public static int TPAC[] = new int[countDeal];
    public static int BUACFibo61[] = new int[countDeal];
    public static int SL[] = new int[countDeal];
    public static int TP[] = new int[countDeal];
    public static ArrayList<Integer> TakeProfit = new ArrayList<Integer>();
    public static double system1Point = 0;
    public static double system2Point = 0;
    public static double system3Point = 0;
    public static double system4Point = 0;
    public static double system5Point = 0;
    public static double system6Point = 0;
    public static double system7Point = 0;
    public static double system8Point = 0;
    public static double system9Point = 0;
    public static double systemClassicPoint = 0;
    public static int system4count = 0;
    public static int classicOpen = 0;
    public static int classicClose = 0;
    public static int unprofitableDeals = 0;
    public static int profitableDeals = 0;
    public static double f100 = 0;
    public static double spread = 0.00020;
    public static double spreadFull = 20;
    public static double pips[] = new double[countDeal];

    public static void processing() {
        tempTransactionCount = g.transactionCount;
        g.transactionCount = 0;
        while (g.transactionCount <= tempTransactionCount) {
            switch (g.step[g.transactionCount]) {//case 0: step0();break;//вызов функции
                case 1:
                    step1();
                    break;//вызов функции
                case 2:
                    step2();
                    break;//вызов функции
                case 3:
                    step3();
                    break;//вызов функции
                case 4:
                    step4();
                    break; //вызов функции
                case 5:
                    step5();
                    break;//вызов функции
                case 6:
                    step6();
                    break; //вызов функции
                case 7:
                    step7();
                    break; //вызов функции
                case 8:
                    step8();
                    break; //вызов функции
                case 9:
                    step9();
                    break; //вызов функции
                case 10:
                    step10();
                    break; //вызов функции
//                case 15:
//                    0;
//                    break;
                //default: Print(step[i],"Более десяти пунктов",i);

            }
            g.transactionCount++;
        }
        g.transactionCount = tempTransactionCount;
        // int[] combinedIntArray = ArrayUtils.addAll(MOFibo38, MPFibo38);

    }

    public static void step1() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 0.382 + g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {

            MOFibo38[g.transactionCount] = 1;
            g.step[g.transactionCount] = 6;
            printStep();
//            System.out.println(buyMaxGrid[transactionCount]);
//            System.out.println(buyMinGrid[transactionCount]);
//            System.out.println(minGrid);
        } else {
            if (g.buyMaxGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
//                System.out.println(g.minGrid);
//                System.out.println(d.minPrice[g.i]);
//                System.out.println(d.maxPrice[g.i]);
                classic[g.transactionCount] = 1;
                g.step[g.transactionCount] = 2;
                printStep();
                classicOpen++;
//                System.out.println(buyMaxGrid[transactionCount]);
//                System.out.println(buyMinGrid[transactionCount]);
//                System.out.println(minGrid);
            } else {
                g.step[g.transactionCount] = 1;
            }
        }
    }

    public static void step2() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
          TP[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
            systemClassicPoint = systemClassicPoint + g.sizeGrid[g.transactionCount] * (1.618 - 1.0)-spreadFull;
            classicClose++;
        } else if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 0.618 + g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {
            MPFibo61[g.transactionCount] = 1;
            g.step[g.transactionCount] = 3;
            printStep();
        } else {
            g.step[g.transactionCount] = 2;
        }
    }

    public static void step3() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
//          TP[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
            system1Point = system1Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.618)-spreadFull;
            system2Point = system2Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.618)-spreadFull;
            systemClassicPoint = systemClassicPoint + g.sizeGrid[g.transactionCount] * (1.618 - 1.0)-spreadFull;
            profitableDeals++;
            classicClose++;
        } else if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 0.382 + g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {
            MPFibo38[g.transactionCount] = 1;
            unprofitableDeals++;
            g.step[g.transactionCount] = 4;
            printStep();
            system1Point = system1Point - g.sizeGrid[g.transactionCount] * (0.618 - 0.382)-spreadFull;
        } else {
            g.step[g.transactionCount] = 3;
        }
    }


    public static void step4() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
            TP[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
            systemClassicPoint = systemClassicPoint + g.sizeGrid[g.transactionCount] * (1.618 - 1.0)-spreadFull;
            system2Point = system2Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.618)-spreadFull;
            system3Point = system3Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.382)-spreadFull;
            system7Point = system7Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.382)-spreadFull;
            pips [g.transactionCount]=g.sizeGrid[g.transactionCount] * (1.618 - 0.382)-spreadFull;
            classicClose++;
            //  system8Point++;
        } else if (g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {
            SLclassic[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 5;
            printStep();
            system2Point = system2Point - g.sizeGrid[g.transactionCount] * (0.618 - 0.0)-spreadFull;
            system7Point = system7Point - g.sizeGrid[g.transactionCount] * (0.382 - 0.0)-spreadFull;
            pips [g.transactionCount]=-(g.sizeGrid[g.transactionCount] * (0.382 - 0.0)+spreadFull);
        } else {
            g.step[g.transactionCount] = 4;
        }
    }

    public static void step5() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
            g.step[g.transactionCount] = 15;
            printStep();

            system3Point = system3Point + g.sizeGrid[g.transactionCount] * (1.0 - 0.382)-spreadFull;
            classicClose++;
        } else if (g.buyMinGrid[g.transactionCount] + 0.02000 > d.minPrice[g.i]) {
            systemClassicPoint = systemClassicPoint - g.sizeGrid[g.transactionCount] * (1) - 2000-spreadFull;
            system3Point = system3Point - g.sizeGrid[g.transactionCount] * (0.382 - 0.0) - 2000-spreadFull;
            classicClose++;
            //system3Point=SL
            // SLFibo0[g.transactionCount] = 1;
            // end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
            system4count++;
        } else {
            g.step[g.transactionCount] = 6;
        }
    }

    public static void step6() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 0.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
            ACopen[g.transactionCount] = 1;
            //OrderModify 3
            g.step[g.transactionCount] = 7;
            printStep();
        } else if (g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {
            // system3Point = system3Point - (g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * (0.382);
            //system3Point=SL
            system6Point = system6Point - g.sizeGrid[g.transactionCount] * (0.382 - 0.0)-spreadFull;
            SLFibo0[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
        } else {
            g.step[g.transactionCount] = 6;
        }
    }

    public static void step7() {
        if (g.buyMaxGrid[g.transactionCount] < d.maxPrice[g.i]) {
            ACclassic[g.transactionCount] = 1;
            g.step[g.transactionCount] = 8;
            printStep();
            classicOpen++;
        } else if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 0.382 + g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]-spread) {
            // system1Point = system1Point - (g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * (0.618 - 0.382);
            system4Point = system4Point - g.sizeGrid[g.transactionCount] * (0.618 - 0.382)-spreadFull;
            SLFibo38[g.transactionCount] = 1;
            unprofitableDeals++;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 9;
            printStep();
        } else {
            g.step[g.transactionCount] = 7;
        }
    }

    public static void step8() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
            TPAC[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
           // TakeProfit.set(1,2);
            printStep();
            system4Point = system4Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.618)-spreadFull;
            system5Point = system5Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.618)-spreadFull;
            system6Point = system6Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.382)-spreadFull;
//            System.out.println(g.transactionCount);
//            System.out.println((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 100000);
            profitableDeals++;
            classicClose++;
        } else if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 0.618 + g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]-spread) {
            system6Point = system6Point + g.sizeGrid[g.transactionCount] * (0.618 - 0.382)-spreadFull;
            BUACFibo61[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 10;
            printStep();
        } else {
            g.step[g.transactionCount] = 8;
        }
    }

    public static void step9() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
            //TPAC[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
            //TakeProfit.insert(1);
            //  classicClose++;
            system5Point = system5Point + g.sizeGrid[g.transactionCount] * (1.618 - 0.618)-spreadFull;
        } else if (g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {
            system5Point = system5Point - g.sizeGrid[g.transactionCount] * (0.618 - 0.0)-spreadFull;
// SLFibo0[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
        }
    }

    public static void step10() {
        if ((g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]) * 1.618 + g.buyMinGrid[g.transactionCount] < d.maxPrice[g.i]-spread) {
//            System.out.println(g.minGrid);
//            System.out.println(d.maxPrice[g.i]);
            //TPAC[g.transactionCount] = 1;
            end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
           // TakeProfit.insert(1);
            classicClose++;
        } else if (g.buyMinGrid[g.transactionCount] > d.minPrice[g.i]) {
           systemClassicPoint = systemClassicPoint - g.sizeGrid[g.transactionCount] * (1.618 - 1.0)-spreadFull;
                 end[g.transactionCount] = 1;
            g.step[g.transactionCount] = 15;
            printStep();
            classicClose++;

        }
    }

    public static void f1000() {
        f100 = (g.buyMaxGrid[g.transactionCount] - g.buyMinGrid[g.transactionCount]);
    }

    public static void printStep() {
        // System.out.println("transactionCount="+g.transactionCount+" step=" + g.step[g.transactionCount]);
    }
}
