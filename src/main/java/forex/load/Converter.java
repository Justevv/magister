package forex.load;

import java.time.LocalDateTime;

public class Converter {

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

    boolean checkBar(Price currentPrice, Price featurePrice) {
        LocalDateTime datePlus1Bar = featurePrice.getDateValue();
        LocalDateTime datePlus1Minutes = currentPrice.getDateValue().plusMinutes(1);
        return datePlus1Minutes.isEqual(datePlus1Bar);
    }
}
