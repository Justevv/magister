package com.java.clope.model;

import java.util.*;

/**
 * class of cluster
 * Created by Nataly on 01.04.2015.
 */
public class Cluster {
    private List<Transaction> transactions = new ArrayList<>(); // transactions on current cluster

    /**
     * create a new cluster with transactions
     *
     * @param transaction the transactions of a new cluster
     */
    public Cluster(Transaction transaction) {
        this.transactions.add(transaction); //добавление новой транзакции
//        System.out.println(transaction);
    }

    /**
     * get height parameter of current cluster
     *
     * @return the height of cluster
     */
    public double getHeight() {
        double clusterHeight = 0;
        for (Integer height : getClusterMap().values()) {
            if (clusterHeight < height) {
                clusterHeight = height;
            }
        }
//        System.out.println(Start.i++);
        return clusterHeight;
    }

    /**
     * get width parameter of current cluster
     *
     * @return the width of cluster
     */
    public double getWidth() {
//        System.out.println(Start.i++);
        return getClusterMap().size();
    }

    /**
     * add a new transaction to the cluster
     *
     * @param transaction the new transaction
     */
    public void addToCluster(Transaction transaction) {
        transactions.add(transaction);
//        System.out.println(Start.i++);
//        System.out.println(transaction);
//        System.out.println(transactions.size());
    }

    /**
     * remove a transaction from cluster
     *
     * @param transaction the transaction is removed
     */
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
//        System.out.println(transaction);
    }

    /**
     * checking for emptiness
     *
     * @return the true if empty
     */
    public boolean isEmpty() {
        return (transactions.size() > 0) ? true : false;
    }

    /**
     * get unique values from all transactions and the number of occurrences in the cluster
     *
     * @return the map with unique value and number of values in the cluster
     */
    private Map<String, Integer> getClusterMap() {
        Map<String, Integer> clusterMap = new HashMap<>();
        for (Transaction transaction : transactions) {
//            System.out.println(transaction);
            for (String string : transaction.getAllData()) {
//                System.out.println(string);
                if (clusterMap.containsKey(string)) {
                    clusterMap.put(string, clusterMap.get(string) + 1);
                } else {
                    clusterMap.put(string, 1);
                }
            }
        }
        return clusterMap;
    }

    /**
     * get count of transactions in the cluster
     *
     * @return the number of transactions
     */
    public double getCountTransactions() {
        return transactions.size();
    }

    @Override
    public String toString() {
//        System.out.println("\n" + "{" + Arrays.toString(transactions.toArray()) + "}");
        return "\n" + "{" + Arrays.toString(transactions.toArray()) + "}";
    }
}