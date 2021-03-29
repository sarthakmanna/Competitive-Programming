package Templates;

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
        long ret = prefixQuery(r);
        if (l > 0) ret -= prefixQuery(l - 1);
        return ret;
    }
}
