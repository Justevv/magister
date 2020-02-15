package forex.processing;

public class Result {
    private GridGeneration gridGeneration;

    public void setGridGeneration(GridGeneration gridGeneration) {
        this.gridGeneration = gridGeneration;
    }

    public static int countDeal = 50000;
    private float fibo38[] = new float[countDeal];
    private float fibo61[] = new float[countDeal];
    private float fibo161[] = new float[countDeal];
    private int cancell[] = new int[countDeal];
    private int end[] = new int[countDeal];
    private int classic[] = new int[countDeal];
    private int MOFibo38[] = new int[countDeal];
    private int MPFibo38[] = new int[countDeal];
    private int MPFibo61[] = new int[countDeal];
    private int SLclassic[] = new int[countDeal];
    private int ACopen[] = new int[countDeal];
    private int SLFibo0[] = new int[countDeal];
    private int SLFibo38[] = new int[countDeal];
    private int ACclassic[] = new int[countDeal];
    private int TPAC[] = new int[countDeal];
    private int BUACFibo61[] = new int[countDeal];
    private int SL[] = new int[countDeal];
    private int TP[] = new int[countDeal];
    public float system1Point = 0;
    public float system2Point = 0;
    public float system3Point = 0;
    public float system4Point = 0;
    public float system5Point = 0;
    public float system6Point = 0;
    public float system7Point = 0;
    public float system8Point = 0;
    public float system9Point = 0;
    public float systemClassicPoint = 0;
    private int system4count = 0;
    private int classicOpen = 0;
    private int classicClose = 0;
    private int unprofitableDeals = 0;
    private int profitableDeals = 0;
    private float f100 = 0;
    private float spread = 0.00020f;
    private float spreadFull = 20;
    private float pips[] = new float[countDeal];
    private float fibonacci0382 = 0.382f;
    private float fibonacci0618 = 0.618f;
    private float fibonacci1000 = 1f;
    private float fibonacci1618 = 1.618f;
    private int i = 0;
    private int currentTransaction;

    public void processing(int i) {
        this.i = i;
        currentTransaction = 0;
        while (gridGeneration.transactionCount >= currentTransaction) {
            switch (gridGeneration.step[currentTransaction]) {
                case 1:
                    step1();
                    break;
                case 2:
                    step2();
                    break;
                case 3:
                    step3();
                    break;
                case 4:
                    step4();
                    break;
                case 5:
                    step5();
                    break;
                case 6:
                    step6();
                    break;
                case 7:
                    step7();
                    break;
                case 8:
                    step8();
                    break;
                case 9:
                    step9();
                    break;
                case 10:
                    step10();
                    break;
            }
            currentTransaction++;
        }
    }

    private void step1() {
        if ((gridGeneration.buyMaxGrid[currentTransaction]
                - gridGeneration.buyMinGrid[currentTransaction]) * 0.382
                + gridGeneration.buyMinGrid[currentTransaction] >
                gridGeneration.getPriceList().get(i).getMinPrice()) {
            MOFibo38[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 6;
            printStep();
//            System.out.println(buyMaxGrid[transactionCount]);
//            System.out.println(buyMinGrid[transactionCount]);
//            System.out.println(minGrid);
        } else {
            if (gridGeneration.buyMaxGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
//                System.out.println(gridGeneration.minGrid);
//                System.out.println(gridGeneration.getPriceM2List().get(gridGeneration.i).getMinPrice());
//                System.out.println(gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice());
                classic[currentTransaction] = 1;
                gridGeneration.step[currentTransaction] = 2;
                printStep();
                classicOpen++;
//                System.out.println(buyMaxGrid[transactionCount]);
//                System.out.println(buyMinGrid[transactionCount]);
//                System.out.println(minGrid);
            } else {
                gridGeneration.step[currentTransaction] = 1;
            }
        }
    }

    private void step2() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * fibonacci1618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            systemClassicPoint += gridGeneration.sizeGrid[currentTransaction] * (fibonacci1618 - 1.0) - spreadFull;
            classicClose++;
        } else if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 0.618 + gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice()) {
            MPFibo61[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 3;
            printStep();
        } else {
            gridGeneration.step[currentTransaction] = 2;
        }
    }

    private void step3() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 1.618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
