package forex;

import forex.entity.Order;
import forex.entity.Statistic;
import forex.load.ConvertM1ToM2;
import forex.load.ConvertM1ToM3;
import forex.load.DataLoading;
import forex.load.Price;
import forex.processing.GridGeneration;
import forex.processing.Result;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculate extends Thread {
    private static final boolean FILTER = true;
    private static final String EUR_USD_1 = "eurUSD1.csv";
    private static final String AUD_USD_1 = "audUSD1.csv";
    private static final String GBP_USD_1 = "gbpUSD1.csv";
    private static final String FILTER_YEAR_STRING = "2015.";
    private final String csvFile;
    private static long startTime;

    public Calculate(String csvFile) {
        this.csvFile = csvFile;
    }

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();    //время выполнения программы
        Calculate calculateAudUSD1 = new Calculate(AUD_USD_1);
        Calculate calculateEurUSD1 = new Calculate(EUR_USD_1);
        Calculate calculateGbpUSD1 = new Calculate(GBP_USD_1);
        calculateAudUSD1.start();
        calculateEurUSD1.start();
        calculateGbpUSD1.start();
        System.out.println("программа выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
    }

    @Override
    public void run() {
        GridGeneration gridGeneration = new GridGeneration();
        ConvertM1ToM2 convertM1ToM2 = new ConvertM1ToM2();
        ConvertM1ToM3 convertM1ToM3 = new ConvertM1ToM3();
        DataLoading dataLoading = new DataLoading(FILTER_YEAR_STRING, FILTER);
        Result result = new Result();
        List<Price> priceM1s = dataLoading.run(csvFile);
        System.out.println("download выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
        gridGeneration.setResult(result);
        List<Price> priceM2s = convertM1ToM2.convert(priceM1s);
        System.out.println("convert to M2 выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
        List<Price> priceM3s = convertM1ToM3.convert(priceM1s);
        System.out.println("convert to M3 выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
        gridGeneration.setPriceListM3(priceM3s);
        gridGeneration.process(priceM2s);

        var profit = result.getOrders().stream()
                .flatMap(x -> x.getOrders().stream())
                .collect(Collectors.groupingBy(x -> new Statistic(x.getStrategy(), x.getOpenTime().getYear()), Collectors.summarizingDouble(Order::getProfit)));
        System.out.println(csvFile);
        profit.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);
        var pr = profit.values().stream().mapToDouble(DoubleSummaryStatistics::getSum).sum();
        System.out.println(pr);

//        result.getOrders().forEach(System.out::println);
        System.out.println("программа выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
    }

}