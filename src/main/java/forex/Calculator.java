package forex;

import forex.checker.M3Checker;
import forex.entity.Order;
import forex.entity.Statistic;
import forex.entity.Strategy;
import forex.load.*;
import forex.processing.GridGenerator;
import forex.processing.GridService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Calculator implements CommandLineRunner {

    private static final String EUR_USD_1 = "eurUSD1.csv";
    private static final String AUD_USD_1 = "audUSD1.csv";
    private static final String GBP_USD_1 = "gbpUSD1.csv";
    private static final List<String> currencies = List.of(EUR_USD_1);
    //    private static final List<String> currencies = List.of(EUR_USD_1, AUD_USD_1, GBP_USD_1);
    @Autowired
    private GridService gridService;
    @Autowired
    private DataLoading dataLoading;
    @Autowired
    private ConvertM1ToM2 convertM1ToM2;
    @Autowired
    private ConvertM1ToM3 convertM1ToM3;
    @Autowired
    private ConvertM1ToM2V2 convertM1ToM2V2;
    @Autowired
    private ConvertM1ToM3V2 convertM1ToM3V2;
    @Autowired
    private GridGenerator gridGenerator;
    private long startTime;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {
        startTime = System.currentTimeMillis();
        currencies.forEach(this::run);
        log.info("Program execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
    }

    @SneakyThrows
    public void run(String csvFile) {
        List<Price> priceM1s = dataLoading.run(csvFile);
        log.info("Download execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
        convertM1ToM2V2.setPriceM1List(priceM1s);
        convertM1ToM3V2.setPriceM1List(priceM1s);
        var priceM2 = executorService.submit(convertM1ToM2V2);
        var priceM3 = executorService.submit(convertM1ToM3V2);
        gridGenerator.process(priceM2.get(), priceM3.get());
        log.info("generation grid execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
        executorService.shutdown();

        var profit = gridService.getOrders().stream()
                .flatMap(x -> x.getOrders().stream())
                .collect(Collectors.groupingBy(x -> new Statistic(x.getStrategy(), x.getOpenTime().getYear()), Collectors.summarizingDouble(Order::getProfit)));
        log.info("Current currency is {}", csvFile);
//        printStatistic(profit);
        var pr = profit.values().stream().mapToDouble(DoubleSummaryStatistics::getSum).sum();
        log.info("Current currency profit is {}", pr);
        var strategyProfit = gridService.getOrders().stream()
                .flatMap(x -> x.getOrders().stream())
                .collect(Collectors.groupingBy(Order::getStrategy, Collectors.summarizingDouble(Order::getProfit)));
        printStrategyStatistic(strategyProfit);
//        gridService.getOrders().forEach(System.out::println);
        log.info("Currency execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
    }

    private void printStrategyStatistic(Map<Strategy, DoubleSummaryStatistics> strategyProfit) {
        strategyProfit.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> log.debug("Strategy {}", x));
    }

    private void printStatistic(Map<Statistic, DoubleSummaryStatistics> profit) {
        profit.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> log.debug("Statistic {}", x));
    }
}
