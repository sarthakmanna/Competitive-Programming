package Templates;

import java.util.Comparator;

public class TreeMapNode implements Comparable<TreeMapNode> {
    TreeMapNode parent, left, right;
    long key, value, sum;
    int size, height;
    Comparator<Long> comparator;

    TreeMapNode(long k, long v, Comparator<Long> com) {
        sum = key = k;
        value = v;
        size = height = 1;
        comparator = com;
    }

    TreeMapNode finalisePosition(TreeMapNode root) {
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

    TreeMapNode rotateRight(TreeMapNode root) {
        TreeMapNode A = this, B = left, P = parent;

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

    TreeMapNode rotateLeft(TreeMapNode root) {
        TreeMapNode A = this, B = right, P = parent;

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
        sum = key + findSum(left) + findSum(right);
        size = 1 + findSize(left) + findSize(right);
        height = 1 + Math.max(findDepth(left), findDepth(right));
    }

    int size() {
        return size;
    }

    long getSum() {
        return sum;
    }

    static int findDepth(TreeMapNode node) {
        return node == null ? 0 : node.height;
    }

    static int findSize(TreeMapNode node) {
        return node == null ? 0 : node.size();
    }

    static long findSum(TreeMapNode node) {
        return node == null ? 0 : node.getSum();
    }

    public int compareTo(TreeMapNode node) {
        return comparator.compare(key, node.key);
    }

    public String toString() {
        return "(" + key + "," + value + ")";
    }
}