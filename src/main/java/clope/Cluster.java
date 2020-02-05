package clope;

import com.java.clope.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * class of cluster
 * Created by Nataly on 01.04.2015.
 */
public class Cluster {
    private List<Transaction> transaction = new ArrayList<>(); // transactions on current cluster

    /**
     * create a new cluster with transactions
     *
     * @param transaction the transactions of a new cluster
     */
    public Cluster(Transaction transaction) {
        this.transaction.add(transaction);

    }
}