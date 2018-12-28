package com.java.clope.model;

import java.util.ArrayList;
import java.util.List;

/**
 * implementation of a CLOPE
 * Created by Nataly on 01.04.2015.
 */
public class Clope {
    private List<Cluster> clusters = new ArrayList<>();

    /**
     * to execute the CLOPE for transactions and repulsion
     *
     * @param transactions the transactions
     * @param repulsion    the repulsion parameter
     * @return the list of clusters
     */
    public List<Cluster> goClope(List<Transaction> transactions, double repulsion) {
        for (Transaction transaction : transactions) {
            Cluster clusterNew = new Cluster(transaction);
            clusters.add(clusterNew);
            double profitFromNewCluster = getProfit(clusters, repulsion);
            double profitMax = profitFromNewCluster;
            clusters.remove(clusterNew);
            for (Cluster cluster : clusters) {
                cluster.addToCluster(transaction);
                double profit = getProfit(clusters, repulsion);
                if (profitMax <= profit) {
                    profitMax = profit;
                } else {
                    cluster.removeTransaction(transaction);
                }
            }
            if (profitMax == profitFromNewCluster) {
                clusters.add(clusterNew);
            }
        }
        return clusters;
    }

    // get profit for current distributed transactions across clusters
    private double getProfit(List<Cluster> clusters, double repulsion) {
        try {
            double profit1 = 0;
            double profit2 = 0;
            for (Cluster cluster : clusters) {
                /*profit1 += cluster.getGradient() * cluster.getCountTransactions();*/
                profit1 += cluster.getHeight() * cluster.getWidth() / Math.pow(cluster.getWidth(), repulsion) * cluster.getCountTransactions();
                profit2 += cluster.getCountTransactions();
            }
            return profit1 / profit2;
        } catch (Exception e) { // some problems with incorrect data
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