//          TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            system1Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.618) - spreadFull;
            system2Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.618) - spreadFull;
            systemClassicPoint += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 1.0) - spreadFull;
            profitableDeals++;
            classicClose++;
        } else if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 0.382 + gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice()) {
            MPFibo38[currentTransaction] = 1;
            unprofitableDeals++;
            gridGeneration.step[currentTransaction] = 4;
            printStep();
            system1Point = system1Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0618 - fibonacci0382) - spreadFull;
        } else {
            gridGeneration.step[currentTransaction] = 3;
        }
    }


    private void step4() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 1.618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            systemClassicPoint += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 1.0) - spreadFull;
            system2Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.618) - spreadFull;
            system3Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.382) - spreadFull;
            system7Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.382) - spreadFull;
            pips[currentTransaction] = gridGeneration.sizeGrid[currentTransaction] * (fibonacci1618 - fibonacci0382) - spreadFull;
            classicClose++;
            //  system8Point++;
        } else if (gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice()) {
            SLclassic[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 5;
            printStep();
            system2Point = system2Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0618 - 0) - spreadFull;
            system7Point = system7Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0382 - 0) - spreadFull;
            pips[currentTransaction] = -(gridGeneration.sizeGrid[currentTransaction] * (fibonacci0382 - 0) + spreadFull);
        } else {
            gridGeneration.step[currentTransaction] = 4;
        }
    }

    private void step5() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 1 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            gridGeneration.step[currentTransaction] = 15;
            printStep();

            system3Point = system3Point + gridGeneration.sizeGrid[currentTransaction] * (fibonacci1000 - fibonacci0382) - spreadFull;
            classicClose++;
        } else if (gridGeneration.buyMinGrid[currentTransaction] + 0.02000 > gridGeneration.getPriceList().get(i).getMinPrice()) {
            systemClassicPoint = systemClassicPoint - gridGeneration.sizeGrid[currentTransaction] * (1) - 2000 - spreadFull;
            system3Point = system3Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0382 - 0) - 2000 - spreadFull;
            classicClose++;
            //system3Point=SL
            // SLFibo0[currentTransaction] = 1;
            // end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            system4count++;
        } else {
            gridGeneration.step[currentTransaction] = 6;
        }
    }

    private void step6() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 0.618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            ACopen[currentTransaction] = 1;
            //OrderModify 3
            gridGeneration.step[currentTransaction] = 7;
            printStep();
        } else if (gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice()) {
            // system3Point = system3Point - (gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * (0.382);
            //system3Point=SL
            system6Point = system6Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0382 - 0) - spreadFull;
            SLFibo0[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
        } else {
            gridGeneration.step[currentTransaction] = 6;
        }
    }

    private void step7() {
        if (gridGeneration.buyMaxGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice()) {
            ACclassic[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 8;
            printStep();
            classicOpen++;
        } else if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 0.382 + gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice() - spread) {
            // system1Point = system1Point - (gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * (0.618 - 0.382);
            system4Point = system4Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0618 - fibonacci0382) - spreadFull;
            SLFibo38[currentTransaction] = 1;
            unprofitableDeals++;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 9;
            printStep();
        } else {
            gridGeneration.step[currentTransaction] = 7;
        }
    }

    private void step8() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 1.618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            TPAC[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            // TakeProfit.set(1,2);
            printStep();
            system4Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.618) - spreadFull;
            system5Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.618) - spreadFull;
            system6Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.382) - spreadFull;
//            System.out.println(currentTransaction);
//            System.out.println((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 100000);
            profitableDeals++;
            classicClose++;
        } else if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 0.618 + gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice() - spread) {
            system6Point = system6Point + gridGeneration.sizeGrid[currentTransaction] * (fibonacci0618 - fibonacci0382) - spreadFull;
            BUACFibo61[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 10;
            printStep();
        } else {
            gridGeneration.step[currentTransaction] = 8;
        }
    }

    private void step9() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 1.618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            //TPAC[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            //TakeProfit.insert(1);
            //  classicClose++;
            system5Point += gridGeneration.sizeGrid[currentTransaction] * (1.618 - 0.618) - spreadFull;
        } else if (gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice()) {
            system5Point = system5Point - gridGeneration.sizeGrid[currentTransaction] * (fibonacci0618 - 0) - spreadFull;
// SLFibo0[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
        }
    }

    private void step10() {
        if ((gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]) * 1.618 + gridGeneration.buyMinGrid[currentTransaction] < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
//            System.out.println(gridGeneration.minGrid);
//            System.out.println(gridGeneration.getPriceM2List().get(gridGeneration.i).getMaxPrice());
            //TPAC[currentTransaction] = 1;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            // TakeProfit.insert(1);
            classicClose++;
        } else if (gridGeneration.buyMinGrid[currentTransaction] > gridGeneration.getPriceList().get(i).getMinPrice()) {
            systemClassicPoint = systemClassicPoint - gridGeneration.sizeGrid[currentTransaction] * (fibonacci1618 - fibonacci1000) - spreadFull;
            end[currentTransaction] = 1;
            gridGeneration.step[currentTransaction] = 15;
            printStep();
            classicClose++;

        }
    }

    private void f1000() {
        f100 = (gridGeneration.buyMaxGrid[currentTransaction] - gridGeneration.buyMinGrid[currentTransaction]);
    }

    private void printStep() {
        // System.out.println("transactionCount="+currentTransaction+" step=" + gridGeneration.step[currentTransaction]);
    }
}
