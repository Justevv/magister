package forex;

import forex.entity.Order;
import forex.entity.Statistic;
import forex.load.ConvertM1ToM2;
import forex.load.ConvertM1ToM3;
import forex.load.DataLoading;
import forex.load.Price;
import forex.processing.GridGenerator;
import forex.processing.GridService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Calculator implements CommandLineRunner {

    private static final String EUR_USD_1 = "eurUSD1.csv";
    private static final String AUD_USD_1 = "audUSD1.csv";
    private static final String GBP_USD_1 = "gbpUSD1.csv";
    private static final List<String> currencies = List.of(EUR_USD_1);
    @Autowired
    private GridService gridService;
    @Autowired
    private DataLoading dataLoading;
    @Autowired
    private ConvertM1ToM2 convertM1ToM2;
    @Autowired
    private ConvertM1ToM3 convertM1ToM3;
    @Autowired
    private GridGenerator gridGenerator;
    private long startTime;

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {
        startTime = System.currentTimeMillis();
        currencies.forEach(this::run);
        log.info("Program execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
    }

    public void run(String csvFile) {
        List<Price> priceM1s = dataLoading.run(csvFile);
        log.info("Download execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
        List<Price> priceM2s = convertM1ToM2.convert(priceM1s);
        log.info("convert to M2 execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
        List<Price> priceM3s = convertM1ToM3.convert(priceM1s);
        log.info("convert to M3 execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
        gridGenerator.process(priceM2s, priceM3s);

        var profit = gridService.getOrders().stream()
                .flatMap(x -> x.getOrders().stream())
                .collect(Collectors.groupingBy(x -> new Statistic(x.getStrategy(), x.getOpenTime().getYear()), Collectors.summarizingDouble(Order::getProfit)));
        log.info("Current currency is {}", csvFile);
        profit.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> log.debug("Statistic {}", x));
        var pr = profit.values().stream().mapToDouble(DoubleSummaryStatistics::getSum).sum();
        log.info("Current currency profit is {}", pr);

//        gridService.getOrders().forEach(System.out::println);
        log.info("Currency execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
    }
}
