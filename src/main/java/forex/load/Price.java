package forex.load;

import java.util.Calendar;

import lombok.Data;

@Data
public class Price {
    private Calendar dateValue;  //Массив дат
    private float minPrice;  //Массив минимума
    private float maxPrice;  //Массив максимума
    private float EMA;

    public Price() {
    }

    public Price(Calendar parsingDate, float maxPrice, float minPrice) {
        this.dateValue = parsingDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
