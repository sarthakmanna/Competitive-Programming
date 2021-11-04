package Templates;

import java.util.ArrayList;
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
        tree = new long[N << 1]; Arrays.fill(tree, DEFAULT_NODE);
        lazy = new long[N << 1]; Arrays.fill(lazy, DEFAULT_LAZY);

        for (int i = 0; i < ar.length; ++i) tree[i + N] = ar[i];
        for (int i = N - 1; i > 0; --i) {
            tree[i] = nodeToNode(tree[i << 1], tree[i << 1 | 1]);
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
        if (i << 1 < tree.length) {
            lazy[i << 1] = lazyToLazy(lazy[i << 1], lazy[i]);
            lazy[i << 1 | 1] = lazyToLazy(lazy[i << 1 | 1], lazy[i]);
        }
        lazy[i] = DEFAULT_LAZY;
    }

    public void pointUpdate(int i, long val) {
        int idx = i + N;
        tree[idx] = nodeToNode(tree[idx], val);
        idx >>= 1;

        while (idx > 0) {
            tree[idx] = nodeToNode(tree[idx << 1], tree[idx << 1 | 1]);
            idx >>= 1;
        }
    }

    public void rangeUpdate(int l, int r, long val) {
        update(1, 0, N - 1, l, r, val);
    }

    public long rangeQuery(int l, int r) {
        return query(1, 0, N - 1, l, r);
    }


    private void update(int i, int l, int r, int ql, int qr, long val) {
        pushDown(i, l, r);
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return;
        else if (l >= ql && r <= qr) {
            lazy[i] = lazyToLazy(lazy[i], val);
            pushDown(i, l, r);
        } else {
            update(i2, l, mid, ql, qr, val);
            update(i2 | 1, mid + 1, r, ql, qr, val);

            // Rebuild tree[i] if necessary
            tree[i] = nodeToNode(tree[i2], tree[i2 | 1]);
        }
    }

    private long query(int i, int l, int r, int ql, int qr) {
        pushDown(i, l, r);
        int mid = l + r >> 1, i2 = i << 1;
        if (l > qr || r < ql) return DEFAULT_NODE;
        else if (l >= ql && r <= qr) return tree[i];
        else {
            return nodeToNode(query(i2, l, mid, ql, qr),
                    query(i2 | 1, mid + 1, r, ql, qr));
        }
    }

    public String toString() {
        ArrayList<Long> toStr = new ArrayList<>();
        for (int i = 0; i < N; ++i) toStr.add(rangeQuery(i, i));
        return toStr.toString();
    }
}
