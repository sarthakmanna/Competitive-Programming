package Templates;


import java.util.Set;

abstract public class Centroid_Decomposition {
    int N;
    Set<Integer>[] graph;

    public int[] getChildCount() {
        return childCount;
    }

    int[] childCount;

    public Centroid_Decomposition(Set<Integer>[] g, int n) {
        N = n;
        graph = g;
        childCount = new int[N];
    }

    public abstract void decompose(int node);

    /*
        Centroid_Decomposition cd = new Centroid_Decomposition(graph, N) {
            @Override
            public void decompose(int node) {
                fillChildCount(node, -7);
                node = findCentroid(node, -7, getChildCount(node) >> 1);

                // <do_stuff>
                for (int itr : graph[node]) {

                }
                // </do_stuff>

                for (int itr : graph[node]) {
                    graph[itr].remove(node);
                    decompose(itr);
                    graph[itr].add(node);
                }
            }

        };
        cd.decompose(0);
     */

    public int getChildCount(int node) {
        return childCount[node];
    }

    public void fillChildCount(int node, int par) {
        childCount[node] = 1;
        for (int itr : graph[node]) if (itr != par) {
            fillChildCount(itr, node);
            childCount[node] += childCount[itr];
        }
    }

    public int findCentroid(int node, int par, final int targetSz) {
        for (int itr : graph[node]) if (itr != par && childCount[itr] > targetSz) {
            return findCentroid(itr, node, targetSz);
        }
        return node;
    }
}