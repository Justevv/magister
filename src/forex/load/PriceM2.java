package forex.load;

import java.util.Date;

public class PriceM2 {
    private Date dateValue;  //Массив дат
    private Double minPrice;  //Массив минимума
    private Double maxPrice;  //Массив максимума

    @Override
    public String toString() {
        return "PriceM2{" +
                "dateValue=" + dateValue +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }

    public PriceM2() {
    }

    public PriceM2(Date parsingDate, Double valueOf, Double valueOf1) {
        dateValue = parsingDate;
        minPrice = valueOf;
        maxPrice = valueOf1;
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
