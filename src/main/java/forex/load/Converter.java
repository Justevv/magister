package forex.load;

import java.time.LocalDateTime;
import java.util.List;

public class Converter {
    LocalDateTime datePlus1Bar;
    LocalDateTime datePlus1Minutes;

    int setPriceTwoCoincidences(List<Price> priceM1List, int m2, Price price) {
        price.setMaxPrice(Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice()));
        price.setMinPrice(Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice()));
        price.setClosePrice(priceM1List.get(m2 + 1).getClosePrice());
        m2++;
        return m2;
    }

    void setPriceOneCoincidences(List<Price> priceM1List, int m2, Price price) {
        price.setMaxPrice(priceM1List.get(m2).getMaxPrice());
        price.setMinPrice(priceM1List.get(m2).getMinPrice());
        price.setClosePrice(priceM1List.get(m2).getClosePrice());
    }

    void settingCalendarPlus1(List<Price> priceM1List, int m2) {
        datePlus1Bar = priceM1List.get(m2 + 1).getDateValue();
        datePlus1Minutes = priceM1List.get(m2).getDateValue().plusMinutes(1);
    }
}
