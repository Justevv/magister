package forex;

import forex.load.ConvertM1ToM2;
import forex.load.DataLoading;
import forex.load.PriceM1;
import forex.processing.GridGeneration;
import forex.processing.Result;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();    //время выполнения программы
        GridGeneration g = new GridGeneration();
        ConvertM1ToM2 c = new ConvertM1ToM2();
        DataLoading d = new DataLoading();
        Result r = new Result();
        long timeSpent;
        double temp = 0;
        int temp1 = 0;
        int temp2 = 0;
        int temp3 = 0;
        List<PriceM1> priceM1s = d.run();
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
//        c.convert(d.run());
        r.setGridGeneration(g);
        g.setResult(r);
        g.process(priceM1s);
        System.out.println("Итог1 " + r.system1Point * 1 + " пунктов");
        System.out.println("Итог2 " + r.system2Point * 1 + " пунктов");
        System.out.println("Итог3 " + r.system3Point * 1 + " пунктов");
        System.out.println("Итог4 " + r.system4Point * 1 + " пунктов");
        System.out.println("Итог5 " + r.system5Point * 1 + " пунктов");
        System.out.println("Итог6 " + r.system6Point * 1 + " пунктов");
        System.out.println("Итог7 " + r.system7Point * 1 + " пунктов");
//        System.out.println("Итог8 " + r.system8Point * 1 + " пунктов");
//        System.out.println("Итог9 " + r.system9Point * 1 + " пунктов");
        System.out.println("Итог классика " + r.systemClassicPoint * 1 + " пунктов");
//        System.out.println(r.system4count);
//        System.out.println(r.classicOpen);
//        System.out.println(r.classicClose);
//        System.out.println("Прибыльные сделки " + r.profitableDeals);
//        System.out.println("Убыточные сделки " + r.unprofitableDeals);
//        for (Grid grid : g.getGrids()) {
//                  System.out.println(grid);
//        }
        System.out.println(g.getGrids().size());
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }

}