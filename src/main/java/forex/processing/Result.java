package forex.processing;

import forex.load.Price;
import lombok.Getter;

import java.util.*;

public class Result {

    public static int countDeal = 50000;
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
    private int TP[] = new int[countDeal];
    private List<Float> point = Arrays.asList(0f, 0f, 0f, 0f);
    public float system1Point = 0;
    public float system2Point = 0;
    public float system3Point = 0;
    public float system4Point = 0;
    public float system5Point = 0;
    public float system6Point = 0;
    public float system7Point = 0;
    private int classicOpen = 0;
    private int classicClose = 0;
    private int unprofitableDeals = 0;
    private int profitableDeals = 0;
    private float spread = 0.00020f;
    private float spreadFull = 20;
    private float fibonacci0382 = 0.382f;
    private float fibonacci0618 = 0.618f;
    private float fibonacci1000 = 1f;
    private float fibonacci1618 = 1.618f;
    private Price price;
    private List<Grid> workGrid = new ArrayList<>();
    private Map<String, Order> orderMap = new LinkedHashMap<>();
    @Getter
    private List<Order> classicOrders = new ArrayList<>();

    public List<Float> getPoint() {
        return point;
    }

    public List<Float> getResult() {
        return Arrays.asList(system1Point, system2Point, system3Point, system4Point, system5Point, system6Point,
                system7Point);
    }

    public void addWorkGrid(Grid grid) {
        workGrid.add(grid);
    }

    public void processing(Price price) {
        this.price = price;
        for (int i = 0; i < workGrid.size(); i++) {
            switch (workGrid.get(i).getStep()) {
                case 1 -> calculateStart(workGrid.get(i), i);
                case 2 -> calculate61To100(workGrid.get(i), i);
                case 3 -> calculate100To61(workGrid.get(i), i);
                case 4 -> calculate100To61To38(workGrid.get(i), i);
                case 5 -> calculate100To38ToZero(workGrid.get(i), i);
                case 6 -> calculate61To38(workGrid.get(i), i);
                case 7 -> calculate61To38To61(workGrid.get(i), i);
                case 8 -> calculate61To38To100(workGrid.get(i), i);
                case 9 -> calculate61To38To61To38(workGrid.get(i), i);
                case 10 -> calculate61To38To100To61(workGrid.get(i), i);
                case 15 -> workGrid.remove(workGrid.get(i));
            }
        }
    }

