package forex.processing;

import forex.entity.*;
import forex.load.Price;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static forex.constant.Constant.*;

@Service
public class OrderService {

    public void openOrders(Grid grid, OpenStrategy openStrategy, Price price) {
        Arrays.stream(Strategy.values())
                .filter(x -> x.getOpenStrategy().equals(openStrategy))
                .forEach(x -> openOrder(grid, x, price));
    }

    private void openOrder(Grid grid, Strategy strategy, Price price) {
        var order = Order.builder()
                .strategy(strategy)
                .openTime(price.getDateValue())
                .openPrice(calculateOpenPrice(grid, strategy))
                .build();
        grid.getOrders().add(order);

    }

    public void closeOrders(Grid grid, List<Strategy> strategies, ClosePriceType closePriceType) {
        strategies.forEach(strategy -> closeOrder(grid, strategy, closePriceType));
    }

    private void closeOrder(Grid grid, Strategy strategy, ClosePriceType closePriceType) {
        var order = grid.getOrders().stream().filter(x -> x.getStrategy().equals(strategy)).findFirst().orElseThrow();
        order.setClosePriceType(closePriceType);
        order.setClosePrice(calculateClosePrice(grid, closePriceType));
        order.setProfit((order.getClosePrice() - order.getOpenPrice()) * PRICE_MULTIPLIER);
    }

    private float calculateOpenPrice(Grid grid, Strategy strategy) {
        return switch (strategy.getOpenStrategy().getOpenPrice()) {
            case FIBONACCI1000 -> grid.getBuyMaxGrid() + SPREAD + FILTER;
            case FIBONACCI0618 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0618) / PRICE_MULTIPLIER + SPREAD + FILTER;
            case FIBONACCI0382 ->
                    grid.getBuyMinGrid() + (grid.getSizeGrid() * FIBONACCI_0382) / PRICE_MULTIPLIER + SPREAD + FILTER;
        };
    }

    private float calculateClosePrice(Grid grid, ClosePriceType closePriceType) {
        return switch (closePriceType) {
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
