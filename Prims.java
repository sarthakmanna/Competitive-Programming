
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

class Prims_MST
{
    int nodes;
    ArrayList<Integer>[] graph;
    Edge[] edges;
    
    final Comparator<Integer> COM = new Comparator<Integer>()
    {
        @Override
        public int compare(Integer a, Integer b)
        {
            return Long.compare(edges[a].val, edges[b].val);
        }
    };
    
    Prims_MST(int n, Edge[] all)
    {
        int i;
        edges = all;
        graph = new ArrayList[nodes = n];
        for(i = 0; i < nodes; ++i)
            graph[i] = new ArrayList<>();
        for(i = 0; i < edges.length; ++i)
            if(edges[i].u != edges[i].v)
            {
                graph[edges[i].u].add(i);
                graph[edges[i].v].add(i);
            }
    }
    Prims_MST(int n, Edge[] all, ArrayList<Integer>[] gr)
    {
        nodes = n;  edges = all;    graph = gr;
    }
    
    Edge[] MST()
    {
        return MST(0);
    }
    Edge[] MST(int root)
    {
        PriorityQueue<Integer> qu = new PriorityQueue<>(COM);
        boolean[] vis = new boolean[nodes];
        Edge[] ret = new Edge[nodes - 1];
        int end = 0;
        
        addToQue(qu, root, vis);
        while(qu.size() > 0)
        {
            Edge top = edges[qu.poll()];
            if(vis[top.u] && vis[top.v])continue;
            
            ret[end++] = top;
            
            if(vis[top.u])addToQue(qu, top.v, vis);
            else addToQue(qu, top.u, vis);
        }
        return ret;
    }
    void addToQue(PriorityQueue<Integer> qu, int toAdd, boolean[] vis)
    {
        if(vis[toAdd])return;
        vis[toAdd] = true;
        
        int next;
        Iterator<Integer> itr = graph[toAdd].iterator();
        while(itr.hasNext())
            if(!vis[edges[next = itr.next()].getOtherVertex(toAdd)])
                qu.add(next);
    }
}


class Edge
{
    int u,v;long val;
    Edge(int a,int b,long wt)
    {
        u=a;    v=b;    val=wt;
    }
    int getOtherVertex(int p)
    {
        return u==p? v: v==p? u: -1;
    }
    String getString()
    {
        return "(" + u + ", " + v + " --> " + val + ")";
    }
}