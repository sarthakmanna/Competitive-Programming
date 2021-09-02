package Templates;

import java.util.Arrays;

public class SegmentTree {
    private int N;

    public int getN() {
        return N;
    }

    public long[] getTree() {
        return tree;
    }

    public long[] getLazy() {
        return lazy;
    }

    private long[] tree, lazy;

    public SegmentTree(long[] ar, long def_node, long def_lazy) {
        DEFAULT_NODE = def_node; DEFAULT_LAZY = def_lazy;
        N = 1;
        while (N < ar.length) N <<= 1;
        tree = new long[N * 2 - 1]; Arrays.fill(tree, DEFAULT_NODE);
        lazy = new long[N * 2 - 1]; Arrays.fill(lazy, DEFAULT_LAZY);

        for (int i = 0; i < ar.length; ++i) tree[i + N - 1] = ar[i];
        for (int i = N - 2; i >= 0; --i) {
            tree[i] = nodeToNode(tree[i * 2 + 1], tree[i * 2 + 2]);
        }
    }

    public final long DEFAULT_NODE, DEFAULT_LAZY;

    // Override following three functions as per convenience.
    public long nodeToNode(long a, long b) {
        return a + b;
    }
    public long lazyToLazy(long a, long b) {
        return a + b;
    }
    public long lazyToNode(long nv, long lv, int l, int r) {
        return nv + lv * (r - l + 1);
    }

    public void pushDown(int i, int l, int r) {
        tree[i] = lazyToNode(tree[i], lazy[i], l, r);
        if (i * 2 + 1 < tree.length) {
            lazy[i * 2 + 1] = lazyToLazy(lazy[i * 2 + 1], lazy[i]);
            lazy[i * 2 + 2] = lazyToLazy(lazy[i * 2 + 2], lazy[i]);
        }
        lazy[i] = DEFAULT_LAZY;
    }

    public void pointUpdate(int i, long val) {
        i += N - 1;
        tree[i] += val;
        i = i - 1 >> 1;
        while (i >= 0) {
            tree[i] = nodeToNode(tree[i * 2 + 1], tree[i * 2 + 2]);
            i = i - 1 >> 1;
        }
    }

    public void rangeUpdate(int l, int r, long val) {
        update(0, 0, N - 1, l, r, val);
    }

    public long rangeQuery(int l, int r) {
        return query(0, 0, N - 1, l, r);
    }


    private void update(int i, int l, int r, int ql, int qr, long val) {
        pushDown(i, l, r);
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return;
        else if (l >= ql && r <= qr) {
            lazy[i] = lazyToLazy(lazy[i], val);
            pushDown(i, l, r);
        } else {
            update(i2 + 1, l, mid, ql, qr, val);
            update(i2 + 2, mid + 1, r, ql, qr, val);

            // Rebuild tree[i] if necessary
            tree[i] = nodeToNode(tree[i2 + 1], tree[i2 + 2]);
        }
    }

    private long query(int i, int l, int r, int ql, int qr) {
        pushDown(i, l, r);
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return DEFAULT_NODE;
        else if (l >= ql && r <= qr) return tree[i];
        else {
            return nodeToNode(query(i2 + 1, l, mid, ql, qr),
                    query(i2 + 2, mid + 1, r, ql, qr));
        }
    }

    public String toString() {
        return "SegTree = " + Arrays.toString(tree) + ", " + Arrays.toString(lazy);
    }
}
