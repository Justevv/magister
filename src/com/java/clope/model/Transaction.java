package com.java.clope.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class of transaction
 * Created by Nataly on 01.04.2015.
 */
public class Transaction {
    private List<String> data = new ArrayList<>(); //items of transaction

    /**
     * create a new transaction
     *
     * @param strings the items of transaction
     */
    public Transaction(String... strings) {
        for (String s : strings) {
            this.data.add(s);   //чтение посимвольно транзакций и добавление в data
//            System.out.println(data);
//            System.out.println(Start.i);
//            Start.i++;
        }
//        System.out.println(Start.i);
//        System.out.println(data);
//        Start.i++;
    }

    /**
     * get all items from transaction
     *
     * @return the list with all items of transaction
     */
    public List<String> getAllData() {
        return data;
    }

    @Override
    public String toString() {
        return Arrays.toString(data.toArray());
    }
}
