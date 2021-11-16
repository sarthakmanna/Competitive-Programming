package Templates;

public class MergeSortTree {
    private int N;

    public int getN() {
        return N;
    }

    public MyArrayListLong[] getTree() {
        return tree;
    }

    private MyArrayListLong[] tree;

    public MergeSortTree(long[] ar) {
        N = 1;
        while (N < ar.length) N <<= 1;
        tree = new MyArrayListLong[N << 1];

        for (int i = N; i < tree.length; ++i) tree[i] = new MyArrayListLong(-7);
        for (int i = 0; i < ar.length; ++i) tree[i + N].add(ar[i]);
        for (int i = N - 1; i > 0; --i) tree[i] = tree[i << 1].mergeSorted(tree[i << 1 | 1]);
    }

    public long rangeQuery(int l, int r, long key) {
        return query(1, 0, N - 1, l, r, key);
    }

    private long query(int i, int l, int r, int ql, int qr, long key) {
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return 0;
        else if (l >= ql && r <= qr) return tree[i].countFloor(key);
        else return query(i2, l, mid, ql, qr, key) + query(i2 | 1, mid + 1, r, ql, qr, key);
    }
}