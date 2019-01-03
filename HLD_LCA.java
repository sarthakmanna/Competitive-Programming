import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

class HLD_LCA {
    static final int MAXN = 1000_006;
    static final long defaultValue = Long.MIN_VALUE;
    ArrayList<Integer>[] graph;
    int[] depth, parent, chCount, queue;
    int N, root;
    long[] weight;

    SegmentTree<Long, Long> st;
    int[] treePos, linearTree, segRoot;

    HLD_LCA(ArrayList<Integer>[] g, int[] dep, int[] par, int[] ch,
            int n, int r, long[] wt) {
        graph = g;  depth = dep;    parent = par;   chCount = ch;
        N = n;  root = r;   weight = wt;

        HLDify();
    }

    HLD_LCA(ArrayList<Integer>[] g, int n, int r, long[] wt) {
        graph = g;  N = n;
        root = r;   weight = wt;
        iterativeDFS();

        HLDify();
    }

    HLD_LCA(Edge[] edges, int n, int r) {
        int i;
        N = n;  root = r;

        graph = new ArrayList[N];
        for (i = 0; i < N; ++i) graph[i] = new ArrayList<>();

        for (Edge e : edges) {
            graph[e.u].add(e.v);
            graph[e.v].add(e.u);
        }
        iterativeDFS();

        weight = new long[N];
        for (Edge e : edges) {
            int child = depth[e.u] > depth[e.v] ? e.u : e.v;
            weight[child] = e.weight;
        }

        HLDify();
    }

    static Long operate(Long a, Long b) {
        return Math.max(a, b);
    }

    private void iterativeDFS() {
        parent = new int[N];    depth = new int[N];
        chCount = new int[N];   queue = new int[N];
        Arrays.fill(chCount, 1);

        int i, st = 0, end = 0;
        parent[root] = -1;  depth[root] = 1;    queue[end++] = root;

        while (st < end) {
            int node = queue[st++], h = depth[node] + 1;
            Iterator<Integer> itr = graph[node].iterator();
            while (itr.hasNext()) {
                int ch = itr.next();
                if (depth[ch] > 0) continue;
                depth[ch] = h;
                parent[ch] = node;
                queue[end++] = ch;
            }
        }
        for (i = N - 1; i >= 0; --i)
            if (queue[i] != root)
                chCount[parent[queue[i]]] += chCount[queue[i]];
    }

    private void HLDify() {
        int i, j, treeRoot = -7;

        treePos = new int[N];   linearTree = new int[N];
        segRoot = new int[N];

        Stack<Integer> stack = new Stack<>();
        stack.ensureCapacity(MAXN);
        stack.push(root);
        for (i = 0; !stack.isEmpty(); ++i) {
            int node = stack.pop();
            if (i == 0 || linearTree[i - 1] != parent[node])
                treeRoot = node;
            linearTree[i] = node;
            treePos[node] = i;
            segRoot[node] = treeRoot;

            int bigChild = -7, bigChildPos = -7, lastPos = graph[node].size() - 1;
            for (j = 0; j < graph[node].size(); ++j) {
                int tempNode = graph[node].get(j);
                if (tempNode == parent[node]) continue;
                if (bigChild < 0 || chCount[bigChild] < chCount[tempNode]) {
                    bigChild = tempNode;
                    bigChildPos = j;
                }
            }
            if (bigChildPos >= 0) {
                int temp = graph[node].get(lastPos);
                graph[node].set(lastPos, bigChild);
                graph[node].set(bigChildPos, temp);
            }

            for (int itr : graph[node])
                if (parent[node] != itr)
                    stack.push(itr);
        }

        Long[] respectiveWeights = new Long[N];
        for (i = 0; i < N; ++i)
            respectiveWeights[i] = weight[linearTree[i]];
        st = new SegmentTree<>(respectiveWeights);
    }

    long pathQuery(int node1, int node2, Long key) {
        long ret = defaultValue, temp;
        while (segRoot[node1] != segRoot[node2]) {
            if (depth[segRoot[node1]] > depth[segRoot[node2]]) {
                node1 ^= node2;
                node2 ^= node1;
                node1 ^= node2;
            }

            temp = st.rangeQuery(treePos[segRoot[node2]], treePos[node2], key);
            ret = operate(ret, temp);
            node2 = parent[segRoot[node2]];
        }

        if (treePos[node1] > treePos[node2]) {
            node1 ^= node2;
            node2 ^= node1;
            node1 ^= node2;
        }
        temp = st.rangeQuery(treePos[node1], treePos[node2], key);   // ...treePos[node1] + 1... for Edge Query
        ret = operate(ret, temp);

        return ret;
    }

    void pathUpdate(int node1, int node2, long value) {
        while (segRoot[node1] != segRoot[node2]) {
            if (depth[segRoot[node1]] > depth[segRoot[node2]]) {
                node1 ^= node2;
                node2 ^= node1;
                node1 ^= node2;
            }
            st.rangeUpdate(treePos[segRoot[node2]], treePos[node2], value);
            node2 = parent[segRoot[node2]];
        }
        if (treePos[node1] > treePos[node2]) {
            node1 ^= node2;
            node2 ^= node1;
            node1 ^= node2;
        }
        st.rangeUpdate(treePos[node1], treePos[node2], value);     // ...treePos[node1] + 1... for Edge Update
    }

    long subtreeQuery(int node, Long key) {
        int pos = treePos[node];
        return st.rangeQuery(pos, pos + chCount[node] - 1, key); // ...(pos + 1,... for Edge Query
    }

    void subtreeUpdate(int node, Long value) {
        int pos = treePos[node];
        st.rangeUpdate(pos, pos + chCount[node] - 1, value);    // ...(pos + 1,... for Edge Update
    }

    int getLCA(int node1, int node2) {
        while (segRoot[node1] != segRoot[node2]) {
            if (depth[segRoot[node1]] > depth[segRoot[node2]]) {
                node1 ^= node2;
                node2 ^= node1;
                node1 ^= node2;
            }
            node2 = parent[segRoot[node2]];
        }
        return (depth[node1] < depth[node2]) ? node1 : node2;
    }

    int parentAtDepth(int node, int targetDepth) // depth[node] >= targetDepth
    {
        if (depth[node] < targetDepth)
            return -7;

        while (depth[segRoot[node]] > targetDepth)
            node = parent[segRoot[node]];
        node = linearTree[treePos[node] - depth[node] + targetDepth];

        return node;
    }
}

class Edge {
    int u, v;
    long weight;

    Edge(int a, int b, int wt) {
        u = a; v = b;
        weight = wt;
    }

    public String toString() {
        return "(" + u + ", " + v + " -> " + weight + ")";
    }
}
