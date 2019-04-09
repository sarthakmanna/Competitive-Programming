import java.util.*;

class myArrayList implements Iterable<Long> {
    int len;
    long[] A;

    myArrayList(int initialLength) {
        A = new long[Math.max(1, initialLength)];
        len = 0;
    }

    myArrayList(myArrayList src) {
        A = new long[src.A.length];
        System.arraycopy(src.A, 0, A, 0, src.len);
        len = src.len;
    }

    void add(long ele) {
        if (len == A.length) {
            long[] newAr = new long[A.length << 1];
            System.arraycopy(A, 0, newAr, 0, len);
            A = newAr;
        }
        A[len++] = ele;
    }

    void set(int ind, long ele) throws Exception {
        if (ind >= len) throw new ArrayIndexOutOfBoundsException(ind);
        A[ind] = ele;
    }

    long get(int ind) {
        if (ind >= len) throw new ArrayIndexOutOfBoundsException(ind);
        return A[ind];
    }

    long pop() {
        return A[--len];
    }

    void clear() {
        len = 0;
    }

    int size() {
        return len;
    }

    int length() {
        return len;
    }

    boolean isEmpty() {
        return len == 0;
    }

    myArrayList mergeSorted(myArrayList list) {
        myArrayList ret = new myArrayList(size() + list.size());
        int p1 = 0, p2 = 0;
        while (p1 < size() || p2 < list.size()) {
            if (p1 >= size()) ret.add(list.get(p2++));
            else if (p2 >= list.size()) ret.add(get(p1++));
            else if (get(p1) <= list.get(p2)) ret.add(get(p1++));
            else ret.add(list.get(p2++));
        }
        return ret;
    }

    void sort() {
        Arrays.sort(A, 0, len);
    }

    void reverse() {
        for (int i = 0; i < size() >> 1; ++i) {
            int j = size() - 1 - i;
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
        }
    }

    int countFloor(long key) {
        // counts number of elements <= key
        // provided this list is sorted
        if (isEmpty()) return 0;
        int l = 0, r = size() - 1, mid;
        while (true) {
            mid = l + r >> 1;
            if (l + 1 >= r) {
                if (get(r) <= key) return r + 1;
                else if (get(l) > key) return l;
                else return r;
            } else if (get(mid) <= key) l = mid + 1;
            else r = mid;
        }
    }

    int itrInd;

    public Iterator<Long> iterator() {
        itrInd = 0;
        Iterator<Long> iterator = new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return itrInd < size();
            }

            @Override
            public Long next() {
                return get(itrInd++);
            }
        };
        return iterator;
    }

    public myArrayList clone() {
        return new myArrayList(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(A[i]).append(" ");
        return sb.toString().trim();
    }
}
