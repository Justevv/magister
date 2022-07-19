package forex.processing;

import forex.entity.CloseStrategy;
import forex.entity.Grid;
import forex.entity.Order;
import forex.entity.Strategy;
import forex.load.Price;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private static final float SPREAD = 0.00020f;
    private static final float FILTER = 0.00001f;
    private static final float PRICE_MULTIPLIER = 100000;
    private static final float FIBONACCI_0382 = 0.382f;
    private static final float FIBONACCI_0618 = 0.618f;
    private static final float FIBONACCI_1618 = 1.618f;
    private static final int FINAL_STEP = 25;
    private final List<Grid> workGrid = new ArrayList<>();
    @Getter
    private final List<Grid> orders = new ArrayList<>();

    public void addWorkGrid(Grid grid) {
        workGrid.add(grid);
    }

    public void processing(Price price) {
        for (int i = 0; i < workGrid.size(); i++) {
            filterByTime(workGrid.get(i), price);
            var steps = workGrid.get(i).getSteps();
            switch (steps.get(steps.size() - 1)) {
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
                case FINAL_STEP -> {
                    orders.add(workGrid.get(i));
//                    if (workGrid.get(i).getBuyDataValue().getDayOfMonth() == 5 && workGrid.get(i).getBuyDataValue().getHour()>8) {
                    System.out.println("close");
                    System.out.println(workGrid.get(i));
//                    }
                    workGrid.remove(workGrid.get(i));
                }
                case 20 -> resolveStepAfter18(workGrid.get(i), price);
                case 21 -> calculateAfter18Low(workGrid.get(i), price);
                case 22 -> calculateAfter18High(workGrid.get(i), price);
                default -> throw new IllegalStateException("Unexpected value: " + steps.get(steps.size() - 1));
            }
        }
    }

    private void filterByTime(Grid grid, Price price) {
        var steps = grid.getSteps();
        if (steps.get(steps.size() - 1) < 20 && price.getDateValue().getHour() >= 18) {
            grid.getSteps().add(20);
        }
    }

    private void resolveStepAfter18(Grid grid, Price price) {
        if (grid.getBuyMaxGrid() > price.getMinPrice()) {
            grid.getSteps().add(21);
        } else if (grid.getBuyMaxGrid() < price.getMinPrice()) {
            grid.getSteps().add(22);
        }
    }

    private void calculateStart(Grid grid, Price price) {
        if (isFibonacci0382Reached(grid, price)) {
            openOrder(grid, Strategy.MR38SL0ACSL1CLASSIC62, price);
            grid.getMaximumRollback().setLevel38(true);
            grid.getSteps().add(6);
        } else if (isFibonacci100Reached(grid, price)) {
            grid.getMaximumLevel().setLevel100(true);
            grid.getSteps().add(2);
            openOrder(grid, Strategy.CLASSIC, price);
        }
    }

    private void openOrder(Grid grid, Strategy strategy, Price price) {
        var order = Order.builder()
                .strategy(strategy)
                .openTime(price.getDateValue())
                .openPrice(calculateOpenPrice(grid, strategy))
                .build();
        grid.getOrders().add(order);
    }

    private float calculateOpenPrice(Grid grid, Strategy strategy) {
        return switch (strategy) {
            case CLASSIC -> grid.getBuyMaxGrid() + SPREAD + FILTER;
            case ACSL0CLASSIC1, ACSL38CLASSIC1, MD61SL0, MD61SL38 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0618) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case MD38SL2000, MD38SLO, MR38SL0ACSL1CLASSIC62 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0382) / PRICE_MULTIPLIER + SPREAD + FILTER;
        };
    }

    private float calculateClosePrice(Grid grid, CloseStrategy closeStrategy) {
        return switch (closeStrategy) {
            case FIBONACCI1618 -> grid.getBuyMaxGrid() + (grid.getSizeGrid() * (FIBONACCI_1618 - 1)) / PRICE_MULTIPLIER;
            case FIBONACCI1000 -> grid.getBuyMaxGrid() + SPREAD + FILTER;
            case FIBONACCI0618 -> grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0618) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case FIBONACCI0382 -> grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0382) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case FIBONACCI0 -> grid.getBuyMinGrid() + (grid.getSizeGrid() * 0) / PRICE_MULTIPLIER;
            case FIBONACCI0MINUS2000 -> grid.getBuyMinGrid() + (grid.getSizeGrid() * 0) / PRICE_MULTIPLIER - 0.02f;
        };
    }

    private void closeOrder(Grid grid, Strategy strategy, CloseStrategy closeStrategy) {
        var order = grid.getOrders().stream().filter(x -> x.getStrategy().equals(strategy)).findFirst().orElseThrow();
        order.setCloseStrategy(closeStrategy);
        order.setClosePrice(calculateClosePrice(grid, closeStrategy));
        order.setProfit((order.getClosePrice() - order.getOpenPrice()) * PRICE_MULTIPLIER);
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
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
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
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.MD61SL38, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.MD61SL0, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
        } else if (isFibonacci0382Reached(grid, price)) {
            openOrder(grid, Strategy.MD38SLO, price);
            openOrder(grid, Strategy.MD38SL2000, price);
            grid.getMaximumDrawdown().setLevel38(true);
            grid.getSteps().add(4);
            closeOrder(grid, Strategy.MD61SL38, CloseStrategy.FIBONACCI0382);
        }
    }

    private void calculate100To61To38(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.MD61SL0, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.MD38SL2000, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.MD38SLO, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevel0(true);
            grid.getSteps().add(5);
            closeOrder(grid, Strategy.MD61SL0, CloseStrategy.FIBONACCI0);
            closeOrder(grid, Strategy.MD38SLO, CloseStrategy.FIBONACCI0);
        }
    }

    private void calculate100To38ToZero(Grid grid, Price price) {
        if (isFibonacci100Reached(grid, price)) {
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.MD38SL2000, CloseStrategy.FIBONACCI1000);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1000);
        } else if (isFibonacci0Minus2000Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevelMinus2000(true);
            closeOrder(grid, Strategy.MD38SL2000, CloseStrategy.FIBONACCI0MINUS2000);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI0MINUS2000);
            grid.getSteps().add(FINAL_STEP);
        }
    }

    private void calculate61To38(Grid grid, Price price) {
        if (isFibonacci0618ReachedLow(grid, price)) {
            openOrder(grid, Strategy.ACSL0CLASSIC1, price);
            openOrder(grid, Strategy.ACSL38CLASSIC1, price);
//            ACopen[currentTransaction] = 1;
            grid.getSteps().add(7);
        } else if (isFibonacci0Reached(grid, price)) {
            closeOrder(grid, Strategy.MR38SL0ACSL1CLASSIC62, CloseStrategy.FIBONACCI0);
            grid.getMaximumRollback().setLevel0(true);
            grid.getSteps().add(FINAL_STEP);
        }
    }

    private void calculate61To38To61(Grid grid, Price price) {
        if (isFibonacci100ReachedHigh(grid, price)) {
            grid.getMaximumLevel().setLevel100(true);
            grid.getSteps().add(8);
            openOrder(grid, Strategy.CLASSIC, price);
        } else if (isFibonacci0382ReachedLow(grid, price)) {
            closeOrder(grid, Strategy.ACSL38CLASSIC1, CloseStrategy.FIBONACCI0382);
            grid.getMaximumRollback().setLevelAC38(true);
            grid.getSteps().add(9);
        }
    }

    private void calculate61To38To100(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.ACSL38CLASSIC1, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.ACSL0CLASSIC1, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.MR38SL0ACSL1CLASSIC62, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
        } else if (isFibonacci0618ReachedSpread(grid, price)) {
            grid.getMaximumDrawdown().setLevel61(true);
            closeOrder(grid, Strategy.MR38SL0ACSL1CLASSIC62, CloseStrategy.FIBONACCI0618);
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
            closeOrder(grid, Strategy.ACSL0CLASSIC1, CloseStrategy.FIBONACCI0);
            grid.getSteps().add(FINAL_STEP);
        }
    }

    private void calculate61To38To61To38To100(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.ACSL0CLASSIC1, CloseStrategy.FIBONACCI1618);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevel0(true);
            closeOrder(grid, Strategy.ACSL0CLASSIC1, CloseStrategy.FIBONACCI0);
            grid.getSteps().add(FINAL_STEP);
        }
    }

    private void calculate61To38To100To61(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getMaximumDrawdown().setLevel0(true);
            grid.getSteps().add(FINAL_STEP);
            closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI0);
        }
    }

    private void calculateAfter18High(Grid grid, Price price) {
        if (isFibonacci1618Reached(grid, price)) {
            grid.getMaximumLevel().setLevel161(true);
            grid.getSteps().add(FINAL_STEP);
            grid.getOrders().forEach(x -> closeOrder(grid, x.getStrategy(), CloseStrategy.FIBONACCI1618));
            if (grid.getOrders().stream().anyMatch(x -> x.getStrategy().equals(Strategy.CLASSIC))) {
                closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1618);
            }
        } else if (isFibonacci100Reached(grid, price)) {
            grid.getSteps().add(FINAL_STEP);
            grid.getOrders().forEach(x -> closeOrder(grid, x.getStrategy(), CloseStrategy.FIBONACCI1000));
            if (grid.getOrders().stream().anyMatch(x -> x.getStrategy().equals(Strategy.CLASSIC))) {
                closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1000);
            }
        }
    }

    private void calculateAfter18Low(Grid grid, Price price) {
        if (isFibonacci100Reached(grid, price)) {
            grid.getSteps().add(FINAL_STEP);
            grid.getOrders().forEach(x -> closeOrder(grid, x.getStrategy(), CloseStrategy.FIBONACCI1000));
            if (grid.getOrders().stream().anyMatch(x -> x.getStrategy().equals(Strategy.CLASSIC))) {
                closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI1000);
            }
        } else if (isFibonacci0Reached(grid, price)) {
            grid.getSteps().add(FINAL_STEP);
            grid.getOrders().forEach(x -> closeOrder(grid, x.getStrategy(), CloseStrategy.FIBONACCI0));
            if (grid.getOrders().stream().anyMatch(x -> x.getStrategy().equals(Strategy.CLASSIC))) {
                closeOrder(grid, Strategy.CLASSIC, CloseStrategy.FIBONACCI0);
            }
        }
    }

}
