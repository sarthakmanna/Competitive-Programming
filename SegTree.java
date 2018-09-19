
import java.util.Arrays;

class SegmentTree
{
    static final int MAXN = 100_005;
    
    void printTree()
    {
        for(int i=0;i<segTree.length;++i)
        {
            for(int j=0;j<segTree[i].length;++j)
                System.out.print("("+segTree[i][j]+","+pending[i][j]+")  ");
            System.out.println();
        }
    }
    
    final long dummyNode = Long.MAX_VALUE, dummyLazy = 0;
    long nodeToNode(long a,long b)
    {
        return Math.min(a, b);
    }
    long lazyToLazy(long a,long b)
    {
        return a+b;
    }
    long lazyToNode(long segVal,long lazyVal, int i, int j)
    {
        /*
        int ri = (1 << i) * j, rj = (1 << i) * (j + 1);
        rj = Math.min(rj, segTree[0].length) - 1;
        return segVal + lazyVal * (rj - ri + 1);
        */
        return segVal + lazyVal;
    }
    long valToLazy(long a)
    {
        return a;
    }
    long updValToNode(long segVal,long toAdd,int arI,int arJ,int ti,int tj)
    {
        /*
        int ri=(1<<arI)*arJ, rj=(1<<arI)*(arJ+1)-1;
        ri= Math.max(ri, ti);   rj=Math.min(rj, tj);
        return segVal + toAdd*(rj-ri+1);
        */
        return segVal;
    }
    
    long[][] segTree,pending;
    void segmentify(long[] data)
    {
        int i,j;
        i = clLog2(data.length) +1;
        segTree = new long[i][];    pending = new long[i][];
        
        segTree[0] = new long[data.length];
        System.arraycopy(data, 0, segTree[0], 0, data.length);

        pending[0] = new long[data.length];
        Arrays.fill(pending[0], dummyLazy);
        
        for(i=1; i<segTree.length; ++i)
        {
            j = (segTree[i-1].length + 1) >> 1;
            segTree[i] = new long[j];
            pending[i] = new long[j];   Arrays.fill(pending[i], dummyLazy);
            
            for(j=0; j<segTree[i].length; ++j)
                fillTreeAt(i,j);
        }
    }
    void fillTreeAt(int i,int j)
    {
        pushUp(i-1,j<<1);
        if((j<<1) +1 >= segTree[i-1].length)
            segTree[i][j] = segTree[i-1][j<<1];
        else
        {
            segTree[i][j] = nodeToNode(segTree[i-1][j<<1], segTree[i-1][(j<<1) +1]);
            pushUp(i-1, (j<<1) +1);
        }
    }
    void ptUpdate(int index,long val)
    {
        segTree[0][index]=val;
        for(int i=1;i<segTree.length;++i)
            fillTreeAt(i,index>>=1);
    }
    void pushUp(int i,int j)
    {
        if(pending[i][j] == dummyLazy)return;
        segTree[i][j] = lazyToNode(segTree[i][j],pending[i][j], i, j);
        int ch = j<<1, jk;
        if(i>0)
            for(jk=0;jk<2 && ch+jk<pending[i-1].length;++jk)
                pending[i-1][ch+jk] = lazyToLazy(pending[i-1][ch+jk], pending[i][j]);
        pending[i][j] = dummyLazy;
    }
    
    int[][] stack=new int[2][MAXN];
    long rangeQuery(int ti,int tj)
    {
        long ans = dummyNode;

        if(ti > tj)
            return ans;

        int top=0,p,q,i,j;
        stack[0][top]=segTree.length-1; stack[1][top]=0;    ++top;
        while(top>0)
        {
            --top;  
            p = stack[0][top];  q = stack[1][top];
            i = (1<<p)*q;       j = (1<<p)*(q+1) -1;
            pushUp(p,q);
            
            if(ti > j || tj < i)continue;
            if(ti <= i && tj >= j)
                ans = nodeToNode(ans,segTree[p][q]);
            else
            {
                --p;                    q<<=1;
                stack[0][top] = p;      stack[1][top] = q;  ++top;
                if(q+1 < segTree[p].length)
                {
                    stack[0][top] = p;  stack[1][top] = q+1;    ++top;
                }
            }
        }
        return ans;
    }
    void rangeUpdate(int ti,int tj,long val)
    {
        if(ti > tj)
            return;

        int top=0,p,q,i,j;
        stack[0][top]=segTree.length-1; stack[1][top]=0;    ++top;
        while(top>0)
        {
            --top;  
            p = stack[0][top];  q = stack[1][top];
            i = (1<<p)*q;       j = (1<<p)*(q+1) -1;
            pushUp(p,q);
            
            if(ti > j || tj < i)continue;
            if(ti <= i && tj >= j)
            {
                pending[p][q] = lazyToLazy(pending[p][q], valToLazy(val));
                pushUp(p,q);
                int jk = p+1, ch = q;
                for(;jk<segTree.length;++jk)
                    fillTreeAt(jk,ch>>=1);
            }
            else
            {
                segTree[p][q] = updValToNode(segTree[p][q],val,p,q,ti,tj);
                --p;                    q<<=1;
                stack[0][top] = p;      stack[1][top] = q;  ++top;
                if(q+1 < segTree[p].length)
                {
                    stack[0][top] = p;  stack[1][top] = q+1;    ++top;
                }
            }
        }
    }
    int clLog2(int num)
    {
        --num;
        num |= num >> 1;
        num |= num >> 2;
        num |= num >> 4;
        num |= num >> 8;
        num |= num >> 16;
        return Integer.bitCount(num);
    }
    int flLog2(int num)
    {
        num |= num >> 1;
        num |= num >> 2;
        num |= num >> 4;
        num |= num >> 8;
        num |= num >> 16;
        return Integer.bitCount(num)-1;
    }
}