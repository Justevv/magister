package forex.load;

import java.util.Date;

import lombok.Data;

@Data
public class Price {
    private Date dateValue;  //Массив дат
    private float minPrice;  //Массив минимума
    private float maxPrice;  //Массив максимума
    private float closePrice;  //Массив максимума
    private float EMA;

    public Price() {
    }

    public Price(Date parsingDate, float maxPrice, float minPrice, float closePrice) {
        this.dateValue = parsingDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.closePrice = closePrice;
    }
}
