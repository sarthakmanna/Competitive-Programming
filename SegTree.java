import java.util.Stack;
import java.util.ArrayList;

class Node<T, U> {
    static int actualLength;
    final int index, leftBound, rightBound;
    final long defaultValue, defaultLazy, defaultUpdate;
    T value;
    U lazy;

    Node(int i, int l, int r) {
        index = i; leftBound = l; rightBound = r;
        defaultValue = Long.MIN_VALUE; defaultLazy = 0; defaultUpdate = 0;
    }

    void node_node(Node<T, U> node) {
        if (!node.isValid()) return;
        Long v1 = value == null ? defaultValue : (Long) value;
        Long v2 = node.value == null ? defaultValue : (Long) node.value;
        value = (T) (v1 > v2 ? v1 : v2);
    }

    void lazy_lazy(Node<T, U> node) {
        if (!node.isValid()) return;
        Long v1 = lazy == null ? defaultLazy : (Long) lazy;
        Long v2 = node.lazy == null ? defaultLazy : (Long) node.lazy;
        lazy = (U) (v1 += v2);
    }

    void lazy_node() {
        int len = rightBound - leftBound + 1;
        Long newVal = lazy == null ? defaultLazy : (Long) lazy;
        //newVal *= len;
        newVal += value == null ? defaultValue : (Long) value;
        value = (T) newVal;
        lazy = null;
    }

    void updateValue_node(int ql, int qr, U updateValue) {
        int l = Math.max(leftBound, ql), r = Math.min(rightBound, qr);
        Long newVal = updateValue == null ? defaultUpdate : (Long) updateValue;
        //newVal *= (r - l + 1);
        newVal += value == null ? defaultValue : (Long) value;
        value = (T) newVal;
    }

    void updateValue_lazy(int ql, int qr, U updateValue) {
        Long newVal = updateValue == null ? defaultUpdate : (Long) updateValue;
        newVal += lazy == null ? defaultLazy : (Long) lazy;
        lazy = (U) newVal;
    }

    void reset() { value = null; lazy = null; }

    boolean isValid() { return leftBound <= rightBound; }

    public String toString() {
        return "(" + index + ". " + leftBound + "->" + rightBound + ") ["
                + value + ", " + lazy + "]";
    }
}

class SegmentTree<T, U> {
    int N;
    Node<T, U>[] tree;
    Stack<Node> stack = new Stack<>();

    SegmentTree (T[] ar) {
        Node.actualLength = ar.length;

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
        Node<T, U> retVal = new Node<>(-7, -7, -7);
        stack.clear();
        stack.push(tree[0]);

        while (!stack.isEmpty()) {
            Node<T, U> top = stack.pop();
            pushDown(top);

            if (!top.isValid() || top.leftBound > r || top.rightBound < l)
                continue;
            else if (top.leftBound >= l && top.rightBound <= r)
                retVal.node_node(top);
            else {
                stack.push(tree[(top.index << 1) + 1]);
                stack.push(tree[(top.index << 1) + 2]);
            }
        }
        return retVal.value;
    }

    void pushDown(Node<T, U> node) {
        if (node.index < N - 1) {
            tree[(node.index << 1) + 1].lazy_lazy(node);
            tree[(node.index << 1) + 2].lazy_lazy(node);
        }
        node.lazy_node();
    }

    int binarySearch(ArrayList<T> ar, int l, int r, T key) {
        int mid = (l + r) >> 1;
        long k = (long) key;
        if ((long) ar.get(l) > k) return l;
        else if ((long) ar.get(r) <= k) return r + 1;
        else if (l + 1 >= r) return r;
        else if ((long) ar.get(mid) <= k) return binarySearch(ar, mid + 1, r, key);
        else return binarySearch(ar, l, mid, key);
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
