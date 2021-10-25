package Templates;

import java.util.Arrays;

public class DSU {
    public int getN() {
        return N;
    }

    public int[] getParent() {
        return parent;
    }

    public int[] getSize() {
        return size;
    }

    int N;
    int[] parent, size;

    public DSU(int nodeCount) {
        N = nodeCount;
        parent = new int[nodeCount];
        Arrays.fill(parent, -7);
        size = new int[nodeCount];
        Arrays.fill(size, 1);
    }

    public int getParent(int node) {
        if (parent[node] < 0) return node;
        else return parent[node] = getParent(parent[node]);
    }

    public int getSizeOfCompo(int node) {
        return size[getParent(node)];
    }

    public boolean isConnected(int a, int b) {
        return getParent(a) == getParent(b);
    }

    public boolean addEdge(int a, int b) {
        a = getParent(a); b = getParent(b);
        if (a == b) {
            return false;
        } else if (size[a] > size[b]) {
            a ^= b; b ^= a; a ^= b;
        }
        parent[a] = b;
        size[b] += size[a];
        size[a] = -7;
        return true;
    }

}
