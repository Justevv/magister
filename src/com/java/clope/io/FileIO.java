package com.java.clope.io;

import com.java.clope.model.Cluster;
import com.java.clope.model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class for input/output data from/to files
 * Created by Nataly on 03.04.2015.
 */
public class FileIO implements Reader, Writer {
    private String filePath;

    /**
     * create a new file io object
     *
     * @param path the path to file
     */
    public FileIO(String path) {
        filePath = path;
    }

    @Override
    public List<Transaction> read() {
        List<Transaction> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath ));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(new Transaction(line.split(" ")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * not implemented
     *
     * @param clusters
     */
    @Override
    public void write(List<Cluster> clusters) {
    }
}
