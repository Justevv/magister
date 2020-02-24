package forex.load;

import lombok.Data;

import java.util.Date;

@Data
public class Price {
    private Date dateValue;  //Массив дат
    private float minPrice;  //Массив минимума
    private float maxPrice;  //Массив максимума
    private float EMA;

    public Price() {
    }

    public Price(Date parsingDate, float maxPrice, float minPrice) {
        this.dateValue = parsingDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
