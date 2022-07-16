package forex;

import forex.entity.Order;
import forex.entity.Statistic;
import forex.load.ConvertM1ToM2;
import forex.load.ConvertM1ToM3;
import forex.load.DataLoading;
import forex.load.Price;
import forex.processing.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calculate extends Thread {
    public static int countLines = 400000;    //Размер массивов
    private static final boolean FILTER = true;
    private static final String eurUSD1 = "eurUSD1.csv";
    private static final String audUSD1 = "audUSD1.csv";
    private static final String gbpUSD1 = "gbpUSD1.csv";
    private static final String filterYearString = "2015.";
    private String csvFile;
    private static long startTime;

    public Calculate(String csvFile) {
        this.csvFile = csvFile;
    }

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();    //время выполнения программы
        if (!FILTER) {
            countLines = 4000000;
        }
        Calculate calculateAudUSD1 = new Calculate(audUSD1);
        calculateAudUSD1.start();
        Calculate calculateEurUSD1 = new Calculate(eurUSD1);
        calculateEurUSD1.start();
        Calculate calculateGbpUSD1 = new Calculate(gbpUSD1);
        calculateGbpUSD1.start();
        System.out.println("программа выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
    }

    @Override
    public void run() {
        GridGeneration gridGeneration = new GridGeneration();
        ConvertM1ToM2 convertM1ToM2 = new ConvertM1ToM2();
        ConvertM1ToM3 convertM1ToM3 = new ConvertM1ToM3();
        DataLoading dataLoading = new DataLoading(filterYearString, FILTER);
        Result result = new Result();
        List<Price> priceM1s = dataLoading.run(csvFile);
        System.out.println("download выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
        gridGeneration.setResult(result);
        List<Price> priceM2s = convertM1ToM2.convert(priceM1s);
        System.out.println("convert to M2 выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
        List<Price> priceM3s = convertM1ToM3.convert(priceM1s);
        System.out.println("convert to M3 выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
        gridGeneration.setPriceListM1(priceM1s);
        gridGeneration.setPriceListM3(priceM3s);
        gridGeneration.process(priceM2s);

        var profit = result.getClassicOrders().stream()
                .flatMap(x -> x.getOrders().stream())
                .collect(Collectors.groupingBy(Order::getStrategy, Collectors.summarizingDouble(Order::getProfit)));
        System.out.println(profit);

//        ema(priceM1s);
//        result.getClassicOrders().forEach(System.out::println);
        System.out.println("программа выполнялась " + (System.currentTimeMillis() - startTime) + " миллисекунд");
    }

    void ema(List<Price> list) {
        int count = 15;
        double[] arr = new double[count];

        // ArrayList to Array Conversion
        for (int i = 0; i < count; i++) {
            arr[i] = list.get(i).getMaxPrice();
        }
        ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage();
        try {
            System.out.println(Arrays.toString(exponentialMovingAverage.calculate(arr, 5)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}