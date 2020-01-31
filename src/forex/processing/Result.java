package forex.processing;

import java.util.ArrayList;

public class Result {
    private GridGeneration gridGeneration;

    public void setGridGeneration(GridGeneration gridGeneration) {
        this.gridGeneration = gridGeneration;
    }

    public static int tempTransactionCount = 0;
    //   public static int transactionCount=0;
    public static int countDeal = 50000;
    //public static int step[] = new int[countDeal];
    public double fibo38[] = new double[countDeal];
    public double fibo61[] = new double[countDeal];
    public double fibo161[] = new double[countDeal];
    public int cancell[] = new int[countDeal];
    public int end[] = new int[countDeal];
    public int classic[] = new int[countDeal];
    public int MOFibo38[] = new int[countDeal];
    public int MPFibo38[] = new int[countDeal];
    public int MPFibo61[] = new int[countDeal];
    public int SLclassic[] = new int[countDeal];
    public int ACopen[] = new int[countDeal];
    public int SLFibo0[] = new int[countDeal];
    public int SLFibo38[] = new int[countDeal];
    public int ACclassic[] = new int[countDeal];
    public int TPAC[] = new int[countDeal];
    public int BUACFibo61[] = new int[countDeal];
    public int SL[] = new int[countDeal];
    public int TP[] = new int[countDeal];
    public ArrayList<Integer> TakeProfit = new ArrayList<Integer>();
    public double system1Point = 0;
    public double system2Point = 0;
    public double system3Point = 0;
    public double system4Point = 0;
    public double system5Point = 0;
    public double system6Point = 0;
    public double system7Point = 0;
    public double system8Point = 0;
    public double system9Point = 0;
    public double systemClassicPoint = 0;
    public int system4count = 0;
    public int classicOpen = 0;
    public int classicClose = 0;
    public int unprofitableDeals = 0;
    public int profitableDeals = 0;
    public double f100 = 0;
    public double spread = 0.00020;
    public double spreadFull = 20;
    public double pips[] = new double[countDeal];

    public void processing() {
        tempTransactionCount = gridGeneration.transactionCount;
        gridGeneration.transactionCount = 0;
        while (gridGeneration.transactionCount <= tempTransactionCount) {
            switch (gridGeneration.step[gridGeneration.transactionCount]) {//case 0: step0();break;//вызов функции
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
            gridGeneration.transactionCount++;
        }
        gridGeneration.transactionCount = tempTransactionCount;
        // int[] combinedIntArray = ArrayUtils.addAll(MOFibo38, MPFibo38);

    }

    public void step1() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount]
                - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 0.382
                + gridGeneration.buyMinGrid[gridGeneration.transactionCount] >
                gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {

            MOFibo38[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 6;
            printStep();
//            System.out.println(buyMaxGrid[transactionCount]);
//            System.out.println(buyMinGrid[transactionCount]);
//            System.out.println(minGrid);
        } else {
            if (gridGeneration.buyMaxGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
//                System.out.println(gridGeneration.minGrid);
//                System.out.println(gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice());
//                System.out.println(gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice());
                classic[gridGeneration.transactionCount] = 1;
                gridGeneration.step[gridGeneration.transactionCount] = 2;
                printStep();
                classicOpen++;
//                System.out.println(buyMaxGrid[transactionCount]);
//                System.out.println(buyMinGrid[transactionCount]);
//                System.out.println(minGrid);
            } else {
                gridGeneration.step[gridGeneration.transactionCount] = 1;
            }
        }
    }

    public void step2() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
            TP[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            systemClassicPoint += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 1.0) - spreadFull;
            classicClose++;
        } else if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 0.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            MPFibo61[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 3;
            printStep();
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 2;
        }
    }

    public void step3() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
