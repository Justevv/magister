package forex.load;

import forex.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConvertM1ToM3 extends Converter {
    private static final Logger LOGGER = LogManager.getLogger(ConvertM1ToM3.class);

    public List<Price> convert(List<Price> priceM1List) {
        List<Price> prices = new ArrayList<>((int) (Calculate.countLines * 0.34));
        for (int m2 = 0; m2 < priceM1List.size() - 2; m2++) {
            Price price = new Price();
            price.setDateValue(priceM1List.get(m2).getDateValue());
            var date = priceM1List.get(m2).getDateValue();
            var datePlus2Minutes = priceM1List.get(m2).getDateValue().plusMinutes(2);
            var datePlus2Bar = priceM1List.get(m2 + 2).getDateValue();
            if ((date.getMinute() % 3) == 0 && datePlus2Minutes.equals(datePlus2Bar)) {
                price.setMaxPrice(Math.max(priceM1List.get(m2 + 2).getMaxPrice(), Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice())));
                price.setMinPrice(Math.min(priceM1List.get(m2 + 2).getMinPrice(), Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice())));
                price.setClosePrice(priceM1List.get(m2 + 2).getClosePrice());
                m2 += 2;
            } else {
                settingCalendarPlus1(priceM1List, m2);
                if (date.getMinute() % 3 == 0 && datePlus2Minutes.equals(datePlus1Bar) ||
                        datePlus1Minutes.equals(datePlus1Bar)) {
                    m2 = setPriceTwoCoincidences(priceM1List, m2, price);
                } else {
                    setPriceOneCoincidences(priceM1List.get(m2), price);
                }
            }
            prices.add(price);
            LOGGER.debug(price);
        }
        return prices;
    }


}
