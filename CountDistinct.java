import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class CountDistinct
{
    Helper_SQRT help;
    long[] values;
    int[] nextInd;
    HashMap<Long, TreeSet<Integer>> record;

    CountDistinct(long[] ar)
    {
        int i;
        values = new long[ar.length];
        nextInd = new int[ar.length];
        record = new HashMap<>();

        addIfAbsent(record, 0);
        for(i = 0; i < ar.length; ++i)
        {
            nextInd[i] = Integer.MAX_VALUE;
            record.get(0l).add(i);
        }

        help = new Helper_SQRT(nextInd);
        for(i = 0; i < ar.length; ++i)
            update(i, ar[i]);
    }

    int countBetween(int l, int r)
    {
        return help.rangeQuery(l, r, r);
    }

    void update(int index, long newVal)
    {
        long oldVal = values[index];

        if(record.get(oldVal).lower(index) != null)
        {
            int lower = record.get(oldVal).lower(index);
            nextInd[lower] = nextInd[index];
            help.ptUpdate(lower, nextInd[index]);
        }
        record.get(oldVal).remove(index);

        if(record.get(oldVal).size() <= 1)
            record.remove(oldVal);

        values[index] = newVal;

        addIfAbsent(record, newVal);

        if(record.get(newVal).lower(index) != null)
        {
            int lower = record.get(newVal).lower(index);
            nextInd[lower] = index;
            help.ptUpdate(lower, index);
        }
        int higher = record.get(newVal).higher(index);
        nextInd[index] = higher;
        help.ptUpdate(index, higher);

        record.get(newVal).add(index);
    }

    void addIfAbsent(HashMap<Long, TreeSet<Integer>> hm, long val)
    {
        if(hm.containsKey(val))
            return;
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(Integer.MAX_VALUE);
        hm.put(val, ts);
    }

    @Override
    public String toString()
    {
        StringBuilder ret = new StringBuilder();
        ret.append(Arrays.toString(values)).append("\n");
        ret.append(Arrays.toString(nextInd)).append("\n");
        ret.append(record.toString()).append("\n\n");
        return ret.toString();
    }
}



class Helper_SQRT
{
    static final int SQRT = 1003;
    int[] indices;
    int[][] sqrtSegs = new int[SQRT][SQRT];

    Helper_SQRT(int[] ind)
    {
        int i;
        indices = ind;
        
        for(i = 0; i <= ind.length / SQRT; ++i)
            prepareSegment(i);
    }
    
    void prepareSegment(int ind)
    {
        int i, l = ind * SQRT, r = Math.min((l + SQRT), indices.length) - 1;
        for(i = 0; l <= r; ++l, ++i)
            sqrtSegs[ind][i] = indices[l];
        Arrays.sort(sqrtSegs[ind]);
    }
    
    void ptUpdate(int i, int v)
    {
        indices[i] = v;
        prepareSegment(i / SQRT);
    }
    
    int rangeQuery(int l, int r, int val)
    {
        int ret = 0;
        
        for(; l <= r && l % SQRT > 0; ++l)
            if(indices[l] > val)
                ++ret;
        
        for(; l + SQRT - 1 <= r && l % SQRT == 0; l += SQRT)
            ret += SQRT - binarySearch(sqrtSegs[l / SQRT], val);
        
        for(; l <= r; ++l)
            if(indices[l] > val)
                ++ret;
        
        return ret;
    }
    int binarySearch(int[] ar, int val)
    {
        int l = 0, r = ar.length - 1, mid = -7;
        
        while(l <= r)
        {
            mid = (l + r) >> 1;
            if(ar[l] > val)
            {
                mid = l;
                break;
            }
            else if(ar[r] <= val)
                return r + 1;
            else if(ar[mid] <= val)
                l = mid + 1;
            else
                r = mid - 1;
        }
        
        while(mid > 0 && ar[mid - 1] > val)
            --mid;
        return mid;
    }
}