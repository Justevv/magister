package forex.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Strategy strategy;
    private ClosePriceType closePriceType;
    private LocalDateTime openTime;
    private double openPrice;
    private double closePrice;
    private double profit;
}
