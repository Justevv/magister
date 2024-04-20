package forex.load;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ConvertM1ToM3 extends Converter {

    public List<Price> convert(List<Price> priceM1List) {
        List<Price> prices = new ArrayList<>((int) (priceM1List.size() * 0.34));
        for (int m2 = 0; m2 < priceM1List.size() - 2; m2++) {
            Price price;
            var date = priceM1List.get(m2).getDateValue();
            var datePlus2Minutes = priceM1List.get(m2).getDateValue().plusMinutes(2);
            var datePlus2Bar = priceM1List.get(m2 + 2).getDateValue();
            if ((date.getMinute() % 3) == 0 && datePlus2Minutes.equals(datePlus2Bar)) {
                price = new Price();
                price.setDateValue(priceM1List.get(m2).getDateValue());
                List<Price> priceList = priceM1List.subList(m2, m2+3);
                int maxPrice = priceList.stream()
                        .mapToInt(Price::getMaxPrice)
                        .max()
                        .orElse(0);
                price.setMaxPrice(maxPrice);
                price.setMinPrice(Math.min(priceM1List.get(m2 + 2).getMinPrice(), Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice())));
                price.setClosePrice(priceM1List.get(m2 + 2).getClosePrice());
                m2 += 2;
            } else {
                settingCalendarPlus1(priceM1List, m2);
                if (date.getMinute() % 3 == 0 && datePlus2Minutes.equals(datePlus1Bar) ||
                        datePlus1Minutes.equals(datePlus1Bar)) {
                    price = setPriceTwoCoincidences(priceM1List.get(m2), priceM1List.get(m2 + 1));
                    m2++;
                } else {
                    price = setPriceOneCoincidences(priceM1List.get(m2));
                }
            }
            prices.add(price);
            log.trace("Price {}", price);
        }
        log.info("Price count {}", prices.size());
        return prices;
    }


}
