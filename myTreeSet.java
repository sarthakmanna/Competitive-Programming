import java.util.ArrayList;
import java.util.Iterator;

class myTreeSet implements Iterable<Long> {
    Node root;

    myTreeSet() { root = null; }

    public boolean add(long value) { return add(new Node(value)); }

    public boolean remove(long value) {
        Node actual = new Node(value), toRemove = floor(actual);
        if (toRemove.compareTo(actual) != 0) return false;
        return remove(toRemove);
    }

    public boolean pollAtIndex(int index) {
        if (index < 0 || index >= size()) return false;
        else return remove(navigateTo(root, index));
    }

    public long floor(long value) { return floor(new Node(value)).value; }

    public long ceiling(long value) { return ceiling(new Node(value)).value; }

    public long lower(long value) { return lower(new Node(value)).value; }

    public long higher(long value) { return higher(new Node(value)).value; }

    public long elementAtIndex(int index) {
        if (index < 0 || index >= size()) return 7 / 0;
        else return navigateTo(root, index).value;
    }

    public int countFloorNodes(long value) { return countFloorNodes(root, new Node(value)); }

    public int size() { return root.size(); }

    private boolean add(Node toAdd) {
        if (root == null) {
            root = toAdd;
            return true;
        }

        Node tr = root;
        while (true) {
            if (toAdd.compareTo(tr) < 0) {
                if (tr.left == null) {
                    tr.left = toAdd;
                    toAdd.parent = tr;
                    break;
                } else tr = tr.left;
            } else if (toAdd.compareTo(tr) > 0) {
                if (tr.right == null) {
                    tr.right = toAdd;
                    toAdd.parent = tr;
                    break;
                } else tr = tr.right;
            } else return false;
        }

        while (toAdd != null) {
            root = toAdd.finalisePosition(root);
            toAdd = toAdd.parent;
        }
        return true;
    }

    private boolean remove(Node node) {
        if (root.size() == 1) {
            root = null;
            return true;
        }
        Node p;

        if (node.left != null) {
            p = prev(node);
            if (p.parent != null) {
                if (p.parent.left == p) p.parent.left = p.left;
                else if (p.parent.right == p) p.parent.right = p.left;
                else System.exit(7 / 0);
            }
            if (p.left != null) p.left.parent = p.parent;
        } else if (node.right != null) {
            p = next(node);
            if (p.parent != null) {
                if (p.parent.left == p) p.parent.left = p.right;
                else if (p.parent.right == p) p.parent.right = p.right;
                else System.exit(7 / 0);
            }
            if (p.right != null) p.right.parent = p.parent;
        } else {
            if (node.parent.left == node) node.parent.left = null;
            else if (node.parent.right == node) node.parent.right = null;
            else System.exit(7 / 0);

            Node tr = node.parent;
            while (tr != null) {
                root = tr.finalisePosition(root);
                tr = tr.parent;
            }
            return true;
        }


        Node tr = p.parent;
        while (tr != node) {
            root = tr.finalisePosition(root);
            tr = tr.parent;
        }

        p.parent = node.parent; node.parent = null;
        p.left = node.left; node.left = null;
        p.right = node.right; node.right = null;

        if (p.parent != null) {
            if (p.parent.left == node) p.parent.left = p;
            else if (p.parent.right == node) p.parent.right = p;
            else System.exit(7 / 0);
        }
        if (p.left != null) p.left.parent = p;
        if (p.right != null) p.right.parent = p;

        if (root == node) root = p;
        while (p != null) {
            root = p.finalisePosition(root);
            p = p.parent;
        }

        return true;
    }

    private Node floor(Node node) {
        if (root == null) return null;

        Node tr = root;
        while (true) {
            if (node.compareTo(tr) < 0) {
                if (tr.left == null) return prev(tr);
                else tr = tr.left;
            } else if (node.compareTo(tr) > 0) {
                if (tr.right == null) return tr;
                else tr = tr.right;
            } else return tr;
        }
    }

    private Node ceiling(Node node) {
        if (root == null) return null;

        Node tr = root;
        while (true) {
            if (node.compareTo(tr) < 0) {
                if (tr.left == null) return tr;
                else tr = tr.left;
            } else if (node.compareTo(tr) > 0) {
                if (tr.right == null) return next(tr);
                else tr = tr.right;
            } else return tr;
        }
    }

    private Node lower(Node node) {
        Node fl = floor(node);
        if (fl != null && node.compareTo(fl) == 0) fl = prev(fl);
        return fl;
    }

    private Node higher(Node node) {
        Node cl = ceiling(node);
        if (cl != null && node.compareTo(cl) == 0) cl = next(cl);
        return cl;
    }

    private Node navigateTo(Node node, int ind) {
        if (Node.findSize(node.left) > ind) return navigateTo(node.left, ind);
        ind -= Node.findSize(node.left);
        if (ind == 0) return node;
        ind -= 1;
        return navigateTo(node.right, ind);
    }

    private int countFloorNodes(Node node, Node key) {
        if (node == null) return 0;
        else if (node.compareTo(key) > 0)
            return countFloorNodes(node.left, key);
        else
            return Node.findSize(node.left) + 1 + countFloorNodes(node.right, key);
    }

    private Node prev(Node node) {
        Node parent;

        if (node.left != null) {
            node = node.left;
            while (node.right != null) node = node.right;
            return node;
        }

        while (true) {
            parent = node.parent;
            if (parent == null) return null;
            else if (parent.right == node) return parent;
            else node = parent;
        }
    }

    private Node next(Node node) {
        Node parent;

        if (node.right != null) {
            node = node.right;
            while (node.left != null) node = node.left;
            return node;
        }

        while (true) {
            parent = node.parent;
            if (parent == null) return null;
            else if (parent.left == node) return parent;
            else node = parent;
        }
    }

    private int itrInd;
    private ArrayList<Node> dfsTrav = new ArrayList<>();

    public Iterator<Long> iterator() {
        dfsTrav.clear(); dfs(root);
        itrInd = 0;
        Iterator<Long> iterator = new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return itrInd < size();
            }

            @Override
            public Long next() {
                return dfsTrav.get(itrInd++).value;
            }
        };
        return iterator;
    }

    private void dfs(Node node) {
        if (node == null) return;
        dfs(node.left);
        dfsTrav.add(node);
        dfs(node.right);
    }

    public String toString() {
        dfsTrav.clear(); dfs(root);
        if (dfsTrav.isEmpty()) return "[]";
        else return dfsTrav.toString();
    }
}

class Node {
    Node parent, left, right;
    long value;
    int size, height;

    Node(long v) {
        value = v;
        size = height = 1;
    }

    Node finalisePosition(Node root) {
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

    Node rotateRight(Node root) {
        Node A = this, B = left, P = parent;

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

    Node rotateLeft(Node root) {
        Node A = this, B = right, P = parent;

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

    static int findDepth(Node node) {
        return node == null ? 0 : node.height;
    }

    static int findSize(Node node) {
        return node == null ? 0 : node.size();
    }

    public int compareTo(Node node) {
        return Long.compare(value, node.value);
    }

    public String toString() {
        return value + "";
    }
}
