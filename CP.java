import java.io.*;
import java.util.*;

class CP {
    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    new Solver().solve();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Solver", 1l << 30).start();
    }
}

class Solver {
    IO io = new IO(System.in);

    void solve() throws Exception {
        int i, j, k;
        for (int tc = io.nextInt(); tc > 0; --tc) {
            
        }
    }
}

class IO {
    static byte[] buf = new byte[2048];
    static int index, total;
    static InputStream in;
    static StringBuilder sb = new StringBuilder();


    IO(InputStream is) {
        in = is;
    }

    int scan() throws Exception {
        if (index >= total) {
            index = 0;
            total = in.read(buf);
            if (total <= 0)
                return -1;
        }
        return buf[index++];
    }

    String next() throws Exception {
        int c;
        for (c = scan(); c <= 32; c = scan()) ;
        StringBuilder sb = new StringBuilder();
        for (; c > 32; c = scan())
            sb.append((char) c);
        return sb.toString();
    }

    int nextInt() throws Exception {
        int c, val = 0;
        for (c = scan(); c <= 32; c = scan()) ;
        boolean neg = c == '-';
        if (c == '-' || c == '+')
            c = scan();
        for (; c >= '0' && c <= '9'; c = scan())
            val = (val << 3) + (val << 1) + (c & 15);
        return neg ? -val : val;
    }

    long nextLong() throws Exception {
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

    void print(Object a) {
        sb.append(a.toString());
    }

    void println(Object a) {
        sb.append(a.toString()).append("\n");
    }

    void println() {
        sb.append("\n");
    }

    void flush() {
        System.out.print(sb);
        sb = new StringBuilder();
    }
}
