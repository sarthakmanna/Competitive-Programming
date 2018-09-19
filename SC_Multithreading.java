
import java.io.InputStream;
import java.util.LinkedList;

class SC_Multithreading
{
    static final int cpu=4;
    static IO io=new IO(System.in);
    static Solver[] recordIni;
    static Object[] answer;
    
    public static void main(String[] args)throws Exception
    {
        int i,j;
        recordIni=new Solver[io.nextInt()];
        answer=new Object[recordIni.length];
        LinkedList<Integer> list=new LinkedList<>();
        
        for(i=0;i<recordIni.length;++i)
        {
            recordIni[i]=new Solver(answer,i);
            recordIni[i].start();
            list.add(i);
            
            while(list.size()>=cpu)
                for(j=0;j<list.size();++j)
                    if(!recordIni[list.get(j)].isAlive())
                        list.remove(j--);
        }
        for(i=0;i<recordIni.length;++i)
        {
            while(recordIni[i].isAlive());
            io.println(answer[i]);
        }
        io.flush();
    }
}
class Solver extends Thread
{
    Object[] answer;int index;
    
    Solver(Object[] ans,int ind)
    {
        answer=ans; index=ind;
    }
    @Override
    public void run()
    {
        answer[index]="Legendary HELLO WORLD !!!";
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