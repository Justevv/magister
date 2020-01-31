package forex;

import forex.load.ConvertM1ToM2;
import forex.load.DataLoading;
import forex.processing.GridGeneration;
import forex.processing.Result;

import static forex.processing.Result.*;

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
        d.run();
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
        c.convert();
        g.process();
        System.out.println("Итог1 " + system1Point * 1 + " пунктов");
        System.out.println("Итог2 " + system2Point * 1 + " пунктов");
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
        for (double number : r.pips) {
            if (number != 0) {
                temp = temp + number;
                //  System.out.println(temp);
                //  System.out.println(number);
            }
        }
//                    for (int i=0;i<5000;i++){
//                        double temp=
//                    }
        for (int i = 0; i < 150; i++) {
            temp1 = MOFibo38[i];
            temp2 = MPFibo38[i];
            temp3 = MPFibo61[i];
//            System.out.print(temp1 + " ");
//            System.out.print(temp2 + " ");
//            System.out.println(temp3);
        }
       // for (int number : TakeProfit)
        //for (int i = 0; i < 15; i++){
            System.out.println(TakeProfit);

       // }
        System.out.println("fuck");
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }

}