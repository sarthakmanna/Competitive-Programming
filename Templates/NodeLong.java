package Templates;

public class NodeLong {
    NodeLong parent, left, right;
    long value;
    int size, height;

    NodeLong(long v) {
        value = v;
        size = height = 1;
    }

    NodeLong finalisePosition(NodeLong root) {
        int lheight = findDepth(left), rheight = findDepth(right);

        if (lheight > rheight + 1) {
            if (findDepth(left.right) > findDepth(left.left))
                root = left.rotateLeft(root);
            root = rotateRight(root);
        } else if (lheight + 1 < rheight) {
            if (findDepth(right.left) > findDepth(right.right))
                root = right.rotateRight(root);
            root = rotateLeft(root);
        } else {
            setParameters();
        }

        return root;
    }

    NodeLong rotateRight(NodeLong root) {
        NodeLong A = this, B = left, P = parent;

        if (P != null) {
            if (P.left == this) P.left = B;
            else if (P.right == this) P.right = B;
            else System.exit(7 / 0);
        } else root = B;
        B.parent = A.parent;

        A.left = B.right;
        if (B.right != null) B.right.parent = A;
        B.right = A;
        A.parent = B;

        A.setParameters();
        B.setParameters();

        return root;
    }

    NodeLong rotateLeft(NodeLong root) {
        NodeLong A = this, B = right, P = parent;

        if (P != null) {
            if (P.left == this) P.left = B;
            else if (P.right == this) P.right = B;
            else System.exit(7 / 0);
        } else root = B;
        B.parent = A.parent;

        A.right = B.left;
        if (B.left != null) B.left.parent = A;
        B.left = A;
        A.parent = B;

        A.setParameters();
        B.setParameters();

        return root;
    }

    void setParameters() {
        size = 1 + findSize(left) + findSize(right);
        height = 1 + Math.max(findDepth(left), findDepth(right));
    }

    int size() {
        return size;
    }

    static int findDepth(NodeLong node) {
        return node == null ? 0 : node.height;
    }

    static int findSize(NodeLong node) {
        return node == null ? 0 : node.size();
    }

    public int compareTo(NodeLong node) {
        return Long.compare(value, node.value);
    }

    public String toString() {
        return value + "";
    }
}