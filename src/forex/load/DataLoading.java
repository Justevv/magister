package forex.load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DataLoading {
    private static String csvFile = "audUSD1.csv";
    private static String line = " ";
    private static String cvsSplitBy = ",";
    public static int size = 400000;    //Размер массивов
    private List<PriceM1> priceM1 = new ArrayList<>(size);
    private Calendar cal = Calendar.getInstance();

    public List<PriceM1> run() {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Date parsingDate = null;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm");
                String dateTime = row[0] + " " + row[1];
                try {
                    parsingDate = ft.parse(dateTime);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.ENGLISH);
                    cal.setTime(dateFormat.parse(dateTime));
                } catch (ParseException e) {
                    System.out.println("Нераспаршена с помощью " + ft);
                }
                if (cal.get(Calendar.YEAR) >= 2018
//                        && cal.get(Calendar.MONTH) >= 5
//                        && cal.get(Calendar.DAY_OF_MONTH) >= 25
                ) {
                    priceM1.add(new PriceM1(parsingDate, Float.parseFloat(row[3]), Float.parseFloat(row[4])));
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
        return priceM1;
    }
}
