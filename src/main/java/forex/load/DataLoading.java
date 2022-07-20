package forex.load;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DataLoading {
    private static final String CVS_SPLIT_BY = ",";
    private static final String FILTER_YEAR_STRING = "2015.";
    private static final boolean FILTER = false;


    public List<Price> run(String csvFile) {
        var formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        var content = readFile(csvFile);
        return content
                .parallelStream()
                .filter(x -> x.contains(FILTER_YEAR_STRING) || !FILTER)
                .map(x -> {
                    String[] row = x.split(CVS_SPLIT_BY);
                    return new Price(
                            LocalDateTime.parse(row[0].concat(" ").concat(row[1]), formatter),
                            Float.parseFloat(row[3]),
                            Float.parseFloat(row[4]),
                            Float.parseFloat(row[5]));
                })
//                .filter(x->x.getDateValue().getMonth()== Month.FEBRUARY)
                .toList();
    }

    private List<String> readFile(String path) {
        try (var lines = Files.lines(Paths.get(path))) {
            return lines.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
