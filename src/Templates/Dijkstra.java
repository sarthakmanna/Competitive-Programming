package Templates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    public static final long INF = Long.MAX_VALUE;
    public int N;
    public ArrayList<Edge>[] graph;
    public long[] dist;


    public long[] getDist() {
        return dist;
    }

    public Dijkstra(int n, ArrayList<Edge>[] g) {
        N = n;
        graph = g;
    }

    public void runDijkstra(int source) {
        boolean[] visited = new boolean[N];
        dist = new long[N];
        Arrays.fill(dist, INF);

        PriorityQueue<Pair<Integer, Long>> pq = new PriorityQueue<>((a, b)
                                                -> Long.compare(a.getSecond(), b.getSecond()));
        pq.add(new Pair<>(source, 0l));

        while (!pq.isEmpty()) {
            Pair<Integer, Long> top = pq.poll();
            int node = top.getFirst(); long d = top.getSecond();
            visited[node] = true;

            if (dist[node] <= d) continue;
            dist[node] = d;

            for (Edge e : graph[node]) {
                if (!visited[e.getOtherNode(node)]) {
                    Pair tmp = new Pair(e.getOtherNode(node), d + e.getWeight());
                    pq.add(tmp);
                }
            }
        }
    }

    public long getDistance(int node) {
        return dist[node];
    }
}
