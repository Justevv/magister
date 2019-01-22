//package clope;
//
//import java.util.List;
//
//public class Clope {
//    namespace AlgorithmClope
//    {
//        public class ClusterData
//        {
//            public Dictionary<int, int> OccDict { get; set;
//        } = new Dictionary<int, int>();
//            public int Square { get; set; }
//            public int Width { get; set; }
//            public int Size { get; set; }
//            public void AddTransaction(List<int>
//                                               transactions)
//            {
//                for (int i = 0; i < transactions.Count; i++)
//                {
//                    if
//                    (!OccDict.ContainsKey(transactions[i]))
//                        OccDict.Add(transactions[i], 1);
//                    else
//                        OccDict[transactions[i]] += 1;
//                }
//                Width = OccDict.Count;
//                Square += transactions.Count;
//                Size++;
//            }
//            public void RemoveTransaction(List<int>
//                                                  transactions)
//            {
//                for (int i = 0; i < transactions.Count; i++)
//                {
//                    OccDict[transactions[i]] -= 1;
//                    if (OccDict[transactions[i]] == 0)
//                        OccDict.Remove(transactions[i]);
//                }
//                Width = OccDict.Count;
//                Square -= transactions.Count;
//                Size--;
//            }
//        }
//        public class Transaction
//        {
//            public List<int> Params { get; set; } = new
//                List<int>();
//            public int ClusterIndex { get; set; }
//        }
//        public class Data
//        {
//            private List<Transaction> _transactions;
//            private int _nextTransaction;
//            public Data()
//            {
//                _nextTransaction = 0;
//                _transactions = new List<Transaction>();
//                string str = "";
//                using (StreamReader sr = new
//                        StreamReader("normalizedData.data", Encoding.ASCII))
//                str
//                                {
//                    _nextTransaction = 0;
//                    return false;
//                }
//                return true;
//            }
//        }
//        public class Clope
//        {
//            public Dictionary<int, ClusterData>
//            Distribute(Data data, double r = 2.6D)
//            {
//                var dict_clusters = new Dictionary<int,
//                        ClusterData>();
//                bool isMoving = true;
//                while (isMoving)
//                {
//                    isMoving = false;
//                    while (data.isReread())
//                    {
//                        Transaction transactions =
//                                data.GetNextTransaction();
//                        double remove =
//                                transactions.ClusterIndex == 0 ? 0 :
//                                        DeltaRemove(dict_clusters[transactions.ClusterIndex],
//                                                transactions.Params, r);
//                        double maxDelta = 0;
//                        double delta = 0;
//                        int nextClusterIndex =
//                                transactions.ClusterIndex;
//                        foreach (var cluster in
//                                dict_clusters)
//                        {
//                            delta = DeltaAdd(cluster.Value,
//                                    transactions.Params, r);
//                            if (delta + remove > maxDelta)
//                            {
//                                maxDelta = delta;
//                                nextClusterIndex =
//                                        cluster.Key;
//                            }
//                        }
//                        delta = DeltaAdd(null,
//                                transactions.Params, r);
//                                                if (delta + remove > maxDelta)
//                        {
//                            if (dict_clusters.Count == 0)
//                                nextClusterIndex = 1;
//                            else
//                                nextClusterIndex =
//                                        dict_clusters.Keys.Max() + 1;
//                            dict_clusters.Add(nextClusterIndex, new ClusterData());
//                        }
//                        if (nextClusterIndex !=
//                                transactions.ClusterIndex)
//                        {
//                            if (transactions.ClusterIndex !=
//                                    0)
//                            {
//                                dict_clusters[transactions.ClusterIndex].RemoveTransactio
//                                n(transactions.Params);
//                            }
//                            dict_clusters[nextClusterIndex].AddTransaction(transacti
//                                    on.Params);
//                            isMoving = true;
//                            transactions.ClusterIndex =
//                                    nextClusterIndex;
////data.WriteTransaction(transactions);
//                        }
//                    }
//                }
//                List<int> deleteKey = new List<int>();
//                foreach (int key in dict_clusters.Keys)
//                if (dict_clusters[key].Size == 0)
//                {
//                    d
//                    {
//                        foreach (var cluster in clusters)
//                        {
//                            System.Diagnostics.Debug.WriteLine($"num {cluster.Key}
//                                    size {cluster.Value.Size } w { cluster.Value.Width}" );
//                        }
//                    }
//                    public double DeltaAdd(ClusterData cluster,
//                        List<int> transactions, double r)
//                    {
//                        /**/
//                        if (cluster == null)
//                            return transactions.Count /
//                                    Math.Pow(transactions.Count, r);
//                        int square = cluster.Square +
//                                transactions.Count;
//                        int width = cluster.Width;
//                        for (int i = 0; i < transactions.Count; i++)
//                            if
//                            (!cluster.OccDict.ContainsKey(transactions[i]))
//                                width++;
//                        return square * (cluster.Size + 1) /
//                                Math.Pow(width, r)
//                                - cluster.Square * cluster.Size /
//                                Math.Pow(cluster.Width, r);
//                    }
//                    public double DeltaRemove(ClusterData cluster,
//                        List<int> transactions, double r)
//                    {
//                        /**/
//                        int squareNew = cluster.Square -
//                                transactions.Count;
//                        int widthNew = cluster.Width;
//                        for (int i = 0; i < transactions.Count; i++)
//                            if (cluster.OccDict[transactions[i]] ==
//                                    1)
//                                widthNew--;
//                        return squareNew * (cluster.Size - 1) /
//                                Math.Pow(widthNew, r)
//                                - cluster.Square * cluster.Size /
//                                Math.Pow(cluster.Width, r);
//                    }
//                }
//            }
//            public class Transaction
//            {
//                public string Class { get; set; }
//                public List<string> Params { get; set; }
//                public Transaction()
//                {
//                    Params = new List<string>();
//                }
//            }
//            public class ClusterData
//            {
//                public string Class { get; set; }
//                public List<string[]> Vector { get; set; }
//                public ClusterData()
//                {
//                    Vector = new List<string[]>();
//                }
//            }
//
//}
