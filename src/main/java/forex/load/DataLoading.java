package forex.load;

import forex.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataLoading {
    private static final Logger LOGGER = LogManager.getLogger(DataLoading.class);
    private static final String CVS_SPLIT_BY = ",";
    private List<Price> priceM1 = new ArrayList<>(Calculate.size);
    private String filterYearString;
    private boolean filter;

    public DataLoading() {
    }

    public DataLoading(String filterYearString, boolean filter) {
        this.filterYearString = filterYearString;
        this.filter = filter;
    }

    public List<Price> run(String CSV_FILE) {
        String line;
        boolean stop = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH);
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            Date parsingDate = null;
            while ((line = br.readLine()) != null) {
                if (line.contains(filterYearString) || !filter) {
                    stop = true;
                    String[] row = line.split(CVS_SPLIT_BY);
                    try {
                        parsingDate = dateFormat.parse(row[0].concat(" ").concat(row[1]));
                    } catch (ParseException e) {
                        System.out.println("Нераспаршена с помощью " + dateFormat);
                    }
                    priceM1.add(new Price(parsingDate, Float.parseFloat(row[3]), Float.parseFloat(row[4])));
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
