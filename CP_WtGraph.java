
import java.io.InputStream;
import java.util.ArrayList;

class CP_Graph
{
    static IO io=new IO(System.in);
    static ArrayList<Integer>[] graph;
    static Edge[] edges;
    static int n,m;
    
    public static void main(String[] args)throws Exception
    {
        for(int tc=io.nextInt();tc>0;--tc)
        {
            int i;
            graph = new ArrayList[n=io.nextInt()];
            edges = new Edge[m=io.nextInt()];

            for(i=0;i<graph.length;++i)graph[i]=new ArrayList<>();
            
            for(i=0;i<m;++i)
            {
                edges[i] = new Edge(io.nextInt()-1,io.nextInt()-1,io.nextLong());
                graph[edges[i].u].add(i);    graph[edges[i].v].add(i);
            }
            
        }
        io.flush();
    }
}
class Edge
{
    int u,v;long wt;
    Edge(int a,int b,long w)
    {
        u=a;    v=b;    wt=w;
    }
    int getOtherVertex(int p)
    {
        return u==p? v: v==p? u: -1;
    }
}
class IO
{
    static byte[] buf = new byte[2048];
    static int index, total;
    static InputStream in;
    static StringBuilder sb = new StringBuilder();
    
 
    IO(InputStream is)
    {
        in = is;
    }
 
    int scan() throws Exception
    {
        if(index>=total){
            index = 0;
            total = in.read(buf);
            if(total<=0)
                return -1;
        }
        return buf[index++];
    }
 
    String next() throws Exception
    {
        int c;
        for(c=scan(); c<=32; c=scan());
        StringBuilder sb = new StringBuilder();
        for(; c>32; c=scan())
            sb.append((char)c);
        return sb.toString();
    }
 
    int nextInt() throws Exception
    {
        int c, val = 0;
        for(c=scan(); c<=32; c=scan());
        boolean neg = c=='-';
        if(c=='-' || c=='+')
            c = scan();
        for(; c>='0' && c<='9'; c=scan())
            val = (val<<3) + (val<<1) + (c&15);
        return neg?-val:val;
    }
    long nextLong() throws Exception
    {
        int c;long val = 0;
        for(c=scan(); c<=32; c=scan());
        boolean neg = c=='-';
        if(c=='-' || c=='+')
            c = scan();
        for(; c>='0' && c<='9'; c=scan())
            val = (val<<3) + (val<<1) + (c&15);
        return neg?-val:val;
    }
    void print(Object a)
    {
        sb.append(a.toString());
    }
    void println(Object a)
    {
        sb.append(a.toString()).append("\n");
    }
    void println()
    {
        sb.append("\n");
    }
    void flush()
    {
        System.out.print(sb);
        sb = new StringBuilder();
    }
}