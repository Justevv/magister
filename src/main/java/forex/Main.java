package forex;

import forex.load.ConvertM1ToM2;
import forex.load.ConvertM1ToM3;
import forex.load.DataLoading;
import forex.load.Price;
import forex.processing.GridGeneration;
import forex.processing.Result;

import java.util.List;

public class Main extends Thread {
    private static final String eurUSD1 = "eurUSD1.csv";
    private static final String audUSD1 = "audUSD1.csv";
    private static final String gbpUSD1 = "gbpUSD1.csv";
    private String CSV_FILE;
    private static long startTime;

    public Main(String CSV_FILE) {
        this.CSV_FILE = CSV_FILE;
    }

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();    //время выполнения программы
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
        DataLoading dataLoading = new DataLoading();
        Result result = new Result();
        long timeSpent;
        List<Price> priceM1s = dataLoading.run(CSV_FILE);
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("download выполнялась " + timeSpent + " миллисекунд");
//        convertM1ToM3.convert(priceM1s);
        result.setGridGeneration(gridGeneration);
        gridGeneration.setResult(result);
        gridGeneration.setPriceListM3(convertM1ToM3.convert(priceM1s));
        gridGeneration.process(convertM1ToM2.convert(priceM1s));
        System.out.println("Итог1 " + result.system1Point * 1 + " пунктов");
        System.out.println("Итог2 " + result.system2Point * 1 + " пунктов");
        System.out.println("Итог3 " + result.system3Point * 1 + " пунктов");
        System.out.println("Итог4 " + result.system4Point * 1 + " пунктов");
        System.out.println("Итог5 " + result.system5Point * 1 + " пунктов");
        System.out.println("Итог6 " + result.system6Point * 1 + " пунктов");
        System.out.println("Итог7 " + result.system7Point * 1 + " пунктов");
//        System.out.println("Итог8 " + r.system8Point * 1 + " пунктов");
//        System.out.println("Итог9 " + r.system9Point * 1 + " пунктов");
        System.out.println("Итог классика " + result.systemClassicPoint * 1 + " пунктов");
//        System.out.println(r.system4count);
//        System.out.println(r.classicOpen);
//        System.out.println(r.classicClose);
//        System.out.println("Прибыльные сделки " + r.profitableDeals);
//        System.out.println("Убыточные сделки " + r.unprofitableDeals);
//        for (Grid grid : g.getGrids()) {
//                  System.out.println(grid);
//        }
        System.out.println(gridGeneration.getGrids().size());
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }

}