
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class Dijkstra
{
    final long MAX = (long) (1e15);
    ArrayList<Integer>[] graph;
    Edge[] edges;
    int n;
    
    Dijkstra(ArrayList<Integer>[] g, Edge[] e, int no)
    {
        graph = g;  edges = e;  n = no;
    }
    
    long[] minDist;
    final Comparator<Integer> com = new Comparator<Integer>()
    {
        @Override
        public int compare(Integer a, Integer b)
        {
            if(minDist[a] == minDist[b])
                return Integer.compare(a, b);
            return Long.compare(minDist[a], minDist[b]);
        }
    };
    TreeSet<Integer> ts = new TreeSet<>(com);
    
    void calcMinDis(int startNode)  // stores distance between all nodes and 'startNode' in 'minDist'
    {
        int i, node;

        minDist = new long[n];
        Arrays.fill(minDist, MAX);
        minDist[startNode] = 0;

        boolean[] marked = new boolean[n];
        for(i = 0; i < n; ++i)
            ts.add(i);

        while(ts.size() > 0)
        {
            marked[node = ts.pollFirst()] = true;

            Iterator<Integer> itr = graph[node].iterator();
            while(itr.hasNext())
            {
                Edge ed = edges[itr.next()];
                int ov = ed.getOtherVertex(node);
                if(marked[ov] || minDist[node] + ed.wt >= minDist[ov])
                    continue;

                ts.remove(ov);
                minDist[ov] = minDist[node] + ed.wt;
                ts.add(ov);
            }
        }
    }
    long getDist(int node)
    {
        return minDist[node];
    }
}
class Edge
{
    int u,v;    long wt;
    Edge(int a,int b,long val){u=a;v=b;wt=val;}
    int getOtherVertex(int a){return a==u? v: a==v? u: -1;}
    String getString(){return "("+u+", "+v+" -> "+wt+")";}
}