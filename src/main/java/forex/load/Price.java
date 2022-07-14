package forex.load;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Price {
    private LocalDateTime dateValue;  //Массив дат
    private float minPrice;  //Массив минимума
    private float maxPrice;  //Массив максимума
    private float closePrice;  //Массив максимума
    private float EMA;

    public Price() {
    }

    public Price(LocalDateTime parsingDate, float maxPrice, float minPrice, float closePrice) {
        this.dateValue = parsingDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.closePrice = closePrice;
    }
}
