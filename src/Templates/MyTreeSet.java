package Templates;

import java.util.*;

public class MyTreeSet implements Iterable<Long> {
    TreeSetNode root;

    public boolean isAllowDuplicates() {
        return allowDuplicates;
    }

    boolean allowDuplicates;

    public MyTreeSet() {
        root = null;
    }

    public MyTreeSet(boolean allowDupli) {
        root = null;
        allowDuplicates = allowDupli;
    }

    public boolean add(long value) {
        return add(new TreeSetNode(value));
    }

    public boolean remove(long value) {
        TreeSetNode actual = new TreeSetNode(value), toRemove = floor(actual);
        if (toRemove.compareTo(actual) != 0) return false;
        return remove(toRemove);
    }

    public boolean pollAtIndex(int index) {
        if (index < 0 || index >= size()) return false;
        else return remove(navigateTo(root, index));
    }

    public boolean contains(long value) {
        TreeSetNode fl = floor(new TreeSetNode(value));
        return fl != null && fl.value == value;
    }

    public long floor(long value) {
        return floor(new TreeSetNode(value)).value;
    }

    public long ceiling(long value) {
        return ceiling(new TreeSetNode(value)).value;
    }

    public long lower(long value) {
        return lower(new TreeSetNode(value)).value;
    }

    public long higher(long value) {
        return higher(new TreeSetNode(value)).value;
    }

    public long elementAtIndex(int index) {
        if (index < 0 || index >= size()) return 7 / 0;
        else return navigateTo(root, index).value;
    }

    public int countFloorNodes(long value) {
        return countFloorNodes(root, new TreeSetNode(value));
    }

    public long sumUpFloorNodes(long value) {
        return sumUpFloorNodes(root, new TreeSetNode(value));
    }

    public int size() {
        return root == null ? 0 : root.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long first() {
        return ceiling(Long.MIN_VALUE);
    }

    public long last() {
        return floor(Long.MAX_VALUE);
    }

    public long pollFirst() {
        long temp = first();
        remove(temp);
        return temp;
    }

    public long pollLast() {
        long temp = last();
        remove(temp);
        return temp;
    }

    private boolean add(TreeSetNode toAdd) {
        if (root == null) {
            root = toAdd;
            return true;
        }

        TreeSetNode tr = root;
        while (true) {
            if (toAdd.compareTo(tr) < 0) {
                if (tr.left == null) {
                    tr.left = toAdd;
                    toAdd.parent = tr;
                    break;
                } else tr = tr.left;
            } else if (allowDuplicates || toAdd.compareTo(tr) > 0) {
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

    private boolean remove(TreeSetNode node) {
        if (root.size() == 1) {
            root = null;
            return true;
        }
        TreeSetNode p;

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

            TreeSetNode tr = node.parent;
            while (tr != null) {
                root = tr.finalisePosition(root);
                tr = tr.parent;
            }
            return true;
        }


        TreeSetNode tr = p.parent;
        while (tr != node) {
            root = tr.finalisePosition(root);
            tr = tr.parent;
        }

        p.parent = node.parent;
        node.parent = null;
        p.left = node.left;
        node.left = null;
        p.right = node.right;
        node.right = null;

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

    private TreeSetNode floor(TreeSetNode node) {
        if (root == null) return null;

        TreeSetNode tr = root;
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

    private TreeSetNode ceiling(TreeSetNode node) {
        if (root == null) return null;

        TreeSetNode tr = root;
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

    private TreeSetNode lower(TreeSetNode node) {
        TreeSetNode fl = floor(node);
        if (fl != null && node.compareTo(fl) == 0) fl = prev(fl);
        return fl;
    }

    private TreeSetNode higher(TreeSetNode node) {
        TreeSetNode cl = ceiling(node);
        if (cl != null && node.compareTo(cl) == 0) cl = next(cl);
        return cl;
    }

    private TreeSetNode navigateTo(TreeSetNode node, int ind) {
        if (TreeSetNode.findSize(node.left) > ind) return navigateTo(node.left, ind);
        ind -= TreeSetNode.findSize(node.left);
        if (ind == 0) return node;
        ind -= 1;
        return navigateTo(node.right, ind);
    }

    private int countFloorNodes(TreeSetNode node, TreeSetNode key) {
        if (node == null) return 0;
        else if (node.compareTo(key) > 0)
            return countFloorNodes(node.left, key);
        else
            return TreeSetNode.findSize(node.left) + 1 + countFloorNodes(node.right, key);
    }

    private long sumUpFloorNodes(TreeSetNode node, TreeSetNode key) {
        if (node == null) return 0;
        else if (node.compareTo(key) > 0)
            return sumUpFloorNodes(node.left, key);
        else
            return TreeSetNode.findSum(node.left) + node.value + sumUpFloorNodes(node.right, key);
    }

    private TreeSetNode prev(TreeSetNode node) {
        TreeSetNode parent;

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

    private TreeSetNode next(TreeSetNode node) {
        TreeSetNode parent;

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
    private ArrayList<TreeSetNode> dfsTrav = new ArrayList<>();

    public Iterator<Long> iterator() {
        dfsTrav.clear();
        dfs(root);
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

    private void dfs(TreeSetNode node) {
        if (node == null) return;
        dfs(node.left);
        dfsTrav.add(node);
        dfs(node.right);
    }

    public String toString() {
        dfsTrav.clear();
        dfs(root);
        return dfsTrav.toString();
    }
}
