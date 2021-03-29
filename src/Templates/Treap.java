package Templates;

import java.util.Random;

// BST (on value) + Max heap (on priority)
public class Treap {
    Random rnd = new Random();
    Unit root = null;

    public void addValue(Unit node) {
        Unit curr = root;

        if (root == null) {
            root = node;
        } else {
            while (true) {
                if (node.compareTo(curr) < 0) {
                    if (curr.leftChild == null) {
                        curr.leftChild = node;
                        node.parent = curr;
                        node.readjust();
                        break;
                    } else {
                        curr = curr.leftChild;
                    }
                } else {
                    if (curr.rightChild == null) {
                        curr.rightChild = node;
                        node.parent = curr;
                        node.readjust();
                        break;
                    } else {
                        curr = curr.rightChild;
                    }
                }
            }

            while (root.parent != null) {
                root = root.parent;
            }
        }
    }

    public void deleteNode(Unit node) {
        node.setPriority(Integer.MIN_VALUE + 7);
        node.readjust();

        if (root == node) {
            root = null;
        } else {
            if (node.parent.leftChild == node) node.parent.leftChild = null;
            else if (node.parent.rightChild == node) node.parent.rightChild = null;
            else System.exit(7 / 0);

            node.parent = null;
        }
    }


    private void inorderTraverse(Unit curr, StringBuilder sb) {
        if (curr == null) return;

        if (curr.parent != null) {
            if (curr.parent.priority < curr.priority) System.exit(7 / 0);
        }

        inorderTraverse(curr.leftChild, sb);
        sb.append(curr + ", ");
        inorderTraverse(curr.rightChild, sb);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        inorderTraverse(root, sb);
        String s = sb.toString();
        return "[" + s.substring(0, s.length() - 2) + "]";
    }
}

class Unit implements Comparable<Unit> {
    Unit parent, leftChild, rightChild;
    long value;
    int priority;

    public Unit(long v, int p) {
        value = v;
        priority = p;
    }

    public void setPriority(int newPriority) {
        priority = newPriority;
    }

    private void climbUp() {
        while (parent != null && parent.priority < priority) {
            Unit par = parent;

            if (par.leftChild == this) {
                if (par.parent != null) {
                    if (par.parent.leftChild == par) par.parent.leftChild = this;
                    else if (par.parent.rightChild == par) par.parent.rightChild = this;
                    else System.exit(7 / 0);
                }
                parent = par.parent;
                par.parent = null;

                if (rightChild != null) {
                    rightChild.parent = par;
                }
                par.leftChild = rightChild;
                rightChild = null;

                rightChild = par;
                par.parent = this;
            } else if (par.rightChild == this) {
                if (par.parent != null) {
                    if (par.parent.leftChild == par) par.parent.leftChild = this;
                    else if (par.parent.rightChild == par) par.parent.rightChild = this;
                    else System.exit(7 / 0);
                }
                parent = par.parent;
                par.parent = null;

                if (leftChild != null) {
                    leftChild.parent = par;
                }
                par.rightChild = leftChild;
                leftChild = null;

                leftChild = par;
                par.parent = this;
            } else {
                System.exit(7 / 0);
            }
        }
    }

    public void readjust() {
        climbUp();

        boolean needChecking;
        do {
            needChecking = false;

            if (leftChild != null && leftChild.priority > priority) {
                if (rightChild == null || leftChild.priority >= rightChild.priority) {
                    needChecking = true;
                    leftChild.climbUp();
                }
            }
            if (rightChild != null && rightChild.priority > priority) {
                if (leftChild == null || rightChild.priority >= leftChild.priority) {
                    needChecking = true;
                    rightChild.climbUp();
                }
            }
        } while (needChecking);
    }

    public int compareTo(Unit node) {
        return Long.compare(value, node.value);
    }

    public String toString() {
        return "(" + value + ", " + priority + ")";
    }
}