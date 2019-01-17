package com.java.clope.run;

import com.java.clope.io.ConsoleIO;
import com.java.clope.io.FileIO;
import com.java.clope.model.Clope;

/**
 * Created by Nataly on 01.04.2015.
 */
public class Start {
    public static void main(String[] o) {
        long startTime = System.currentTimeMillis();
        long timeSpent;
        String[] param = new String[2];
        param[0] = "D:\\export2.txt";
        param[1] = "2.6";
        new ConsoleIO().write(new Clope().goClope(new FileIO(param[0]).read(), Double.parseDouble(param[1])));
//        System.out.println(Cluster.transactions.size());
        timeSpent = System.currentTimeMillis() - startTime;//время выполнения программы
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }
}