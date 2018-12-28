package com.java.clope.run;

import com.java.clope.io.ConsoleIO;
import com.java.clope.io.FileIO;
import com.java.clope.model.Clope;

/**
 * Created by Nataly on 01.04.2015.
 */
public class Start {
    public static void main(String[] o) {
        new ConsoleIO().write(new Clope().goClope(new FileIO(o[0]).read(), Double.parseDouble(o[1])));
        System.out.println("run");
    }
}