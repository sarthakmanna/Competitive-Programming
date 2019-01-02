import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

class HLD_LCA
{
    static final int MAXN = 1000_006;
    ArrayList<Integer>[] graph;
    int[] depth, parent, chCount;
    int nodes, root;
    long[] weight;

    SegmentTree<Long, Long> st;
    int[] treePos, linearTree, segRoot;

    HLD_LCA(ArrayList<Integer>[] g, int[] dep, int[] par, int[] ch,
            int n, int r, long[] wt)
    {
        graph = g;  depth = dep;    parent = par;   chCount = ch;
        nodes = n;  root = r;       weight = wt;

        HLDify();
    }

    static Long operate(Long a, Long b) {
        return Math.max(a, b);
    }

    private void HLDify()
    {
        int i, treeRoot = -7;
        treePos = new int[nodes];
        linearTree = new int[nodes];
        segRoot = new int[nodes];

        Stack<Integer> stack = new Stack<>();
        stack.ensureCapacity(MAXN);
        stack.push(root);
        for (i = 0; !stack.isEmpty(); ++i)
        {
            int node = stack.pop();
            if (i == 0 || linearTree[i - 1] != parent[node])
                treeRoot = node;
            linearTree[i] = node;
            treePos[node] = i;
            segRoot[node] = treeRoot;

            Collections.sort(graph[node], (Integer a, Integer b) -> Integer.compare(chCount[a], chCount[b]));
            for (int itr : graph[node])
                if(parent[node] != itr)
                    stack.push(itr);
        }

        Long[] respectiveWeights = new Long[nodes];
        for (i = 0; i < nodes; ++i)
            respectiveWeights[i] = weight[linearTree[i]];
        st = new SegmentTree<>(respectiveWeights);
    }

    long pathQuery(int node1, int node2, Long key)
    {
        long ret = Node.defaultValue, temp;
        while (segRoot[node1] != segRoot[node2])
        {
            if (depth[segRoot[node1]] > depth[segRoot[node2]])
            {
                node1 ^= node2;
                node2 ^= node1;
                node1 ^= node2;
            }

            temp = st.rangeQuery(treePos[segRoot[node2]], treePos[node2], key);
            ret = operate(ret, temp);
            node2 = parent[segRoot[node2]];
        }

        if(treePos[node1] > treePos[node2])
        {
            node1 ^= node2;
            node2 ^= node1;
            node1 ^= node2;
        }
        temp = st.rangeQuery(treePos[node1], treePos[node2], key);   // ...treePos[node1] + 1... for Edge Query
        ret = operate(ret, temp);

        return ret;
    }
    void pathUpdate(int node1, int node2, long value)
    {
        while (segRoot[node1] != segRoot[node2])
        {
            if (depth[segRoot[node1]] > depth[segRoot[node2]])
            {
                node1 ^= node2;
                node2 ^= node1;
                node1 ^= node2;
            }
            st.rangeUpdate(treePos[segRoot[node2]], treePos[node2], value);
            node2 = parent[segRoot[node2]];
        }
        if(treePos[node1] > treePos[node2])
        {
            node1 ^= node2;
            node2 ^= node1;
            node1 ^= node2;
        }
        st.rangeUpdate(treePos[node1], treePos[node2], value);     // ...treePos[node1] + 1... for Edge Update
    }

    int getLCA(int node1, int node2)
    {
        while (segRoot[node1] != segRoot[node2])
        {
            if (depth[segRoot[node1]] > depth[segRoot[node2]])
            {
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
        if(depth[node] < targetDepth)
            return -7;

        while (depth[segRoot[node]] > targetDepth)
            node = parent[segRoot[node]];
        node = linearTree[treePos[node] - depth[node] + targetDepth];

        return node;
    }
}
