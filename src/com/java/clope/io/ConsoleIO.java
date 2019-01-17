package com.java.clope.io;

import com.java.clope.model.Cluster;
import com.java.clope.model.Transaction;

import java.util.List;

/**
 * class for input/output from/in console
 * Created by Nataly on 03.04.2015.
 */
public class ConsoleIO implements Reader, Writer {

    /**
     * not implemented
     *
     * @return
     */
    @Override
    public List<Transaction> read() {
        return null;
    }

    @Override
    public void write(List<Cluster> clusters) {
        System.out.println(clusters.size());
        System.out.println(clusters.toString());
    }
}