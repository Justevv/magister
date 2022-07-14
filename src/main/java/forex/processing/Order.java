package forex.processing;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Strategy strategy;
    private LocalDateTime openTime;
    private double profit;
}