    private void calculateStart(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice()) {
            MOFibo38[currentTransaction] = 1;
            grid.setStep(6);
            printStep();
        } else if (grid.getBuyMaxGrid() < price.getMaxPrice() - spread) {
            classic[currentTransaction] = 1;
            grid.setStep(2);
            printStep();
            classicOpen++;
        }
    }

    private void calculate61To100(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * fibonacci1618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            var profit = grid.getSizeGrid() * (fibonacci1618 - 1.0) - spreadFull;
            var order = Order.builder()
                    .strategy(Strategy.CLASSIC)
                    .grid(grid)
                    .profit(profit)
                    .build();
            classicOrders.add(order);
            classicClose++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > price.getMinPrice()) {
            MPFibo61[currentTransaction] = 1;
            grid.setStep(3);
            printStep();
        }
    }

    private void calculate100To61(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            system1Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            point.set(0, point.get(0) + grid.getSizeGrid() * (fibonacci1618 - fibonacci0618) - spreadFull);
            system2Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            var profit = grid.getSizeGrid() * (1.618 - 1.0) - spreadFull;
            var order = Order.builder()
                    .strategy(Strategy.CLASSIC)
                    .grid(grid)
                    .profit(profit)
                    .build();
            classicOrders.add(order);
            profitableDeals++;
            classicClose++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice()) {
            MPFibo38[currentTransaction] = 1;
            unprofitableDeals++;
            grid.setStep(4);
            printStep();
            system1Point -= grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) + spreadFull;
            point.set(0, point.get(0) - grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) - spreadFull);
        }
    }

    private void calculate100To61To38(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            TP[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            var profit = grid.getSizeGrid() * (1.618 - 1.0) - spreadFull;
            var order = Order.builder()
                    .strategy(Strategy.CLASSIC)
                    .grid(grid)
                    .profit(profit)
                    .build();
            classicOrders.add(order);
            system2Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system3Point += grid.getSizeGrid() * (1.618 - 0.382) - spreadFull;
            system7Point += grid.getSizeGrid() * (1.618 - 0.382) - spreadFull;
            classicClose++;
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            SLclassic[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(5);
            printStep();
            system2Point -= grid.getSizeGrid() * (fibonacci0618 - 0) + spreadFull;
            system7Point -= grid.getSizeGrid() * (fibonacci0382 - 0) + spreadFull;
        }
    }

    private void calculate100To38ToZero(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            grid.setStep(15);
            printStep();
            system3Point += grid.getSizeGrid() * (fibonacci1000 - fibonacci0382) - spreadFull;
            classicClose++;
        } else if (grid.getBuyMinGrid() + 0.02000 > price.getMinPrice()) {
            var profit = -(grid.getSizeGrid() * (1) + 2000 + spreadFull);
            var order = Order.builder()
                    .strategy(Strategy.CLASSIC)
                    .grid(grid)
                    .profit(profit)
                    .build();
            classicOrders.add(order);
            system3Point -= grid.getSizeGrid() * (fibonacci0382 - 0) + 2000 + spreadFull;
            classicClose++;
            grid.setStep(15);
            printStep();
        }
    }

    private void calculate61To38(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            ACopen[currentTransaction] = 1;
            grid.setStep(7);
            printStep();
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            system6Point -= grid.getSizeGrid() * (fibonacci0382 - 0) + spreadFull;
            SLFibo0[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
        }
    }

    private void calculate61To38To61(Grid grid, int currentTransaction) {
        if (grid.getBuyMaxGrid() < price.getMaxPrice()) {
            ACclassic[currentTransaction] = 1;
            grid.setStep(8);
            printStep();
            classicOpen++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.382 + grid.getBuyMinGrid() > price.getMinPrice() - spread) {
            system4Point -= grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) + spreadFull;
            SLFibo38[currentTransaction] = 1;
            unprofitableDeals++;
            end[currentTransaction] = 1;
            grid.setStep(9);
            printStep();
        }
    }

    private void calculate61To38To100(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            TPAC[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            system4Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system5Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
            system6Point += grid.getSizeGrid() * (1.618 - 0.382) - spreadFull;
            profitableDeals++;
            classicClose++;
        } else if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 0.618 + grid.getBuyMinGrid() > price.getMinPrice() - spread) {
            system6Point += grid.getSizeGrid() * (fibonacci0618 - fibonacci0382) - spreadFull;
            BUACFibo61[currentTransaction] = 1;
            end[currentTransaction] = 1;
            grid.setStep(10);
            printStep();
        }
    }

    private void calculate61To38To61To38(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            system5Point += grid.getSizeGrid() * (1.618 - 0.618) - spreadFull;
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            system5Point -= grid.getSizeGrid() * (fibonacci0618 - 0) + spreadFull;
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
        }
    }

    private void calculate61To38To100To61(Grid grid, int currentTransaction) {
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * 1.618 + grid.getBuyMinGrid() < price.getMaxPrice() - spread) {
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            classicClose++;
        } else if (grid.getBuyMinGrid() > price.getMinPrice()) {
            var profit = -(grid.getSizeGrid() * (fibonacci1618 - fibonacci1000) + spreadFull);
            var order = Order.builder()
                    .strategy(Strategy.CLASSIC)
                    .grid(grid)
                    .profit(profit)
                    .build();
            classicOrders.add(order);
            end[currentTransaction] = 1;
            grid.setStep(15);
            printStep();
            classicClose++;
        }
    }

    private void printStep() {
    }
}
