package forex.load;

import java.util.Date;

public class PriceM2 {
    private Date dateValue;  //Массив дат
    private float minPrice;  //Массив минимума
    private float maxPrice;  //Массив максимума
    private float EMA;

    @Override
    public String toString() {
        return "PriceM2{" +
                "dateValue=" + dateValue +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", EMA=" + EMA +
                '}';
    }

    public PriceM2() {
    }

    public float getEMA() {
        return EMA;
    }

    public void setEMA(float EMA) {
        this.EMA = EMA;
    }

    public PriceM2(Date parsingDate, float valueOf, float valueOf1, float EMA) {
        dateValue = parsingDate;
        minPrice = valueOf;
        maxPrice = valueOf1;
        this.EMA = EMA;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }


    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

}
