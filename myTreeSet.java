class myTreeSet {
    Node root;
    int size;

    myTreeSet() { root = null; size = 0; }

    boolean add(long value) { return add(new Node(value)); }
    boolean remove(long value) {
        Node actual = new Node(value), toRemove = floor(actual);
        if (toRemove.compareTo(actual) != 0) return false;
        return remove(toRemove);
    }
    long floor(long value) { return floor(new Node(value)).value; }
    long ceiling(long value) { return ceiling(new Node(value)).value; }
    long lower(long value) { return lower(new Node(value)).value; }
    long higher(long value) { return higher(new Node(value)).value; }
    int size() { return root.size(); }

    boolean add(Node toAdd) {
        if (root == null) {
            root = toAdd; return true;
        }

        Node tr = root;
        while (true) {
            if (toAdd.compareTo(tr) < 0) {
                if (tr.left == null) {
                    tr.left = toAdd; toAdd.parent = tr;
                    break;
                } else tr = tr.left;
            } else if (toAdd.compareTo(tr) > 0) {
                if (tr.right == null) {
                    tr.right = toAdd; toAdd.parent = tr;
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

    boolean remove(Node node) {
        if (root.size() == 1) {
            root = null; return true;
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

        p.parent = node.parent; p.left = node.left; p.right = node.right;
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

    Node floor(Node node) {
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

    Node ceiling(Node node) {
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

    Node lower(Node node) {
        Node fl = floor(node);
        if (fl != null && node.compareTo(fl) == 0) fl = prev(fl);
        return fl;
    }

    Node higher(Node node) {
        Node cl = ceiling(node);
        if (cl != null && node.compareTo(cl) == 0) cl = next(cl);
        return cl;
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

    void dfs(Node node, StringBuilder sb) {
        if (node == null) return;
        dfs(node.left, sb);
        sb.append(node);
        dfs(node.right, sb);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        if (sb.length() == 0) return "[]";
        return "[" + sb.substring(0, sb.length() - 2) + "]";
    }
}

class Node {
    Node parent, left, right;
    long value;
    int size, height;

    Node(long v) { value = v; size = height = 1; }

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
        B.right = A; A.parent = B;

        A.setParameters(); B.setParameters();

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
        B.left = A; A.parent = B;

        A.setParameters(); B.setParameters();

        return root;
    }

    void setParameters() {
        size = 1 + findSize(left) + findSize(right);
        height = 1 + Math.max(findDepth(left), findDepth(right));
    }

    int size() { return size; }

    int findDepth(Node node) {
        return node == null ? 0 : node.height;
    }

    int findSize(Node node) { return node == null ? 0 : node.size(); }

    public int compareTo(Node node) {
        return Long.compare(value, node.value);
    }

    public String toString() {
        return value + "(" + size + ", " + height +  "), ";
    }
}
