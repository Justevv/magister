package forex.processing;

import forex.entity.Grid;
import forex.entity.Order;
import forex.entity.Strategy;
import forex.load.Price;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private static final float SPREAD = 0.00020f;
    private static final float SPREAD_FULL = 20;
    private static final float FIBONACCI_0382 = 0.382f;
    private static final float FIBONACCI_0618 = 0.618f;
    private static final float FIBONACCI_1000 = 1f;
    private static final float FIBONACCI_1618 = 1.618f;
    private final List<Grid> workGrid = new ArrayList<>();
    @Getter
    private final List<Grid> orders = new ArrayList<>();

    public void addWorkGrid(Grid grid) {
        workGrid.add(grid);
    }

    public void processing(Price price) {
        for (int i = 0; i < workGrid.size(); i++) {
            var steps = workGrid.get(i).getSteps();
            switch (steps.get(steps.size()-1)) {
                case 1 -> calculateStart(workGrid.get(i), price);
                case 2 -> calculate61To100(workGrid.get(i), price);
                case 3 -> calculate100To61(workGrid.get(i), price);
                case 4 -> calculate100To61To38(workGrid.get(i), price);
                case 5 -> calculate100To38ToZero(workGrid.get(i), price);
                case 6 -> calculate61To38(workGrid.get(i), price);
                case 7 -> calculate61To38To61(workGrid.get(i), price);
                case 8 -> calculate61To38To100(workGrid.get(i), price);
                case 9 -> calculate61To38To61To38(workGrid.get(i), price);
                case 10 -> calculate61To38To100To61(workGrid.get(i), price);
                case 11 -> calculate61To38To61To38To100(workGrid.get(i), price);
                case 15 -> {
                    orders.add(workGrid.get(i));
                    workGrid.remove(workGrid.get(i));
                }
                default -> throw new IllegalStateException("Unexpected value: " + steps.get(steps.size()-1));
            }
        }
    }

    private void calculateStart(Grid grid, Price price) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice()) {
            openOrder(grid, Strategy.MR38SL0ACSL1CLASSIC62, price);
            grid.getMaximumRollback().setLevel38(true);
            grid.getSteps().add(6);
        } else if (grid.getBuyMaxGrid() < price.getMaxPrice() - SPREAD) {
            grid.getMaximumLevel().setLevel100(true);
            grid.getSteps().add(2);
            openOrder(grid, Strategy.CLASSIC, price);
        }
    }

    private void openOrder(Grid grid, Strategy strategy, Price price) {
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
        return grid.getSizeGrid() * (difference) - SPREAD_FULL;
    }

    private double calculateExpense(Grid grid, double difference) {
        return -(grid.getSizeGrid() * (difference) + SPREAD_FULL);
    }

    private boolean isFibonacci1618Reached(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * FIBONACCI_1618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD;
    }

    private boolean isFibonacci100Reached(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD;
    }

    private boolean isFibonacci100ReachedHigh(Grid grid, Price price) {
        return grid.getBuyMaxGrid() < price.getMaxPrice();
    }

    private boolean isFibonacci0618Reached(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > price.getMinPrice();
    }

    private boolean isFibonacci0618ReachedSpread(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > price.getMinPrice() - SPREAD;
    }

    private boolean isFibonacci0618ReachedLow(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() < price.getMaxPrice() - SPREAD;
    }

    private boolean isFibonacci0382Reached(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice();
    }

    private boolean isFibonacci0382ReachedLow(Grid grid, Price price) {
        return (grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice() - SPREAD;
    }

    private boolean isFibonacci0Reached(Grid grid, Price price) {
        return grid.getBuyMinGrid() > price.getMinPrice();
    }

    private boolean isFibonacci0Minus2000Reached(Grid grid, Price price) {
        return grid.getBuyMinGrid() + 0.02000 > price.getMinPrice();
    }

    private void calculate61To100(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(15);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - 1.0), Strategy.CLASSIC);
        } else if (isFibonacci0618Reached(grid, price)) {
            openOrder(grid, Strategy.MD61SL38, price);
            openOrder(grid, Strategy.MD61SL0, price);
            grid.getMaximumDrawdown().setLevel61(true);
            grid.getSteps().add(3);
        }
    }

    private void calculate100To61(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(15);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_0618), Strategy.MD61SL38);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.618), Strategy.MD61SL0);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_1000), Strategy.CLASSIC);
        } else if (isFibonacci0382Reached(grid, price)) {
            openOrder(grid, Strategy.MD38SLO, price);
            openOrder(grid, Strategy.MD38SL2000, price);
            grid.getMaximumDrawdown().setLevel38(true);
            grid.getSteps().add(4);
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0618 - FIBONACCI_0382), Strategy.MD61SL38);
        }
    }

    private void calculate100To61To38(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(15);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.618), Strategy.MD61SL0);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.382), Strategy.MD38SL2000);
            closeOrder(grid, calculateProfit(grid, 1.618 - 0.382), Strategy.MD38SLO);
            var profit = calculateProfit(grid, 1.618 - 1.0);
            closeOrder(grid, profit, Strategy.CLASSIC);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevel0(true);
            grid.getSteps().add(5);
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0618 - 0), Strategy.MD61SL0);
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0382 - 0), Strategy.MD38SLO);
        }
    }

    private void calculate100To38ToZero(Grid grid, Price price) {
        if (isFibonacci100Reached(grid, price)) {
            grid.getSteps().add(15);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1000 - FIBONACCI_0382), Strategy.MD38SL2000);
            closeOrder(grid, 1, Strategy.CLASSIC);
        } else if (isFibonacci0Minus2000Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevelMinus2000(true);
            var profit = calculateExpense(grid, 1) - 2000;
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0382 - 0) - 2000, Strategy.MD38SL2000);
            closeOrder(grid, profit, Strategy.CLASSIC);
            grid.getSteps().add(15);
        }
    }

    private void calculate61To38(Grid grid, Price price) {
        if (isFibonacci0618ReachedLow(grid, price)) {
            openOrder(grid, Strategy.ACSL0CLASSIC1, price);
            openOrder(grid, Strategy.ACSL38CLASSIC1, price);
//            ACopen[currentTransaction] = 1;
            grid.getSteps().add(7);
        } else if (isFibonacci0Reached(grid, price)) {
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0382 - 0), Strategy.MR38SL0ACSL1CLASSIC62);
            grid.getMaximumRollback().setLevel0(true);
            grid.getSteps().add(15);
        }
    }

    private void calculate61To38To61(Grid grid, Price price) {
        if (isFibonacci100ReachedHigh(grid, price)) {
            grid.getMaximumLevel().setLevel100(true);
            grid.getSteps().add(8);
            openOrder(grid, Strategy.CLASSIC, price);
        } else if (isFibonacci0382ReachedLow(grid, price)) {
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0618 - FIBONACCI_0382), Strategy.ACSL38CLASSIC1);
            grid.getMaximumRollback().setLevelAC38(true);
            grid.getSteps().add(9);
        }
    }

    private void calculate61To38To100(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(15);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_0618), Strategy.ACSL38CLASSIC1);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_0618), Strategy.ACSL0CLASSIC1);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_0382), Strategy.MR38SL0ACSL1CLASSIC62);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_1000), Strategy.CLASSIC);
        } else if (isFibonacci0618ReachedSpread(grid, price)) {
            grid.getMaximumDrawdown().setLevel61(true);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_0618 - FIBONACCI_0382), Strategy.MR38SL0ACSL1CLASSIC62);
