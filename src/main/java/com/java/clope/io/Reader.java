package com.java.clope.io;

import com.java.clope.model.Transaction;

import java.util.List;

/**
 * interface for reader functionality
 * Created by Nataly on 03.04.2015.
 */
public interface Reader {
    List<Transaction> read();
}