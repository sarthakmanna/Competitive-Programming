class SegmentTree {
    private int N;
    private long[] tree, lazy;

    public SegmentTree(long[] ar) {
        N = 1;
        while (N < ar.length) N <<= 1;
        tree = new long[N * 2 - 1];
        lazy = new long[N * 2 - 1];

        for (int i = 0; i < ar.length; ++i) tree[i + N - 1] = ar[i];
        for (int i = N - 2; i >= 0; --i) tree[i] = tree[i * 2 + 1] + tree[i * 2 + 2];
    }

    public void pushDown(int i, int l, int r) {
        tree[i] += lazy[i] * (r - l + 1);
        if (i * 2 + 1 < tree.length) {
            lazy[i * 2 + 1] += lazy[i];
            lazy[i * 2 + 2] += lazy[i];
        }
        lazy[i] = 0;
    }

    public void pointUpdate(int i, long val) {
        i += N - 1;
        while (i >= 0) {
            tree[i] += val;
            i = i - 1 >> 1;
        }
    }

    public void rangeUpdate(int l, int r, long val) {
        update(0, 0, N - 1, l, r, val);
    }

    public long rangeQuery(int l, int r, long key) {
        return query(0, 0, N - 1, l, r, key);
    }


    private void update(int i, int l, int r, int ql, int qr, long val) {
        pushDown(i, l, r);
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return;
        else if (l >= ql && r <= qr) {
            lazy[i] += val;
            pushDown(i, l, r);
        } else {
            update(i2 + 1, l, mid, ql, qr, val);
            update(i2 + 2, mid + 1, r, ql, qr, val);

            // Rebuild tree[i] if necessary
            tree[i] = tree[i2 + 1] + tree[i2 + 2];
        }
    }

    private long query(int i, int l, int r, int ql, int qr, long key) {
        pushDown(i, l, r);
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return 0;
        else if (l >= ql && r <= qr) return tree[i];
        else {
            return query(i2 + 1, l, mid, ql, qr, key)
                    + query(i2 + 2, mid + 1, r, ql, qr, key);
        }
    }
}
