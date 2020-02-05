package forex.load;

import java.util.Calendar;

import lombok.Data;

@Data
public class PriceM1 {
    private Calendar m1DateValue;  //Массив дат
    private float m1MinPrice;  //Массив минимума
    private float m1MaxPrice;  //Массив максимума

    public PriceM1(Calendar parsingDate, float valueOf, float valueOf1) {
        m1DateValue = parsingDate;
        m1MaxPrice = valueOf;
        m1MinPrice = valueOf1;
    }

}
