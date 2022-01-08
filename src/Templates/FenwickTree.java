package Templates;

import java.util.ArrayList;

public class FenwickTree {
    int N;
    long[] tree;

    public FenwickTree(int size) {
        N = size + 1;
        tree = new long[N];
    }

    public void update(int idx, long val) {
        ++idx;
        while (idx < N) {
            tree[idx] += val;
            idx += idx & -idx;
        }
    }

    public long prefixQuery(int idx) {
        ++idx;
        long ret = 0;
        while (idx > 0) {
            ret += tree[idx];
            idx -= idx & -idx;
        }
        return ret;
    }

    public long query(int l, int r) {
        if (l > r) return 0;
        long ret = prefixQuery(r);
        if (l > 0) ret -= prefixQuery(l - 1);
        return ret;
    }

    public String toString() {
        ArrayList<Long> toStr = new ArrayList<>();
        for (int i = 0; i < N - 1; ++i) toStr.add(query(i, i));
        return toStr.toString();
    }
}
