package forex.load;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import forex.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertM1ToM3 extends Converter {
    private static final Logger LOGGER = LogManager.getLogger(ConvertM1ToM3.class);
    private List<Price> prices = new ArrayList<>((int) (Calculate.size * 0.34));
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendarPlus2Bar = Calendar.getInstance();
    private Calendar calendarPlus2Minutes = Calendar.getInstance();

    public List<Price> convert(List<Price> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 2; m2++) {
            Price price = new Price();
            price.setDateValue(priceM1List.get(m2).getDateValue());
            calendar.setTime(priceM1List.get(m2).getDateValue());
            calendarPlus2Minutes.setTime(priceM1List.get(m2).getDateValue());
            calendarPlus2Minutes.add(Calendar.MINUTE, 2);
            calendarPlus2Bar.setTime(priceM1List.get(m2 + 2).getDateValue());
            if ((calendar.get(Calendar.MINUTE) % 3) == 0 && calendarPlus2Minutes.equals(calendarPlus2Bar)) {
                price.setMaxPrice(Math.max(priceM1List.get(m2 + 2).getMaxPrice(), Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice())));
                price.setMinPrice(Math.min(priceM1List.get(m2 + 2).getMinPrice(), Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice())));
                price.setClosePrice(priceM1List.get(m2 + 2).getClosePrice());
                m2 += 2;
            } else {
                settingCalendarPlus1(priceM1List, m2);
                if (calendar.get(Calendar.MINUTE) % 3 == 0 && calendarPlus2Minutes.equals(calendarPlus1Bar) ||
                        calendarPlus1Minutes.equals(calendarPlus1Bar)) {
                    m2 = setPriceTwoCoincidences(priceM1List, m2, price);
                } else {
                    setPriceOneCoincidences(priceM1List, m2, price);
                }
            }
            prices.add(price);
            LOGGER.debug(price);
        }
        return prices;
    }


}
