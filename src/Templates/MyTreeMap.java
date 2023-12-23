package Templates;

import java.util.*;

public class MyTreeMap implements Iterable<Map.Entry<Long, Long>> {
    static final long DUMMY_VALUE = -7;
    TreeMapNode root;

    public boolean isAllowDuplicates() {
        return allowDuplicates;
    }

    boolean allowDuplicates;

    public MyTreeMap() {
        root = null;
    }

    public MyTreeMap(boolean allowDupli) {
        root = null;
        allowDuplicates = allowDupli;
    }

    public boolean add(long key) {
        return put(key, DUMMY_VALUE);
    }

    public boolean put(long key, long value) {
        return add(new TreeMapNode(key, value));
    }

    public boolean remove(long key) {
        TreeMapNode actual = new TreeMapNode(key, DUMMY_VALUE), toRemove = floor(actual);
        if (toRemove.compareTo(actual) != 0) return false;
        return remove(toRemove);
    }

    public boolean pollAtIndex(int index) {
        if (index < 0 || index >= size()) return false;
        else return remove(navigateTo(root, index));
    }

    public boolean containsKey(long key) {
        TreeMapNode fl = floor(new TreeMapNode(key, DUMMY_VALUE));
        return fl != null && fl.key == key;
    }

    public long floorKey(long key) {
        return floor(new TreeMapNode(key, DUMMY_VALUE)).key;
    }

    public Map.Entry<Long, Long> floor(long key) {
        TreeMapNode floorNode = floor(new TreeMapNode(key, DUMMY_VALUE));
        return new AbstractMap.SimpleEntry<>(floorNode.key, floorNode.value);
    }

    public long ceilingKey(long key) {
        return ceiling(new TreeMapNode(key, DUMMY_VALUE)).key;
    }

    public Map.Entry<Long, Long> ceiling(long key) {
        TreeMapNode ceilingNode = ceiling(new TreeMapNode(key, DUMMY_VALUE));
        return new AbstractMap.SimpleEntry<>(ceilingNode.key, ceilingNode.value);
    }

    public long lowerKey(long key) {
        return lower(new TreeMapNode(key, DUMMY_VALUE)).key;
    }

    public Map.Entry<Long, Long> lower(long key) {
        TreeMapNode lowerNode = lower(new TreeMapNode(key, DUMMY_VALUE));
        return new AbstractMap.SimpleEntry<>(lowerNode.key, lowerNode.value);
    }

    public long higherKey(long key) {
        return higher(new TreeMapNode(key, DUMMY_VALUE)).key;
    }

    public Map.Entry<Long, Long> higher(long key) {
        TreeMapNode higherNode = higher(new TreeMapNode(key, DUMMY_VALUE));
        return new AbstractMap.SimpleEntry<>(higherNode.key, higherNode.value);
    }

    public long elementAtIndex(int index) {
        if (index < 0 || index >= size()) return 7 / 0;
        else return navigateTo(root, index).key;
    }

    public Map.Entry<Long, Long> pairAtIndex(int index) {
        if (index < 0 || index >= size()) return null;
        TreeMapNode node = navigateTo(root, index);
        return new AbstractMap.SimpleEntry<>(node.key, node.value);
    }

    public int countFloorNodes(long key) {
        return countFloorNodes(root, new TreeMapNode(key, DUMMY_VALUE));
    }

    public long sumUpFloorNodes(long key) {
        return sumUpFloorNodes(root, new TreeMapNode(key, DUMMY_VALUE));
    }

    public int size() {
        return root == null ? 0 : root.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long firstKey() {
        return ceiling(Long.MIN_VALUE).getKey();
    }

    public Map.Entry<Long, Long> first() {
        return ceiling(Long.MIN_VALUE);
    }

    public long lastKey() {
        return floor(Long.MAX_VALUE).getKey();
    }

    public Map.Entry<Long, Long> last() {
        return floor(Long.MAX_VALUE);
    }

    public long pollFirst() {
        long temp = firstKey();
        remove(temp);
        return temp;
    }

    public long pollLast() {
        long temp = lastKey();
        remove(temp);
        return temp;
    }

    private boolean add(TreeMapNode toAdd) {
        if (root == null) {
            root = toAdd;
            return true;
        }

        TreeMapNode tr = root;
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
            } else {
                tr.value = toAdd.value;
                return false;
            }
        }

        while (toAdd != null) {
            root = toAdd.finalisePosition(root);
            toAdd = toAdd.parent;
        }
        return true;
    }

    private boolean remove(TreeMapNode node) {
        if (root.size() == 1) {
            root = null;
            return true;
        }
        TreeMapNode p;

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

            TreeMapNode tr = node.parent;
            while (tr != null) {
                root = tr.finalisePosition(root);
                tr = tr.parent;
            }
            return true;
        }


        TreeMapNode tr = p.parent;
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

    private TreeMapNode floor(TreeMapNode node) {
        if (root == null) return null;

        TreeMapNode tr = root;
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

    private TreeMapNode ceiling(TreeMapNode node) {
        if (root == null) return null;

        TreeMapNode tr = root;
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

    private TreeMapNode lower(TreeMapNode node) {
        TreeMapNode fl = floor(node);
        if (fl != null && node.compareTo(fl) == 0) fl = prev(fl);
        return fl;
    }

    private TreeMapNode higher(TreeMapNode node) {
        TreeMapNode cl = ceiling(node);
        if (cl != null && node.compareTo(cl) == 0) cl = next(cl);
        return cl;
    }

    private TreeMapNode navigateTo(TreeMapNode node, int ind) {
        if (TreeMapNode.findSize(node.left) > ind) return navigateTo(node.left, ind);
        ind -= TreeMapNode.findSize(node.left);
        if (ind == 0) return node;
        ind -= 1;
        return navigateTo(node.right, ind);
    }

    private int countFloorNodes(TreeMapNode node, TreeMapNode key) {
        if (node == null) return 0;
        else if (node.compareTo(key) > 0)
            return countFloorNodes(node.left, key);
        else
            return TreeMapNode.findSize(node.left) + 1 + countFloorNodes(node.right, key);
    }

    private long sumUpFloorNodes(TreeMapNode node, TreeMapNode key) {
        if (node == null) return 0;
        else if (node.compareTo(key) > 0)
            return sumUpFloorNodes(node.left, key);
        else
            return TreeMapNode.findSum(node.left) + node.key + sumUpFloorNodes(node.right, key);
    }

    private TreeMapNode prev(TreeMapNode node) {
        TreeMapNode parent;

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

    private TreeMapNode next(TreeMapNode node) {
        TreeMapNode parent;

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
    private ArrayList<TreeMapNode> dfsTrav = new ArrayList<>();

    @Override
    public Iterator<Map.Entry<Long, Long>> iterator() {
        dfsTrav.clear();
        dfs(root);
        itrInd = 0;
        Iterator<Map.Entry<Long, Long>> iterator = new Iterator<>() {
            @Override
            public boolean hasNext() {
                return itrInd < size();
            }

            @Override
            public Map.Entry<Long, Long> next() {
                TreeMapNode node = dfsTrav.get(itrInd++);
                return new AbstractMap.SimpleEntry<>(node.key, node.value);
            }
        };
        return iterator;
    }

    private void dfs(TreeMapNode node) {
        if (node == null) return;
        dfs(node.left);
        dfsTrav.add(node);
        dfs(node.right);
    }

    @Override
    public String toString() {
        dfsTrav.clear();
        dfs(root);
        return dfsTrav.toString();
    }
}
