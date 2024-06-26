package forex.load;

import forex.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class DataLoading {
    private static final String CVS_SPLIT_BY = ",";
    private static final String FILTER_YEAR_STRING = "2015.";
    private static final boolean FILTER = false;


    public List<Price> run(String csvFile) {
        var formatter = DateTimeFormatter.ofPattern("yyyy.MM.ddHH:mm");
        var content = readFile(csvFile);
        return content
                .parallelStream()
//                .filter(x -> x.contains(FILTER_YEAR_STRING) || !FILTER)
                .map(x -> {
                    String[] row = x.split(CVS_SPLIT_BY);
                    return new Price(
                            LocalDateTime.parse(row[0].concat(row[1]), formatter),
                            (Integer.parseInt(row[3].replace(".", ""))),
                            (Integer.parseInt(row[4].replace(".", ""))),
                            (Integer.parseInt(row[5].replace(".", ""))));
                })
//                .filter(x->x.getDateValue().getMonth()== Month.FEBRUARY)
                .toList();
    }

    private List<String> readFile(String path) {
        var startTime = System.currentTimeMillis();
        try (var lines = Files.lines(Paths.get(path))) {
            log.info("Read file execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
            return lines.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
