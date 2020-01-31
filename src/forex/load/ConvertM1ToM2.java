package forex.load;

import java.util.ArrayList;
import java.util.List;

public class ConvertM1ToM2 {
    public List<PriceM2> priceM2s = new ArrayList<>(DataLoading.size/2);

    public void convert() {
        int i = 0;
        int m2;
        //int maxI = 0;
        DataLoading d = new DataLoading();
        for (m2 = 0; m2 < d.getPriceM1().size(); m2++, i++) {
            PriceM2 priceM2 = new PriceM2();
            if ((d.getPriceM1().get(m2).getM1DateValue().getMinutes() % 2) == 0 && (d.getPriceM1().get(m2 + 1).getM1DateValue().getMinutes() % 2) != 0) {
                priceM2.setDateValue(d.getPriceM1().get(m2).getM1DateValue());
                d.dateValue[i] = d.getPriceM1().get(m2).getM1DateValue();
                //System.out.println(dateValue[i]);
                if (d.getPriceM1().get(m2).getM1MaxPrice() > d.getPriceM1().get(m2 + 1).getM1MaxPrice()) {
                    priceM2.setMaxPrice(d.getPriceM1().get(m2).getM1MaxPrice());
                    d.maxPrice[i] = d.getPriceM1().get(m2).getM1MaxPrice();
                } else {
                    d.maxPrice[i] = d.getPriceM1().get(m2 + 1).getM1MaxPrice();
                    priceM2.setMaxPrice(d.getPriceM1().get(m2 + 1).getM1MaxPrice());
                }
                if (d.getPriceM1().get(m2).getM1MinPrice() < d.getPriceM1().get(m2 + 1).getM1MinPrice()) {
                    priceM2.setMinPrice(d.getPriceM1().get(m2).getM1MinPrice());
                    d.minPrice[i] = d.getPriceM1().get(m2).getM1MinPrice();
                } else {
                    priceM2.setMinPrice(d.getPriceM1().get(m2 + 1).getM1MinPrice());
                    d.minPrice[i] = d.getPriceM1().get(m2 + 1).getM1MinPrice();
                }
                m2++;
            } else {
                {
                    priceM2.setDateValue(d.getPriceM1().get(m2).getM1DateValue());
                    priceM2.setMaxPrice(d.getPriceM1().get(m2).getM1MaxPrice());
                    priceM2.setMinPrice(d.getPriceM1().get(m2).getM1MinPrice());
                    d.dateValue[i] = d.getPriceM1().get(m2).getM1DateValue();
                    d.maxPrice[i] = d.getPriceM1().get(m2).getM1MaxPrice();
                    d.minPrice[i] = d.getPriceM1().get(m2).getM1MinPrice();
//                    System.out.println("NOK"+dateValue[i]);
//                    System.out.println(i);
                }
            }
        }

        d.maxI = i;
//            System.out.println(dateValue[i]);
//            System.out.println(minPrice[i]);
//            System.out.println(maxPrice[i]);
        System.out.println(i);
    }
}
