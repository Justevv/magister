package com.java.clope.io;

import com.java.clope.model.Cluster;

import java.util.List;

/**
 * interface for writer functionality
 * Created by Nataly on 03.04.2015.
 */
public interface Writer {
    void write(List<Cluster> clusters);
}
