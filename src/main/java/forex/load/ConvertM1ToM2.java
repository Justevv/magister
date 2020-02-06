package forex.load;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConvertM1ToM2 {
    private List<Price> prices = new ArrayList<>(DataLoading.size / 2);
//    ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage(0.234);

    public List<Price> convert(List<Price> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 1; m2++) {
            Price price = new Price();
            if ((priceM1List.get(m2).getDateValue().get(Calendar.MINUTE) % 2) == 0 && (priceM1List.get(m2 + 1).getDateValue().get(Calendar.MINUTE) % 2) != 0) {
                price.setDateValue(priceM1List.get(m2).getDateValue());
                price.setMaxPrice(Math.max(priceM1List.get(m2).getMaxPrice(), priceM1List.get(m2 + 1).getMaxPrice()));
                price.setMinPrice(Math.min(priceM1List.get(m2).getMinPrice(), priceM1List.get(m2 + 1).getMinPrice()));
                m2++;
            } else {
                price.setDateValue(priceM1List.get(m2).getDateValue());
                price.setMaxPrice(priceM1List.get(m2).getMaxPrice());
                price.setMinPrice(priceM1List.get(m2).getMinPrice());
            }
//            priceM2.setEMA(exponentialMovingAverage.average(priceM2.getMaxPrice()));
            prices.add(price);
//            System.out.println(priceM2);
        }
        System.out.println(prices.size());

//        for (PriceM2 priceM2 : priceM2s) {
//            System.out.println(exponentialMovingAverage.average(priceM2.getMaxPrice()));
//        }
        return prices;
    }
}
