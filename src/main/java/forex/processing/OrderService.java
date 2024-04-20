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
        var optionalOrder = grid.getOrders().stream().filter(x -> x.getStrategy().equals(strategy)).findFirst();
        if (optionalOrder.isPresent()) {
            var order = optionalOrder.get();
            order.setClosePriceType(closePriceType);
            order.setClosePrice(calculateClosePrice(grid, closePriceType));
            order.setProfit((order.getClosePrice() - order.getOpenPrice()));
        }
    }

    private int calculateOpenPrice(Grid grid, Strategy strategy) {
        return (int) (grid.getBuyMinGrid() + (grid.getSizeGrid() * strategy.getOpenStrategy().getOpenPrice().getValue()) + SPREAD + FILTER);
    }

    private int calculateClosePrice(Grid grid, ClosePriceType closePriceType) {
        final double price = grid.getBuyMinGrid() + (grid.getSizeGrid() * closePriceType.getValue());
        return switch (closePriceType) {
            case FIBONACCI_1618, FIBONACCI_0 -> (int) price;
            case FIBONACCI_1000, FIBONACCI_0618, FIBONACCI_0382 -> (int) (price + SPREAD + FILTER);
            case FIBONACCI_0_MINUS_2000 -> (int) price - MINUS_2000;
        };
    }


}
