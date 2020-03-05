package forex.load;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import forex.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertM1ToM3 {
    private static final Logger LOGGER = LogManager.getLogger(ConvertM1ToM3.class);
    private List<Price> prices = new ArrayList<>((int) (Calculate.size * 0.34));

    public List<Price> convert(List<Price> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 2; m2++) {
            Price price = new Price();
            price.setDateValue(priceM1List.get(m2).getDateValue());
            Calendar newCalendar = new GregorianCalendar();
            newCalendar.setTime(priceM1List.get(m2).getDateValue());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(priceM1List.get(m2).getDateValue());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(priceM1List.get(m2 + 2).getDateValue());
            newCalendar.add(Calendar.MINUTE, 2);
            if ((calendar.get(Calendar.MINUTE) % 3) == 0 && newCalendar.equals(calendar2)) {
                price.setMaxPrice(Math.max(priceM1List.get(m2 + 2).getMaxPrice(), Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice())));
                price.setMinPrice(Math.min(priceM1List.get(m2 + 2).getMinPrice(), Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice())));
                price.setClosePrice(priceM1List.get(m2 + 2).getClosePrice());
                m2 += 2;
            } else {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(priceM1List.get(m2 + 1).getDateValue());
                Calendar newCalendar2 = new GregorianCalendar();
                newCalendar2.setTime(priceM1List.get(m2).getDateValue());
                newCalendar2.add(Calendar.MINUTE, 1);
                if ((calendar.get(Calendar.MINUTE) % 3 == 0 &&
                        (newCalendar.equals(calendar1)) ||
                        newCalendar2.equals(calendar1))) {
                    price.setMaxPrice(Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice()));
                    price.setMinPrice(Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice()));
                    price.setClosePrice(priceM1List.get(m2 + 1).getClosePrice());
                    m2++;
                } else {
                    price.setMaxPrice(priceM1List.get(m2).getMaxPrice());
                    price.setMinPrice(priceM1List.get(m2).getMinPrice());
                    price.setClosePrice(priceM1List.get(m2).getClosePrice());
                }
            }
            prices.add(price);
            LOGGER.debug(price);
        }
        return prices;
    }
}
