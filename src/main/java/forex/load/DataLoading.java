package forex.load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataLoading {
    private static String csvFile = "audUSD1.csv";
    private static String cvsSplitBy = ",";
    public static int size = 400000;    //Размер массивов
    private List<PriceM1> priceM1 = new ArrayList<>(size);

    public List<PriceM1> run() {
        String line;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.ENGLISH);
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                Calendar calendar = Calendar.getInstance();
                String[] row = line.split(cvsSplitBy);
                try {
                    calendar.setTime(dateFormat.parse(row[0].concat(" ").concat(row[1])));
                } catch (ParseException e) {
                    System.out.println("Нераспаршена с помощью " + dateFormat);
                }
                if (calendar.get(Calendar.YEAR) >= 2018
//                        && cal.get(Calendar.MONTH) >= 5
//                        && cal.get(Calendar.DAY_OF_MONTH) >= 25
                ) {
                    priceM1.add(new PriceM1(calendar, Float.parseFloat(row[3]), Float.parseFloat(row[4])));
//                System.out.println(
//                        row[0]
//                                + "  " + row[1]
//                                + "  " + row[2]
//                                + "  " + row[3]
//                                + "  " + row[4]
//                                + "  " + row[5]
//                                + "  " + row[6]);//Вывод входных данных
                }
//                if (calendar.get(Calendar.YEAR) >= 2018) {
//                    break;
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceM1;
    }
}
