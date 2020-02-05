package forex.load;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConvertM1ToM2 {
    private List<PriceM2> priceM2s = new ArrayList<>(DataLoading.size / 2);
//    ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage(0.234);

    public List<PriceM2> convert(List<PriceM1> priceM1List) {
        for (int m2 = 0; m2 < priceM1List.size() - 1; m2++) {
            PriceM2 priceM2 = new PriceM2();
            if ((priceM1List.get(m2).getM1DateValue().get(Calendar.MINUTE) % 2) == 0 && (priceM1List.get(m2 + 1).getM1DateValue().get(Calendar.MINUTE) % 2) != 0) {
                priceM2.setDateValue(priceM1List.get(m2).getM1DateValue());
                priceM2.setMaxPrice(Math.max(priceM1List.get(m2).getM1MaxPrice(), priceM1List.get(m2 + 1).getM1MaxPrice()));
                priceM2.setMinPrice(Math.min(priceM1List.get(m2).getM1MinPrice(), priceM1List.get(m2 + 1).getM1MinPrice()));
                m2++;
            } else {
                priceM2.setDateValue(priceM1List.get(m2).getM1DateValue());
                priceM2.setMaxPrice(priceM1List.get(m2).getM1MaxPrice());
                priceM2.setMinPrice(priceM1List.get(m2).getM1MinPrice());
            }
//            priceM2.setEMA(exponentialMovingAverage.average(priceM2.getMaxPrice()));
            priceM2s.add(priceM2);
//            System.out.println(priceM2);
        }
        System.out.println(priceM2s.size());

//        for (PriceM2 priceM2 : priceM2s) {
//            System.out.println(exponentialMovingAverage.average(priceM2.getMaxPrice()));
//        }
        return priceM2s;
    }
}