//          TP[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            system1Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.618) - spreadFull;
            system2Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.618) - spreadFull;
            systemClassicPoint += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 1.0) - spreadFull;
            profitableDeals++;
            classicClose++;
        } else if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 0.382 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            MPFibo38[gridGeneration.transactionCount] = 1;
            unprofitableDeals++;
            gridGeneration.step[gridGeneration.transactionCount] = 4;
            printStep();
            system1Point = system1Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.618 - 0.382) - spreadFull;
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 3;
        }
    }


    public void step4() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
            TP[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            systemClassicPoint = systemClassicPoint + gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 1.0) - spreadFull;
            system2Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.618) - spreadFull;
            system3Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.382) - spreadFull;
            system7Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.382) - spreadFull;
            pips[gridGeneration.transactionCount] = gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.382) - spreadFull;
            classicClose++;
            //  system8Point++;
        } else if (gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            SLclassic[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 5;
            printStep();
            system2Point = system2Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.618 - 0.0) - spreadFull;
            system7Point = system7Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.382 - 0.0) - spreadFull;
            pips[gridGeneration.transactionCount] = -(gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.382 - 0.0) + spreadFull);
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 4;
        }
    }

    public void step5() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();

            system3Point = system3Point + gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.0 - 0.382) - spreadFull;
            classicClose++;
        } else if (gridGeneration.buyMinGrid[gridGeneration.transactionCount] + 0.02000 > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            systemClassicPoint = systemClassicPoint - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1) - 2000 - spreadFull;
            system3Point = system3Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.382 - 0.0) - 2000 - spreadFull;
            classicClose++;
            //system3Point=SL
            // SLFibo0[gridGeneration.transactionCount] = 1;
            // end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            system4count++;
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 6;
        }
    }

    public void step6() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 0.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
            ACopen[gridGeneration.transactionCount] = 1;
            //OrderModify 3
            gridGeneration.step[gridGeneration.transactionCount] = 7;
            printStep();
        } else if (gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            // system3Point = system3Point - (gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * (0.382);
            //system3Point=SL
            system6Point = system6Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.382 - 0.0) - spreadFull;
            SLFibo0[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 6;
        }
    }

    public void step7() {
        if (gridGeneration.buyMaxGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice()) {
            ACclassic[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 8;
            printStep();
            classicOpen++;
        } else if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 0.382 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice() - spread) {
            // system1Point = system1Point - (gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * (0.618 - 0.382);
            system4Point = system4Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.618 - 0.382) - spreadFull;
            SLFibo38[gridGeneration.transactionCount] = 1;
            unprofitableDeals++;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 9;
            printStep();
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 7;
        }
    }

    public void step8() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
            TPAC[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            // TakeProfit.set(1,2);
            printStep();
            system4Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.618) - spreadFull;
            system5Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.618) - spreadFull;
            system6Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.382) - spreadFull;
//            System.out.println(gridGeneration.transactionCount);
//            System.out.println((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 100000);
            profitableDeals++;
            classicClose++;
        } else if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 0.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice() - spread) {
            system6Point = system6Point + gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.618 - 0.382) - spreadFull;
            BUACFibo61[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 10;
            printStep();
        } else {
            gridGeneration.step[gridGeneration.transactionCount] = 8;
        }
    }

    public void step9() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
            //TPAC[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            //TakeProfit.insert(1);
            //  classicClose++;
            system5Point += gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 0.618) - spreadFull;
        } else if (gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            system5Point = system5Point - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (0.618 - 0.0) - spreadFull;
// SLFibo0[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
        }
    }

    public void step10() {
        if ((gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]) * 1.618 + gridGeneration.buyMinGrid[gridGeneration.transactionCount] < gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice() - spread) {
//            System.out.println(gridGeneration.minGrid);
//            System.out.println(gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice());
            //TPAC[gridGeneration.transactionCount] = 1;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            // TakeProfit.insert(1);
            classicClose++;
        } else if (gridGeneration.buyMinGrid[gridGeneration.transactionCount] > gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice()) {
            systemClassicPoint = systemClassicPoint - gridGeneration.sizeGrid[gridGeneration.transactionCount] * (1.618 - 1.0) - spreadFull;
            end[gridGeneration.transactionCount] = 1;
            gridGeneration.step[gridGeneration.transactionCount] = 15;
            printStep();
            classicClose++;

        }
    }

    public void f1000() {
        f100 = (gridGeneration.buyMaxGrid[gridGeneration.transactionCount] - gridGeneration.buyMinGrid[gridGeneration.transactionCount]);
    }

    public void printStep() {
        // System.out.println("transactionCount="+gridGeneration.transactionCount+" step=" + gridGeneration.step[gridGeneration.transactionCount]);
    }
}
