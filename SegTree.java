import java.util.ArrayList;
import java.util.Stack;

class Node<T, U> {
    static int actualLength;
    final T defaultValue = (T) (Long) Long.MIN_VALUE;
    final U defaultLazy = (U) (Long) 0l, defaultUpdate = (U) (Long) 0l;

    final int index, leftBound, rightBound;
    T value;
    U lazy;

    Node(int i, int l, int r) {
        index = i; leftBound = l; rightBound = r;
    }

    T operate(T retVal, U key) {
        Long v1 = (Long) (value == null ? defaultValue : value);
        Long v2 = (Long) (retVal == null ? defaultValue : retVal);
        return (T) (v1 > v2 ? v1 : v2);
    }

    void node_node(Node<T, U> node) {
        if (!node.isValid()) return;
        Long v1 = (Long) (value == null ? defaultValue : value);
        Long v2 = (Long) (node.value == null ? defaultValue : node.value);
        value = (T) (v1 > v2 ? v1 : v2);
    }

    void lazy_lazy(Node<T, U> node) {
        if (!node.isValid()) return;
        Long v1 = (Long) (lazy == null ? defaultLazy : lazy);
        Long v2 = (Long) (node.lazy == null ? defaultLazy : node.lazy);
        lazy = (U) (v1 += v2);
    }

    void lazy_node() {
        int len = rightBound - leftBound + 1;
        Long newVal = (Long) (lazy == null ? defaultLazy : lazy);
        //newVal *= len;
        newVal += (Long) (value == null ? defaultValue : value);
        value = (T) newVal;
        lazy = null;
    }

    void updateValue_node(int ql, int qr, U updateValue) {
        int l = Math.max(leftBound, ql), r = Math.min(rightBound, qr);
        Long newVal = (Long) (updateValue == null ? defaultUpdate : updateValue);
        //newVal *= (r - l + 1);
        newVal += (Long) (value == null ? defaultValue : value);
        value = (T) newVal;
    }

    void updateValue_lazy(int ql, int qr, U updateValue) {
        Long newVal = (Long) (updateValue == null ? defaultUpdate : updateValue);
        newVal += (Long) (lazy == null ? defaultLazy : lazy);
        lazy = (U) newVal;
    }

    int binarySearch(ArrayList<U> ar, int l, int r, U key) {
        int mid; long k;
        for ( ; ; ) {
            mid = (l + r) >> 1;
            k = (Long) key;

            if ((Long) ar.get(l) > k) return l;
            else if ((Long) ar.get(r) <= k) return r + 1;
            else if (l + 1 >= r) return r;
            else if ((Long) ar.get(mid) <= k) l = mid + 1;
            else r = mid;
        }
    }

    void reset() { value = null; lazy = null; }

    boolean isValid() { return index < 0 || leftBound <= rightBound; }

    public String toString() {
        return "(" + index + ". " + leftBound + "->" + rightBound + ") ["
                + value + ", " + lazy + "]";
    }
}

class SegmentTree<T, U> {
    static final int MAXN = 1000_006;
    int N;
    Node<T, U>[] tree;
    Stack<Node> stack;

    SegmentTree (T[] ar) {
        Node.actualLength = ar.length;
        stack = new Stack<>();
        stack.ensureCapacity(MAXN);

        N = 1; while (N < ar.length) N <<= 1;
        tree = new Node[(N << 1) - 1];
        int step = N << 1, left = 0;
        for (int i = 0, temp = 0; i < tree.length; ++i) {
            if (Integer.bitCount(i) == temp) {
                step >>= 1;
                left = 0;
                ++temp;
            }
            tree[i] = new Node<>(i, left, Math.min(ar.length - 1, (left += step) - 1));
        }

        for (int i = 0; i < ar.length; ++i) tree[i + N - 1].value = ar[i];
        for (int i = N - 2; i >= 0; --i) build(i);
    }

    void build(int i) {
        pushDown(tree[(i << 1) + 1]);
        pushDown(tree[(i << 1) + 2]);

        tree[i].reset();
        tree[i].node_node(tree[(i << 1) + 1]);
        tree[i].node_node(tree[(i << 1) + 2]);
    }

    void rangeUpdate(int l, int r, U updateValue) {
        if (l > r) return;

        stack.clear();
        stack.push(tree[0]);

        while (!stack.isEmpty()) {
            Node<T, U> top = stack.pop();
            pushDown(top);

            if (!top.isValid() || top.leftBound > r || top.rightBound < l)
                continue;
            else if (top.leftBound >= l && top.rightBound <= r) {
                top.updateValue_lazy(l, r, updateValue);

                // Comment if bottom-up update is not required
                for (int i = (top.index - 1) >> 1; i >= 0; i = (i - 1) >> 1)
                    build(i);
            }
            else {
                top.updateValue_node(l, r, updateValue);
                stack.push(tree[(top.index << 1) + 1]);
                stack.push(tree[(top.index << 1) + 2]);
            }
        }
    }

    T rangeQuery(int l, int r, U key) {
        T retVal = tree[0].defaultValue;
        if (l > r) return retVal;

        stack.clear();
        stack.push(tree[0]);

        while (!stack.isEmpty()) {
            Node<T, U> top = stack.pop();
            pushDown(top);

            if (!top.isValid() || top.leftBound > r || top.rightBound < l)
                continue;
            else if (top.leftBound >= l && top.rightBound <= r)
                retVal = top.operate(retVal, key);
            else {
                stack.push(tree[(top.index << 1) + 1]);
                stack.push(tree[(top.index << 1) + 2]);
            }
        }
        return retVal;
    }

    void pushDown(Node<T, U> node) {
        if (node.index < N - 1) {
            tree[(node.index << 1) + 1].lazy_lazy(node);
            tree[(node.index << 1) + 2].lazy_lazy(node);
        }
        node.lazy_node();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, temp = 0; i < tree.length; ++i) {
            if (Integer.bitCount(i) == temp) {
                sb.append("\n");
                ++temp;
            }
            sb.append(tree[i]).append("; ");
        }
        return sb.append("\n\n").toString();
    }
}
