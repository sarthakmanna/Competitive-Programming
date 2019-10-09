class FenwickTree {
    int N;
    long[] tree;

    FenwickTree(int size) {
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
}
