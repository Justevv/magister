package forex.load;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertM1ToM2 {
    private static final Logger LOGGER = LogManager.getLogger(ConvertM1ToM2.class);
    private List<Price> prices = new ArrayList<>(DataLoading.size / 2);

    public List<Price> convert(List<Price> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 1; m2++) {
            Price price = new Price();
            price.setDateValue(priceM1List.get(m2).getDateValue());
            if ((priceM1List.get(m2).getDateValue().get(Calendar.MINUTE) % 2) == 0 && (priceM1List.get(m2 + 1).getDateValue().get(Calendar.MINUTE) % 2) != 0) {
                price.setMaxPrice(Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice()));
                price.setMinPrice(Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice()));
                m2++;
            } else {
                price.setMaxPrice(priceM1List.get(m2).getMaxPrice());
                price.setMinPrice(priceM1List.get(m2).getMinPrice());
            }
            prices.add(price);
            LOGGER.debug(price);
        }
        LOGGER.info(prices.size());
        return prices;
    }
}
