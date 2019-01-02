import java.io.*;
import java.util.*;

public class CP {
    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            @Override
            public void run() {
                new Solver().run();
            }
        }, "Solver", 1l << 30).start();
    }
}

class Solver extends Thread {
    public void run() {
        try {
            solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static IO io = new IO(System.in);
    static final long MOD = (long) (1e9 + 7);
    static int N, K, D;
    static long[][] dp;

    static void solve() throws Exception {
        int i, j, k;

        N = io.nextInt(); K = io.nextInt(); D = io.nextInt();
        dp = new long[N + 1][2];
        dp[0][0] = 1;

        for (i = 0; i < N; ++i) {
            for (j = 1; j <= K && i + j <= N; ++j) {
                if (j < D) {
                    dp[i + j][0] += dp[i][0];
                    dp[i + j][1] += dp[i][1];
                } else {
                    dp[i + j][1] += dp[i][0] + dp[i][1];
                }
                //dp[i + j][0] %= MOD;
                //dp[i + j][1] %= MOD;
            }
        }

        io.println(dp[N][1] % MOD);

        io.flush();
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
