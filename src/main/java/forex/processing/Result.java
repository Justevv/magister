package forex.processing;

import forex.load.Price;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Result {
    private static final float SPREAD = 0.00020f;
    public static int countDeal = 50000;
    private int end[] = new int[countDeal];
    private int ACopen[] = new int[countDeal];
    private int SLFibo38[] = new int[countDeal];
    private int ACclassic[] = new int[countDeal];
    private int TPAC[] = new int[countDeal];
    private int BUACFibo61[] = new int[countDeal];
    private List<Float> point = Arrays.asList(0f, 0f, 0f, 0f);
    private float spreadFull = 20;
    private float fibonacci0382 = 0.382f;
    private float fibonacci0618 = 0.618f;
    private float fibonacci1000 = 1f;
    private float fibonacci1618 = 1.618f;
    private Price price;
    private List<Grid> workGrid = new ArrayList<>();
    @Getter
    private List<Grid> classicOrders = new ArrayList<>();

    public void addWorkGrid(Grid grid) {
        workGrid.add(grid);
    }

    public void processing(Price price) {
        this.price = price;
        for (int i = 0; i < workGrid.size(); i++) {
            switch (workGrid.get(i).getStep()) {
                case 1 -> calculateStart(workGrid.get(i));
                case 2 -> calculate61To100(workGrid.get(i));
                case 3 -> calculate100To61(workGrid.get(i));
                case 4 -> calculate100To61To38(workGrid.get(i));
                case 5 -> calculate100To38ToZero(workGrid.get(i));
                case 6 -> calculate61To38(workGrid.get(i), i);
                case 7 -> calculate61To38To61(workGrid.get(i), i);
                case 8 -> calculate61To38To100(workGrid.get(i), i);
                case 9 -> calculate61To38To61To38(workGrid.get(i));
                case 10 -> calculate61To38To100To61(workGrid.get(i));
                case 15 -> {
                    classicOrders.add(workGrid.get(i));
                    workGrid.remove(workGrid.get(i));
                }
            }
        }
    }

    private void calculateStart(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice()) {
            openOrder(grid, Strategy.MR38SL0ACSL1CLASSIC62);
            grid.getMaximumRollback().setLevel38(true);
            grid.setStep(6);
            printStep();
        } else if (grid.getBuyMaxGrid() < price.getMaxPrice() - SPREAD) {
            grid.setStep(2);
            printStep();
            openOrder(grid, Strategy.CLASSIC);
        }
    }

    private void openOrder(Grid grid, Strategy strategy) {
        var order = Order.builder()
                .strategy(strategy)
                .openTime(price.getDateValue())
                .build();
        grid.getOrders().add(order);
    }

    private void closeOrder(Grid grid, double profit, Strategy strategy) {
        var order = grid.getOrders().stream().filter(x -> x.getStrategy().equals(strategy)).findFirst().orElseThrow();
        order.setProfit(profit);
    }

    private double calculateProfit(Grid grid, double difference) {
        return grid.getSizeGrid() * (difference) - spreadFull;
    }

    private double calculateExpense(Grid grid, double difference) {
        return -(grid.getSizeGrid() * (difference) + spreadFull);
    }

    private void calculate61To100(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * fibonacci1618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            grid.getMaximumLevel().setLevel161(true);
            grid.setStep(15);
            printStep();
            var profit = calculateProfit(grid, fibonacci1618 - 1.0);
            closeOrder(grid, profit, Strategy.CLASSIC);
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > price.getMinPrice()) {
            openOrder(grid, Strategy.MD61SL38);
            openOrder(grid, Strategy.MD61SL0);
            grid.getMaximumDrawdown().setLevel61(true);
            grid.setStep(3);
            printStep();
        }
    }

    private void calculate100To61(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            grid.getMaximumLevel().setLevel161(true);
            grid.setStep(15);
            printStep();
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.618), Strategy.MD61SL38);
            point.set(0, point.get(0) + grid.getSizeGrid() * (fibonacci1618 - fibonacci0618) - spreadFull);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.618), Strategy.MD61SL0);
            var profit = grid.getSizeGrid() * (1.618 - 1.0) - spreadFull;
            closeOrder(grid, profit, Strategy.CLASSIC);
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice()) {
            openOrder(grid, Strategy.MD38SLO);
            openOrder(grid, Strategy.MD38SL2000);
            grid.getMaximumDrawdown().setLevel38(true);
            grid.setStep(4);
            printStep();
            closeOrder(grid, calculateExpense(grid, fibonacci0618 - fibonacci0382), Strategy.MD61SL38);
            point.set(0, point.get(0) - grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) - spreadFull);
        }
    }

    private void calculate100To61To38(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            grid.getMaximumLevel().setLevel161(true);
            grid.setStep(15);
            printStep();
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.618), Strategy.MD61SL0);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.382), Strategy.MD38SL2000);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.382), Strategy.MD38SLO);
            var profit = calculateProfit(grid, 1.618 - 1.0);
            closeOrder(grid, profit, Strategy.CLASSIC);
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            grid.getMaximumDrawdown().setLevel0(true);
            grid.setStep(5);
            printStep();
            closeOrder(grid, calculateExpense(grid, fibonacci0618 - 0), Strategy.MD61SL0);
            closeOrder(grid, calculateExpense(grid, fibonacci0382 - 0), Strategy.MD38SLO);
        }
    }

    private void calculate100To38ToZero(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            grid.setStep(15);
            printStep();
            closeOrder(grid, calculateProfit(grid, fibonacci1000 - fibonacci0382), Strategy.MD38SL2000);
            closeOrder(grid, 0, Strategy.CLASSIC);
        } else if (grid.getBuyMinGrid() + 0.02000 > price.getMinPrice()) {
            grid.getMaximumDrawdown().setLevelMinus2000(true);
            var profit = calculateExpense(grid, 1) - 2000;
            closeOrder(grid, calculateExpense(grid, fibonacci0382 - 0) - 2000, Strategy.MD38SL2000);
            closeOrder(grid, profit, Strategy.CLASSIC);
            grid.setStep(15);
            printStep();
        }
    }

    private void calculate61To38(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            openOrder(grid, Strategy.ACSL0CLASSIC1);
            openOrder(grid, Strategy.ACSL38CLASSIC1);
            ACopen[currentTransaction] = 1;
            grid.setStep(7);
            printStep();
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            closeOrder(grid, calculateExpense(grid, fibonacci0382 - 0), Strategy.MR38SL0ACSL1CLASSIC62);
            grid.getMaximumRollback().setLevel0(true);
            grid.setStep(15);
            printStep();
        }
    }

    private void calculate61To38To61(Grid grid, int currentTransaction) {
        if (grid.getBuyMaxGrid() < price.getMaxPrice()) {
            ACclassic[currentTransaction] = 1;
            grid.setStep(8);
            printStep();
            openOrder(grid, Strategy.CLASSIC);
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice() - SPREAD) {
            closeOrder(grid, calculateExpense(grid, fibonacci0618 - fibonacci0382), Strategy.ACSL38CLASSIC1);
            SLFibo38[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(9);
            printStep();
        }
    }

    private void calculate61To38To100(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            TPAC[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            closeOrder(grid, calculateProfit(grid, fibonacci1618 - fibonacci0618), Strategy.ACSL38CLASSIC1);
            closeOrder(grid, calculateProfit(grid, fibonacci1618 - fibonacci0618), Strategy.ACSL0CLASSIC1);
            closeOrder(grid, calculateProfit(grid, fibonacci1618 - fibonacci0382), Strategy.MR38SL0ACSL1CLASSIC62);
            closeOrder(grid, 0, Strategy.CLASSIC);
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > price.getMinPrice() - SPREAD) {
            closeOrder(grid, calculateProfit(grid, fibonacci0618 - fibonacci0382), Strategy.MR38SL0ACSL1CLASSIC62);
            BUACFibo61[currentTransaction] = 1;
            grid.setStep(10);
            printStep();
        }
    }

    private void calculate61To38To61To38(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            grid.setStep(15);
            printStep();
            closeOrder(grid, calculateProfit(grid, fibonacci1618 - fibonacci0618), Strategy.ACSL0CLASSIC1);
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            closeOrder(grid, calculateExpense(grid, fibonacci0618 - 0), Strategy.ACSL0CLASSIC1);
            grid.setStep(15);
            printStep();
        }
    }

    private void calculate61To38To100To61(Grid grid) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD) {
            grid.setStep(15);
            printStep();
            closeOrder(grid, 0, Strategy.CLASSIC);
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            var profit = calculateExpense(grid, fibonacci1618 - fibonacci1000);
            grid.setStep(15);
            printStep();
            closeOrder(grid, profit, Strategy.CLASSIC);
        }
    }

    private void printStep() {
    }
}
