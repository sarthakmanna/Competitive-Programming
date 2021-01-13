package Templates;

public class FenwickTree {
    int N;
    long[] tree;

    public FenwickTree(int size) {
        N = size + 1;
        tree = new long[N];
    }

    void update(int idx, long val) {
        ++idx;
        while (idx < N) {
            tree[idx] += val;
            idx += idx & -idx;
        }
    }

    long query(int idx) {
        ++idx;
        long ret = 0;
        while (idx > 0) {
            ret += tree[idx];
            idx -= idx & -idx;
        }
        return ret;
    }

    long query(int l, int r) {
        long ret = query(r);
        if (l > 0) ret -= query(l - 1);
        return ret;
    }
}