//            BUACFibo61[currentTransaction] = 1;
            grid.getSteps().add(10);
        }
    }

    private void calculate61To38To61To38(Grid grid, Price price) {
        if (isFibonacci100Reached(grid, price)) {
            grid.getMaximumLevel().setLevel100(true);
            grid.getSteps().add(11);
            openOrder(grid, Strategy.CLASSIC, price);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumRollback().setLevel0(true);
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0618 - 0), Strategy.ACSL0CLASSIC1);
            grid.getSteps().add(15);
        }
    }

    private void calculate61To38To61To38To100(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(15);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_0618), Strategy.ACSL0CLASSIC1);
            closeOrder(grid, calculateProfit(grid, FIBONACCI_1618 - FIBONACCI_1000), Strategy.CLASSIC);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevel0(true);
            closeOrder(grid, calculateExpense(grid, FIBONACCI_0618 - 0), Strategy.ACSL0CLASSIC1);
            grid.getSteps().add(15);
        }
    }

    private void calculate61To38To100To61(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(15);
            closeOrder(grid, 1, Strategy.CLASSIC);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevel0(true);
            grid.getSteps().add(15);
            closeOrder(grid, calculateExpense(grid, FIBONACCI_1000 - 0), Strategy.CLASSIC);
        }
    }

}
