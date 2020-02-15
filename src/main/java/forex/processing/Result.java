package forex.processing;

import java.util.ArrayList;
import java.util.List;

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
    private List<Grid> workGrid = new ArrayList<>();

    public void addWorkGrid(Grid grid) {
        workGrid.add(grid);
    }

    public void processing(int i) {
        this.i = i;
        currentTransaction = 0;
        for (Grid grid : workGrid) {
            switch (grid.getStep()) {
                case 1:
                    step1(grid);
                    break;
                case 2:
                    step2(grid);
                    break;
                case 3:
                    step3(grid);
                    break;
                case 4:
                    step4(grid);
                    break;
                case 5:
                    step5(grid);
                    break;
                case 6:
                    step6(grid);
                    break;
                case 7:
                    step7(grid);
                    break;
                case 8:
                    step8(grid);
                    break;
                case 9:
                    step9(grid);
                    break;
                case 10:
                    step10(grid);
                    break;
            }
            currentTransaction++;
        }
    }

    private void step1(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() >
                gridGeneration.getPriceList().get(i).getMinPrice()) {
            MOFibo38[currentTransaction] = 1;
            grid.setStep(6);
            printStep();
        } else {
            if (grid.getBuyMaxGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
                classic[currentTransaction] = 1;
                grid.setStep(2);
                printStep();
                classicOpen++;
            }
        }
    }

    private void step2(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * fibonacci1618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            systemClassicPoint += grid.getSizeGrid() * (fibonacci1618 - 1.0) - spreadFull;
            classicClose++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice()) {
            MPFibo61[currentTransaction] = 1;
            grid.setStep(3);
            printStep();
        }
    }

    private void step3(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            system1Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system2Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            systemClassicPoint += grid.getSizeGrid() * (1.618 - 1.0) - spreadFull;
            profitableDeals++;
            classicClose++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice()) {
            MPFibo38[currentTransaction] = 1;
            unprofitableDeals++;
            grid.setStep(4);
            printStep();
            system1Point = system1Point - grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) - spreadFull;
        }
    }


    private void step4(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            systemClassicPoint += grid.getSizeGrid() * (1.618 - 1.0) - spreadFull;
            system2Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system3Point += grid.getSizeGrid() * (1.618 - 0.382) - spreadFull;
            system7Point += grid.getSizeGrid() * (1.618 - 0.382) - spreadFull;
            pips[currentTransaction] = grid.getSizeGrid() * (fibonacci1618 - fibonacci0382) - spreadFull;
            classicClose++;
        } else if (grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice()) {
            SLclassic[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(5);
            printStep();
            system2Point = system2Point - grid.getSizeGrid() * (fibonacci0618 - 0) - spreadFull;
            system7Point = system7Point - grid.getSizeGrid() * (fibonacci0382 - 0) - spreadFull;
            pips[currentTransaction] = -(grid.getSizeGrid() * (fibonacci0382 - 0) + spreadFull);
        }
    }

    private void step5(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            grid.setStep(15);
            printStep();
            system3Point = system3Point + grid.getSizeGrid() * (fibonacci1000 - fibonacci0382) - spreadFull;
            classicClose++;
        } else if (grid.getBuyMinGrid() + 0.02000 > gridGeneration.getPriceList().get(i).getMinPrice()) {
            systemClassicPoint = systemClassicPoint - grid.getSizeGrid() * (1) - 2000 - spreadFull;
            system3Point = system3Point - grid.getSizeGrid() * (fibonacci0382 - 0) - 2000 - spreadFull;
            classicClose++;
            grid.setStep(15);
            printStep();
            system4count++;
        }
    }

    private void step6(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            ACopen[currentTransaction] = 1;
            grid.setStep(7);
            printStep();
        } else if (grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice()) {
            system6Point = system6Point - grid.getSizeGrid() * (fibonacci0382 - 0) - spreadFull;
            SLFibo0[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
        }
    }

    private void step7(Grid grid) {
        if (grid.getBuyMaxGrid() < gridGeneration.getPriceList().get(i).getMaxPrice()) {
            ACclassic[currentTransaction] = 1;
            grid.setStep(8);
            printStep();
            classicOpen++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice() - spread) {
            system4Point = system4Point - grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) - spreadFull;
            SLFibo38[currentTransaction] = 1;
            unprofitableDeals++;
            end[currentTransaction] = 1;
            grid.setStep(9);
            printStep();
        }
    }

    private void step8(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            TPAC[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            system4Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system5Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system6Point += grid.getSizeGrid() * (1.618 - 0.382) - spreadFull;
            profitableDeals++;
            classicClose++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice() - spread) {
            system6Point = system6Point + grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) - spreadFull;
            BUACFibo61[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(10);
            printStep();
        }
    }

    private void step9(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            system5Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
        } else if (grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice()) {
            system5Point = system5Point - grid.getSizeGrid() * (fibonacci0618 - 0) - spreadFull;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
        }
    }

    private void step10(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < gridGeneration.getPriceList().get(i).getMaxPrice() - spread) {
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            classicClose++;
        } else if (grid.getBuyMinGrid() > gridGeneration.getPriceList().get(i).getMinPrice()) {
            systemClassicPoint = systemClassicPoint - grid.getSizeGrid() * (fibonacci1618 - fibonacci1000) - spreadFull;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            classicClose++;
        }
    }

    private void printStep() {
        // System.out.println("transactionCount="+currentTransaction+" step=" + gridGeneration.step[currentTransaction]);
    }
}
