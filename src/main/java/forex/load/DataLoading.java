package forex.load;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataLoading {
    private static final String CVS_SPLIT_BY = ",";
    private final String filterYearString;
    private final boolean filter;


    public DataLoading(String filterYearString, boolean filter) {
        this.filterYearString = filterYearString;
        this.filter = filter;
    }

    public List<Price> run(String csvFile) {
        var formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        var content = readFile(csvFile);
        return content
                .parallelStream()
                .filter(x -> x.contains(filterYearString) || !filter)
                .map(x -> {
                    String[] row = x.split(CVS_SPLIT_BY);
                    return new Price(
                            LocalDateTime.parse(row[0].concat(" ").concat(row[1]), formatter),
                            Float.parseFloat(row[3]),
                            Float.parseFloat(row[4]),
                            Float.parseFloat(row[5]));
                })
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
