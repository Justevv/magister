package forex.load;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConvertM1ToM2 extends Converter {

    public List<Price> convert(List<Price> priceM1List) {
        List<Price> prices = new ArrayList<>((int) (priceM1List.size() * 0.51));
        for (int m2 = 0; m2 < priceM1List.size() - 1; m2++) {
            Price price;
            var date = priceM1List.get(m2).getDateValue();
            if (date.getMinute() % 2 == 0 && checkBar(priceM1List.get(m2), priceM1List.get(m2 + 1))) {
                price = setPriceTwoCoincidences(priceM1List.get(m2), priceM1List.get(m2 + 1));
                m2++;
            } else {
                price = setPriceOneCoincidences(priceM1List.get(m2));
            }
            prices.add(price);
            log.trace("Price {}", price);
        }
        log.info("Price count {}", prices.size());
        return prices;
    }

}
