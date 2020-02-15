package forex.load;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DataLoading {
    private static final Logger LOGGER = LogManager.getLogger(DataLoading.class);
    private static final String CSV_FILE = "audUSD1.csv";
    private static final String CVS_SPLIT_BY = ",";
    private static final boolean filter = true;
    public static int size = 400000;    //Размер массивов
    private static final String FILTER_YEAR_STRING = "2018.";
    private List<Price> priceM1 = new ArrayList<>(size);

    public List<Price> run() {
        String line;
        boolean stop = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH);
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                if (line.contains(FILTER_YEAR_STRING) || !filter) {
                    stop = true;
                    String[] row = line.split(CVS_SPLIT_BY);
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(dateFormat.parse(row[0].concat(" ").concat(row[1])));
                    } catch (ParseException e) {
                        System.out.println("Нераспаршена с помощью " + dateFormat);
                    }
                    priceM1.add(new Price(calendar, Float.parseFloat(row[3]), Float.parseFloat(row[4])));
                } else if (stop) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceM1;
    }
}
