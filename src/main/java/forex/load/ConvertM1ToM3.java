package forex.load;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertM1ToM3 {
    private static final Logger LOGGER = LogManager.getLogger(ConvertM1ToM3.class);
    private List<Price> prices = new ArrayList<>(DataLoading.size / 3);

    public List<Price> convert(List<Price> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 2; m2++) {
            Price price = new Price();
            price.setDateValue(priceM1List.get(m2).getDateValue());
            Calendar newCalendar = new GregorianCalendar();
            newCalendar.setTime(priceM1List.get(m2).getDateValue().getTime());
            newCalendar.add(Calendar.MINUTE, 2);
            if ((priceM1List.get(m2).getDateValue().get(Calendar.MINUTE) % 3) == 0 && newCalendar.equals(priceM1List.get(m2 + 2).getDateValue())) {
                price.setMaxPrice(Math.max(priceM1List.get(m2 + 2).getMaxPrice(), Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice())));
                price.setMinPrice(Math.min(priceM1List.get(m2 + 2).getMinPrice(), Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice())));
                m2 += 2;
            } else {
                Calendar newCalendar2 = new GregorianCalendar();
                newCalendar2.setTime(priceM1List.get(m2).getDateValue().getTime());
                newCalendar2.add(Calendar.MINUTE, 1);
                if ((priceM1List.get(m2).getDateValue().get(Calendar.MINUTE) % 3 == 0 &&
                        (newCalendar.equals(priceM1List.get(m2 + 1).getDateValue())) ||
                        newCalendar2.equals(priceM1List.get(m2 + 1).getDateValue()))) {
                    price.setMaxPrice(Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice()));
                    price.setMinPrice(Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice()));
                    m2++;
                } else {
                    price.setMaxPrice(priceM1List.get(m2).getMaxPrice());
                    price.setMinPrice(priceM1List.get(m2).getMinPrice());
                }
            }
            prices.add(price);
            LOGGER.debug(price);
        }
        return prices;
    }
}
