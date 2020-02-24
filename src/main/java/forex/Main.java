package forex;

import forex.load.ConvertM1ToM2;
import forex.load.ConvertM1ToM3;
import forex.load.DataLoading;
import forex.load.Price;
import forex.processing.GridGeneration;
import forex.processing.Result;

import java.util.List;

public class Main extends Thread {
    public static int size = 400000;    //Размер массивов
    private static final boolean filter = false;
    private static final String eurUSD1 = "eurUSD1.csv";
    private static final String audUSD1 = "audUSD1.csv";
    private static final String gbpUSD1 = "gbpUSD1.csv";
    private static final String filterYearString = "2018.";
    private String CSV_FILE;
    private static long startTime;

    public Main(String CSV_FILE) {
        this.CSV_FILE = CSV_FILE;
    }

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();    //время выполнения программы
        if (!filter) {
            size = 4000000;
        }
        Main main1 = new Main(audUSD1);
        main1.start();
        Main main = new Main(eurUSD1);
        main.start();
        Main main2 = new Main(gbpUSD1);
        main2.start();
        long timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }

    @Override
    public void run() {
//        long startTime = System.currentTimeMillis();    //время выполнения программы
        GridGeneration gridGeneration = new GridGeneration();
        ConvertM1ToM2 convertM1ToM2 = new ConvertM1ToM2();
        ConvertM1ToM3 convertM1ToM3 = new ConvertM1ToM3();
        DataLoading dataLoading = new DataLoading(filterYearString, filter);
        Result result = new Result();
        long timeSpent;
        List<Price> priceM1s = dataLoading.run(CSV_FILE);
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("download выполнялась " + timeSpent + " миллисекунд");
        gridGeneration.setResult(result);
        List<Price> priceM2s = convertM1ToM2.convert(priceM1s);
        System.out.println("convert to M2 выполнялась " + timeSpent + " миллисекунд");
        List<Price> priceM3s = convertM1ToM3.convert(priceM1s);
        System.out.println("convert to M3 выполнялась " + timeSpent + " миллисекунд");
        gridGeneration.setPriceListM3(priceM3s);
        gridGeneration.process(priceM2s);
        System.out.println("Итог1 " + result.system1Point * 1 + " пунктов");
        System.out.println("Итог2 " + result.system2Point * 1 + " пунктов");
        System.out.println("Итог3 " + result.system3Point * 1 + " пунктов");
        System.out.println("Итог4 " + result.system4Point * 1 + " пунктов");
        System.out.println("Итог5 " + result.system5Point * 1 + " пунктов");
        System.out.println("Итог6 " + result.system6Point * 1 + " пунктов");
        System.out.println("Итог7 " + result.system7Point * 1 + " пунктов");
        System.out.println("Итог классика " + result.systemClassicPoint * 1 + " пунктов");
        float allResult = result.system1Point + result.system2Point + result.system3Point +
                result.system4Point + result.system5Point + result.system6Point + result.system7Point;
        System.out.println("All result " + allResult);
        System.out.println(gridGeneration.getGrids().size());
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }

}