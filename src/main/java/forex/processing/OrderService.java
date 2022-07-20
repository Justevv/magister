package forex.processing;

import forex.entity.CloseStrategy;
import forex.entity.Grid;
import forex.entity.Order;
import forex.entity.Strategy;
import forex.load.Price;
import org.springframework.stereotype.Service;

import java.util.List;

import static forex.constant.Constant.*;

@Service
public class OrderService {

    public void openOrder(Grid grid, Strategy strategy, Price price) {
        var order = Order.builder()
                .strategy(strategy)
                .openTime(price.getDateValue())
                .openPrice(calculateOpenPrice(grid, strategy))
                .build();
        grid.getOrders().add(order);
    }

    public void closeOrder(Grid grid, List<Strategy> strategies, CloseStrategy closeStrategy) {
        strategies.forEach(strategy -> closeOrder(grid, strategy, closeStrategy));
    }

    public void closeOrder(Grid grid, Strategy strategy, CloseStrategy closeStrategy) {
        var order = grid.getOrders().stream().filter(x -> x.getStrategy().equals(strategy)).findFirst().orElseThrow();
        order.setCloseStrategy(closeStrategy);
        order.setClosePrice(calculateClosePrice(grid, closeStrategy));
        order.setProfit((order.getClosePrice() - order.getOpenPrice()) * PRICE_MULTIPLIER);
    }

    private float calculateOpenPrice(Grid grid, Strategy strategy) {
        return switch (strategy.getStrategy()) {
            case FIBONACCI1000 -> grid.getBuyMaxGrid() + SPREAD + FILTER;
            case FIBONACCI0618 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0618) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case FIBONACCI0382 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0382) / PRICE_MULTIPLIER + SPREAD + FILTER;
        };
    }

    private float calculateClosePrice(Grid grid, CloseStrategy closeStrategy) {
        return switch (closeStrategy) {
            case FIBONACCI1618 -> grid.getBuyMaxGrid() + (grid.getSizeGrid() * (FIBONACCI_1618 - 1)) / PRICE_MULTIPLIER;
            case FIBONACCI1000 -> grid.getBuyMaxGrid() + SPREAD + FILTER;
            case FIBONACCI0618 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0618) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case FIBONACCI0382 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0382) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case FIBONACCI0 -> grid.getBuyMinGrid() + (grid.getSizeGrid() * 0) / PRICE_MULTIPLIER;
            case FIBONACCI0MINUS2000 -> grid.getBuyMinGrid() + (grid.getSizeGrid() * 0) / PRICE_MULTIPLIER - 0.02f;
        };
    }


}
