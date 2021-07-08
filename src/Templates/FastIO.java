package Templates;

import java.io.*;
import java.util.*;

class FastIO {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer("");
    StringBuilder sb = new StringBuilder();

    public String next() throws Exception {
        while (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public int nextInt() throws Exception {
        return Integer.parseInt(next());
    }

    public long nextLong() throws Exception {
        return Long.parseLong(next());
    }

    public String nextLine() throws Exception {
        return br.readLine();
    }

    public void print(Object o) {
        sb.append(o);
    }

    public void println() {
        print("\n");
    }

    public void println(Object o) {
        print(o);
        println();
    }

    public void flush() {
        System.out.print(sb);
        System.out.flush();
        sb = new StringBuilder();
    }

    int[] getIntArray(int size) throws Exception {
        int[] ret = new int[size];
        for (int i = 0; i < size; ++i) ret[i] = nextInt();
        return ret;
    }

    long[] getLongArray(int size) throws Exception {
        long[] ret = new long[size];
        for (int i = 0; i < size; ++i) ret[i] = nextLong();
        return ret;
    }
}