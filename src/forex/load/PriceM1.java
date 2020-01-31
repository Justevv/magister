package forex.load;

import java.util.Date;

public class PriceM1 {
    private Date m1DateValue;  //Массив дат
    private Double m1MinPrice;  //Массив минимума
    private Double m1MaxPrice;  //Массив максимума

    public PriceM1(Date parsingDate, Double valueOf, Double valueOf1) {
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

    public Date getM1DateValue() {
        return m1DateValue;
    }

    public void setM1DateValue(Date m1DateValue) {
        this.m1DateValue = m1DateValue;
    }

    public Double getM1MinPrice() {
        return m1MinPrice;
    }

    public void setM1MinPrice(Double m1MinPrice) {
        this.m1MinPrice = m1MinPrice;
    }

    public Double getM1MaxPrice() {
        return m1MaxPrice;
    }

    public void setM1MaxPrice(Double m1MaxPrice) {
        this.m1MaxPrice = m1MaxPrice;
    }
}
