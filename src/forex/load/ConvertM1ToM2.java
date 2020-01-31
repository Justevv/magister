package forex.load;

import java.util.ArrayList;
import java.util.List;

public class ConvertM1ToM2 {
    private List<PriceM2> priceM2s = new ArrayList<>(DataLoading.size / 2);

    public List<PriceM2> convert(List<PriceM1> priceM1List, DataLoading d) {
        int m2;
        for (m2 = 0; m2 < priceM1List.size(); m2++) {
            PriceM2 priceM2 = new PriceM2();
            if ((priceM1List.get(m2).getM1DateValue().getMinutes() % 2) == 0 && (priceM1List.get(m2 + 1).getM1DateValue().getMinutes() % 2) != 0) {
                priceM2.setDateValue(priceM1List.get(m2).getM1DateValue());
                if (priceM1List.get(m2).getM1MaxPrice() > priceM1List.get(m2 + 1).getM1MaxPrice()) {
                    priceM2.setMaxPrice(priceM1List.get(m2).getM1MaxPrice());
                } else {
                    priceM2.setMaxPrice(priceM1List.get(m2 + 1).getM1MaxPrice());
                }
                if (priceM1List.get(m2).getM1MinPrice() < priceM1List.get(m2 + 1).getM1MinPrice()) {
                    priceM2.setMinPrice(priceM1List.get(m2).getM1MinPrice());
                } else {
                    priceM2.setMinPrice(priceM1List.get(m2 + 1).getM1MinPrice());
                }
                m2++;
            } else {
                {
                    priceM2.setDateValue(priceM1List.get(m2).getM1DateValue());
                    priceM2.setMaxPrice(priceM1List.get(m2).getM1MaxPrice());
                    priceM2.setMinPrice(priceM1List.get(m2).getM1MinPrice());
                }
            }
            priceM2s.add(priceM2);
        }
        System.out.println(priceM2s.size());
        return priceM2s;
    }
}
