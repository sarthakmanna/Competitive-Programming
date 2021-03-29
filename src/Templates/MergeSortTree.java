package Templates;

public class MergeSortTree {
    private int N;

    public int getN() {
        return N;
    }

    public myArrayListLong[] getTree() {
        return tree;
    }

    private myArrayListLong[] tree;

    public MergeSortTree(long[] ar) {
        N = 1;
        while (N < ar.length) N <<= 1;
        tree = new myArrayListLong[N * 2 - 1];

        for (int i = N - 1; i < tree.length; ++i) tree[i] = new myArrayListLong(-7);
        for (int i = 0; i < ar.length; ++i) tree[i + N - 1].add(ar[i]);
        for (int i = N - 2; i >= 0; --i) tree[i] = tree[i * 2 + 1].mergeSorted(tree[i * 2 + 2]);
    }

    public long rangeQuery(int l, int r, long key) {
        return query(0, 0, N - 1, l, r, key);
    }

    private long query(int i, int l, int r, int ql, int qr, long key) {
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return 0;
        else if (l >= ql && r <= qr) return tree[i].countFloor(key);
        else return query(i2 + 1, l, mid, ql, qr, key) + query(i2 + 2, mid + 1, r, ql, qr, key);
    }
}