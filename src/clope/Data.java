package clope;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data {

    public static String csvFile = "D:\\data1.txt";
    public static BufferedReader br = null;
    public static String line = " ";
    public static String cvsSplitBy = ",";
    static ArrayList<String> transactions = new ArrayList<>();
    static ArrayList<ArrayList<String>> cluster = new ArrayList<ArrayList<String>>();
    private static Double repulsion=2.6;
    static int numberCluster;

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                transactions.add(row[0]);

            }
            cluster.add(transactions);
            System.out.println(cluster);
            for (String transaction : transactions) {
//                Cluster clusterNew = new Cluster(transaction);
//                clusters.add(clusterNew);
//                double profitFromNewCluster = getProfit(cluster, repulsion);
//                double profitMax = profitFromNewCluster;
            //    System.out.println(transaction);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
