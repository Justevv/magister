package forex.load;

public class ConvertM1ToM2 {
    public static void convert() {
        int i = 0;
        int m2;
        //int maxI = 0;
        DataLoading d = new DataLoading();
        for (m2 = 0; m2 < d.m1; m2++) {
            if ((d.m1DateValue[m2].getMinutes() % 2) == 0 && (d.m1DateValue[m2 + 1].getMinutes() % 2) != 0) {
                d.dateValue[i] = d.m1DateValue[m2];
                //System.out.println(dateValue[i]);
                if (d.m1MaxPrice[m2] > d.m1MaxPrice[m2 + 1]) {
                    d.maxPrice[i] = d.m1MaxPrice[m2];
                } else {
                    d.maxPrice[i] = d.m1MaxPrice[m2 + 1];
                }
                if (d.m1MinPrice[m2] < d.m1MinPrice[m2 + 1]) {
                    d.minPrice[i] = d.m1MinPrice[m2];
                } else {
                    d.minPrice[i] = d.m1MinPrice[m2 + 1];
                }
                m2++;
            } else {
                {
                    d.dateValue[i] = d.m1DateValue[m2];
                    d.maxPrice[i] = d.m1MaxPrice[m2];
                    d.minPrice[i] = d.m1MinPrice[m2];
//                    System.out.println("NOK"+dateValue[i]);
//                    System.out.println(i);
                }
            }
            i++;
        }

        d.maxI =i;
//            System.out.println(dateValue[i]);
//            System.out.println(minPrice[i]);
//            System.out.println(maxPrice[i]);
         System.out.println(i);
    }
}
