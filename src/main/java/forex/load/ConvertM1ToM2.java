package forex.load;

import forex.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConvertM1ToM2 extends Converter {
    private static final Logger LOGGER = LogManager.getLogger(ConvertM1ToM2.class);
    private List<Price> prices = new ArrayList<>((int) (Calculate.countLines * 0.51));

    public List<Price> convert(List<Price> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 1; m2++) {
            Price price = new Price();
            price.setDateValue(priceM1List.get(m2).getDateValue());
            var date = priceM1List.get(m2).getDateValue();
            settingCalendarPlus1(priceM1List, m2);
            if ((date.getMinute() % 2) == 0 && datePlus1Minutes.isEqual(datePlus1Bar)) {
                m2 = setPriceTwoCoincidences(priceM1List, m2, price);
            } else {
                setPriceOneCoincidences(priceM1List, m2, price);
            }
            prices.add(price);
            LOGGER.debug(price);
        }
        LOGGER.info(prices.size());
        return prices;
    }

}
