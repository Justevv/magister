package forex.load;

import java.util.ArrayList;
import java.util.List;

public class ConvertM1ToM2 {
    public List<PriceM2> priceM2s = new ArrayList<>(DataLoading.size / 2);

    public List<PriceM2> convert(List<PriceM1> priceM1List) {
        int i = 0;
        int m2;
        DataLoading d = new DataLoading();
        for (m2 = 0; m2 < priceM1List.size(); m2++, i++) {
            PriceM2 priceM2 = new PriceM2();
            if ((priceM1List.get(m2).getM1DateValue().getMinutes() % 2) == 0 && (priceM1List.get(m2 + 1).getM1DateValue().getMinutes() % 2) != 0) {
                priceM2.setDateValue(priceM1List.get(m2).getM1DateValue());
                d.dateValue[i] = priceM1List.get(m2).getM1DateValue();
                //System.out.println(dateValue[i]);
                if (priceM1List.get(m2).getM1MaxPrice() > priceM1List.get(m2 + 1).getM1MaxPrice()) {
                    priceM2.setMaxPrice(priceM1List.get(m2).getM1MaxPrice());
                    d.maxPrice[i] = priceM1List.get(m2).getM1MaxPrice();
                } else {
                    d.maxPrice[i] = priceM1List.get(m2 + 1).getM1MaxPrice();
                    priceM2.setMaxPrice(priceM1List.get(m2 + 1).getM1MaxPrice());
                }
                if (priceM1List.get(m2).getM1MinPrice() < priceM1List.get(m2 + 1).getM1MinPrice()) {
                    priceM2.setMinPrice(priceM1List.get(m2).getM1MinPrice());
                    d.minPrice[i] = priceM1List.get(m2).getM1MinPrice();
                } else {
                    priceM2.setMinPrice(priceM1List.get(m2 + 1).getM1MinPrice());
                    d.minPrice[i] = priceM1List.get(m2 + 1).getM1MinPrice();
                }
                m2++;
            } else {
                {
                    priceM2.setDateValue(priceM1List.get(m2).getM1DateValue());
                    priceM2.setMaxPrice(priceM1List.get(m2).getM1MaxPrice());
                    priceM2.setMinPrice(priceM1List.get(m2).getM1MinPrice());
                    d.dateValue[i] =priceM1List.get(m2).getM1DateValue();
                    d.maxPrice[i] = priceM1List.get(m2).getM1MaxPrice();
                    d.minPrice[i] = priceM1List.get(m2).getM1MinPrice();
//                    System.out.println("NOK"+dateValue[i]);
//                    System.out.println(i);
                }
            }
            priceM2s.add(priceM2);
        }
        d.maxI = i;
//            System.out.println(dateValue[i]);
//            System.out.println(minPrice[i]);
//            System.out.println(maxPrice[i]);
        System.out.println(i);
        return priceM2s;
    }
}
