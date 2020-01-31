package forex.load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DataLoading {
    public static String csvFile = "D:\\audUSD12.csv";
    public static String line = " ";
    public static String cvsSplitBy = ",";
    public static int size = 400000;    //Размер массивов
    private List<PriceM1> priceM1 = new ArrayList<>(size);
    public static Date[] dateValue = new Date[size];  //Массив дат
    public static Double[] minPrice = new Double[size];  //Массив минимума
    public static Double[] maxPrice = new Double[size];  //Массив максимума
    public static int maxI = 0;
    public static Date parsingDate = new Date();
    public static Calendar cal = Calendar.getInstance();
    //public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm z");

//    public List<PriceM1> getPriceM1() {
//        return priceM1;
//    }
//
//    public void setPriceM1(List<PriceM1> priceM1) {
//        this.priceM1 = priceM1;
//    }

    public  List<PriceM1> run() {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm");
                String dateTime = row[0] + " " + row[1];
                // System.out.print("Строка " + str + " распаршена как ");
                try {
                    parsingDate = ft.parse(dateTime);
                    //  Calendar parsingDate = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.ENGLISH);
                    cal.setTime(dateFormat.parse(dateTime));
                    // minute = cal.get(Calendar.MINUTE);
                    // System.out.println("Long : "+ parsingDate.getTime());
//                    System.out.println(cal.get(Calendar.YEAR));
//                    System.out.println(cal.get(Calendar.MONTH));
//                    System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//                    System.out.println(parsingDate.get(Calendar.YEAR));
//                    System.out.println(parsingDate.get(Calendar.MONTH)+1);
//                    System.out.println(parsingDate.get(Calendar.DAY_OF_WEEK)-1);
//                     System.out.println(parsingDate);
                } catch (ParseException e) {
                    System.out.println("Нераспаршена с помощью " + ft);
                }
                if (cal.get(Calendar.YEAR) >= 2018
//                        && cal.get(Calendar.MONTH) >= 5
//                        && cal.get(Calendar.DAY_OF_MONTH) >= 25
                ) {
                    //System.out.println(cal.get(Calendar.DAY_OF_MONTH));
//                    if (parsingDate.getTime() == previusMinute + 120000) {
//                        //System.out.println("eroor");
//                        m1DateValue[m1] = parsingDate;
//                        m1MaxPrice[m1] = Double.valueOf(row[3]);
//                        m1MinPrice[m1] = Double.valueOf(row[4]);
//                        m1++;
//                        // temp++;
//                    }
                    //System.out.println(parsingDate.getTime());
                    priceM1.add(new PriceM1(parsingDate, Double.valueOf(row[3]), Double.valueOf(row[4])));
//                    if (m1 < 1) {
//                        System.out.println(m1);
//                        //System.out.println(i);
//                    }
                    // previusMinute = m1DateValue[m1].getTime();
//                    m1++;
//                System.out.println(
//                        row[0]
//                                + "  " + row[1]
//                                + "  " + row[2]
//                                + "  " + row[3]
//                                + "  " + row[4]
//                                + "  " + row[5]
//                                + "  " + row[6]);//Вывод входных данных
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(m1max);
//        System.out.println(m1);
        return priceM1;
    }
}
