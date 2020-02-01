package forex.load;

import java.util.Date;

public class PriceM2 {
    private Date dateValue;  //Массив дат
    private Double minPrice;  //Массив минимума
    private Double maxPrice;  //Массив максимума
    private Double EMA;

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

    public Double getEMA() {
        return EMA;
    }

    public void setEMA(Double EMA) {
        this.EMA = EMA;
    }

    public PriceM2(Date parsingDate, Double valueOf, Double valueOf1, Double EMA) {
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


    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

}
