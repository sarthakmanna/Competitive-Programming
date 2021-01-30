package Templates;

public class Edge {
    int u, v;

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public long getWeight() {
        return weight;
    }

    long weight;

    public Edge(int a, int b, long wt) {
        u = a;
        v = b;
        weight = wt;
    }

    public String toString() {
        return "(" + u + ", " + v + " -> " + weight + ")";
    }
}