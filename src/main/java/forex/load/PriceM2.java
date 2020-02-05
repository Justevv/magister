package forex.load;

import java.util.Calendar;

import lombok.Data;

@Data
public class PriceM2 {
    private Calendar dateValue;  //Массив дат
    private float minPrice;  //Массив минимума
    private float maxPrice;  //Массив максимума
    private float EMA;

}
