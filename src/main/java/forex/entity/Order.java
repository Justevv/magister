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
    private int openPrice;
    private int closePrice;
    private int profit;
}
