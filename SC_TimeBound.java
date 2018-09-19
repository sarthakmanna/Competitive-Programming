
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

class SC_TimeBound
{
    static Timer tim=new Timer();
    static TimerTask tt=new TimerTask()
    {
        @Override
        public void run()
        {
            io.flush();System.exit(0);
        }
    };
    SC_TimeBound()
    {
        tim.schedule(tt, time);
    }
    
    static IO io=new IO(System.in);
    static final int time = 2000;
    public static void main(String[] args)throws Exception
    {
        new SC_TimeBound();
        for(;;)
        {
            
        }
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