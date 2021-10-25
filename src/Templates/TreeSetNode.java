package Templates;

public class TreeSetNode {
    TreeSetNode parent, left, right;
    long value, sum;
    int size, height;

    TreeSetNode(long v) {
        sum = value = v;
        size = height = 1;
    }

    TreeSetNode finalisePosition(TreeSetNode root) {
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

    TreeSetNode rotateRight(TreeSetNode root) {
        TreeSetNode A = this, B = left, P = parent;

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

    TreeSetNode rotateLeft(TreeSetNode root) {
        TreeSetNode A = this, B = right, P = parent;

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
        sum = value + findSum(left) + findSum(right);
        size = 1 + findSize(left) + findSize(right);
        height = 1 + Math.max(findDepth(left), findDepth(right));
    }

    int size() {
        return size;
    }

    long getSum() {
        return sum;
    }

    static int findDepth(TreeSetNode node) {
        return node == null ? 0 : node.height;
    }

    static int findSize(TreeSetNode node) {
        return node == null ? 0 : node.size();
    }

    static long findSum(TreeSetNode node) {
        return node == null ? 0 : node.getSum();
    }

    public int compareTo(TreeSetNode node) {
        return Long.compare(value, node.value);
    }

    public String toString() {
        return value + "";
    }
}