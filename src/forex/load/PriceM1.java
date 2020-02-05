package forex.load;

import java.util.Calendar;

public class PriceM1 {
    private Calendar m1DateValue;  //Массив дат
    private float m1MinPrice;  //Массив минимума
    private float m1MaxPrice;  //Массив максимума

    public PriceM1(Calendar parsingDate, float valueOf, float valueOf1) {
        m1DateValue = parsingDate;
        m1MaxPrice = valueOf;
        m1MinPrice = valueOf1;
    }

    @Override
    public String toString() {
        return "PriceM1{" +
                "m1DateValue=" + m1DateValue +
                ", m1MinPrice=" + m1MinPrice +
                ", m1MaxPrice=" + m1MaxPrice +
                '}';
    }

    public Calendar getM1DateValue() {
        return m1DateValue;
    }

    public void setM1DateValue(Calendar m1DateValue) {
        this.m1DateValue = m1DateValue;
    }

    public float getM1MinPrice() {
        return m1MinPrice;
    }

    public void setM1MinPrice(float m1MinPrice) {
        this.m1MinPrice = m1MinPrice;
    }

    public float getM1MaxPrice() {
        return m1MaxPrice;
    }

    public void setM1MaxPrice(float m1MaxPrice) {
        this.m1MaxPrice = m1MaxPrice;
    }
}
