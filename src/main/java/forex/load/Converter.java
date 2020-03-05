package forex.load;

import java.util.Calendar;
import java.util.List;

public class Converter {
    Calendar calendarPlus1Bar = Calendar.getInstance();
    Calendar calendarPlus1Minutes = Calendar.getInstance();

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
        calendarPlus1Bar.setTime(priceM1List.get(m2 + 1).getDateValue());
        calendarPlus1Minutes.setTime(priceM1List.get(m2).getDateValue());
        calendarPlus1Minutes.add(Calendar.MINUTE, 1);
    }
}
