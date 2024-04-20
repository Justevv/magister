package forex.load;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private LocalDateTime dateValue;
    private int maxPrice;
    private int minPrice;
    private int closePrice;
}
