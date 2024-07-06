package Templates;

import java.io.*;
import java.util.ArrayList;

public class FastReader {
    static final int BUFSIZE = 1 << 20;
    static byte[] buf;
    static int index, total;
    static InputStream in;

    public FastReader(InputStream is) {
        try {
            in = is;
            buf = new byte[BUFSIZE];
        } catch (Exception e) {
        }
    }

    private int scan() {
        try {
            if (index >= total) {
                index = 0;
                total = in.read(buf);
                if (total <= 0)
                    return -1;
            }
            return buf[index++];
        } catch (Exception | Error e) {
            System.err.println(e.getMessage());
            return 13 / 0;
        }
    }

    public String next() {
        int c;
        for (c = scan(); c <= 32; c = scan()) ;
        StringBuilder sb = new StringBuilder();
        for (; c > 32; c = scan())
            sb.append((char) c);
        return sb.toString();
    }

    public char nextChar() {
        int c;
        for (c = scan(); c <= 32; c = scan()) ;
        return (char) c;
    }

    public int nextInt() {
        int c, val = 0;
        for (c = scan(); c <= 32; c = scan()) ;
        boolean neg = c == '-';
        if (c == '-' || c == '+')
            c = scan();
        for (; c >= '0' && c <= '9'; c = scan())
            val = (val << 3) + (val << 1) + (c & 15);
        return neg ? -val : val;
    }

    public long nextLong() {
        int c;
        long val = 0;
        for (c = scan(); c <= 32; c = scan()) ;
        boolean neg = c == '-';
        if (c == '-' || c == '+')
            c = scan();
        for (; c >= '0' && c <= '9'; c = scan())
            val = (val << 3) + (val << 1) + (c & 15);
        return neg ? -val : val;
    }

    public long[] getLongArray(int size) {
        long[] ar = new long[size];
        for (int i = 0; i < size; ++i) ar[i] = nextLong();
        return ar;
    }

    public int[] getIntArray(int size) {
        int[] ar = new int[size];
        for (int i = 0; i < size; ++i) ar[i] = nextInt();
        return ar;
    }

    public String[] getStringArray(int size) {
        String[] ar = new String[size];
        for (int i = 0; i < size; ++i) ar[i] = next();
        return ar;
    }

    public ArrayList<Integer>[] getUndirectedTree(int N) {
        return getUndirectedGraph(N, N - 1);
    }

    public ArrayList<Integer>[] getUndirectedGraph(int N, int M) {
        return getUndirectedGraph(N, M, 1);
    }

    public ArrayList<Integer>[] getUndirectedGraph(int N, int M, int basedOffset) {
        ArrayList<Integer>[] graph = initializeGraph(N);
        for (int i = 0; i < M; ++i) {
            int u = nextInt() - basedOffset, v = nextInt() - basedOffset;
            graph[u].add(v);
            graph[v].add(u);
        }
        return graph;
    }

    public ArrayList<Edge>[] getUndirectedWeightedTree(int N) {
        return getUndirectedWeightedGraph(N, N - 1);
    }

    public ArrayList<Edge>[] getUndirectedWeightedGraph(int N, int M) {
        return getUndirectedWeightedGraph(N, M, 1);
    }

    public ArrayList<Edge>[] getUndirectedWeightedGraph(int N, int M, int basedOffset) {
        ArrayList<Edge>[] graph = initializeGraph(N);
        for (int i = 0; i < M; ++i) {
            int u = nextInt() - basedOffset, v = nextInt() - basedOffset;
            long w = nextLong();
            Edge edge = new Edge(u, v, w);
            graph[u].add(edge);
            graph[v].add(edge);
        }
        return graph;
    }

    public <T> ArrayList<T>[] initializeGraph(int N) {
        ArrayList<T> graph[] = new ArrayList[N];
        for (int i = 0; i < N; ++i) graph[i] = new ArrayList<>();
        return graph;
    }
}