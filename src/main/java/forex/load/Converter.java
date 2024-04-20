package forex.load;

import java.time.LocalDateTime;
import java.util.List;

public class Converter {
    LocalDateTime datePlus1Bar;
    LocalDateTime datePlus1Minutes;

    Price setPriceTwoCoincidences(Price currentPrice, Price featurePrice) {
        Price price = new Price();
        price.setMaxPrice(Math.max(currentPrice.getMaxPrice(), featurePrice.getMaxPrice()));
        price.setMinPrice(Math.min(currentPrice.getMinPrice(), featurePrice.getMinPrice()));
        price.setClosePrice(featurePrice.getClosePrice());
        price.setDateValue(currentPrice.getDateValue());
        return price;
    }

    Price setPriceOneCoincidences(Price m1Price) {
        Price price = new Price();
        price.setDateValue(m1Price.getDateValue());
        price.setMaxPrice(m1Price.getMaxPrice());
        price.setMinPrice(m1Price.getMinPrice());
        price.setClosePrice(m1Price.getClosePrice());
        return price;
    }

    void settingCalendarPlus1(List<Price> priceM1List, int m2) {
        datePlus1Bar = priceM1List.get(m2 + 1).getDateValue();
        datePlus1Minutes = priceM1List.get(m2).getDateValue().plusMinutes(1);
    }
}
