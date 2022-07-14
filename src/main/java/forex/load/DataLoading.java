package forex.load;

import forex.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataLoading {
    private static final Logger LOGGER = LogManager.getLogger(DataLoading.class);
    private static final String CVS_SPLIT_BY = ",";
    private List<Price> priceM1 = new ArrayList<>(Calculate.countLines);
    private String filterYearString;
    private boolean filter;

    public DataLoading() {
    }

    public DataLoading(String filterYearString, boolean filter) {
        this.filterYearString = filterYearString;
        this.filter = filter;
    }

    public List<Price> run(String csvFile) {
        String line;
        boolean stop = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            LocalDateTime parsingDate = null;
            while ((line = br.readLine()) != null) {
                if (line.contains(filterYearString) || !filter) {
                    stop = true;
                    String[] row = line.split(CVS_SPLIT_BY);
                    parsingDate = LocalDateTime.parse(row[0].concat(" ").concat(row[1]), formatter);
                    priceM1.add(new Price(parsingDate, Float.parseFloat(row[3]), Float.parseFloat(row[4]), Float.parseFloat(row[5])));
                } else if (stop) {
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.fatal(e);
        }
        return priceM1;
    }
}
