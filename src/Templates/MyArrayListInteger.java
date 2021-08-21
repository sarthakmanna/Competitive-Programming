package Templates;

import java.util.*;

public class MyArrayListInteger implements Iterable<Integer> {
    private int len;
    private int[] A;

    public MyArrayListInteger(int initialLength) {
        A = new int[Math.max(1, initialLength)];
        len = 0;
    }

    public MyArrayListInteger(MyArrayListInteger src) {
        A = new int[src.A.length];
        System.arraycopy(src.A, 0, A, 0, src.len);
        len = src.len;
    }

    public void add(int ele) {
        if (len == A.length) {
            int[] newAr = new int[A.length << 1];
            System.arraycopy(A, 0, newAr, 0, len);
            A = newAr;
        }
        A[len++] = ele;
    }

    public void set(int ind, int ele) {
        if (ind >= len) throw new ArrayIndexOutOfBoundsException(ind);
        else if (ind < 0) ind += size();
        A[ind] = ele;
    }

    public int get(int ind) {
        if (ind >= len) throw new ArrayIndexOutOfBoundsException(ind);
        else if (ind < 0) ind += size();
        return A[ind];
    }

    public int pop() {
        return A[--len];
    }

    public void clear() {
        len = 0;
    }

    public int size() {
        return len;
    }

    public int length() {
        return len;
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public MyArrayListInteger mergeSorted(MyArrayListInteger list) {
        MyArrayListInteger ret = new MyArrayListInteger(size() + list.size());
        int p1 = 0, p2 = 0;
        while (p1 < size() || p2 < list.size()) {
            if (p1 >= size()) ret.add(list.get(p2++));
            else if (p2 >= list.size()) ret.add(get(p1++));
            else if (get(p1) <= list.get(p2)) ret.add(get(p1++));
            else ret.add(list.get(p2++));
        }
        return ret;
    }

    public void mergeSmallToLarge(MyArrayListInteger list) {
        if (this.size() >= list.size()) {
            for (int itr : list) this.add(itr);
        } else {
            for (int itr : this) list.add(itr);
            this.A = list.A;
            this.len = list.len;
        }
    }

    public void sort(int fromIndex, int toIndex) {
        Arrays.sort(A, fromIndex, toIndex);
    }

    public void sort() {
        sort(0, size());
    }

    public void sort(int fromIndex, int toIndex, Comparator<Integer> com) {
        Integer[] temp = new Integer[toIndex - fromIndex];
        for (int i = fromIndex, k = 0; i < toIndex; ++i) temp[k++] = A[i];
        Arrays.sort(temp, com);
        for (int i = fromIndex, k = 0; i < toIndex; ++i) A[i] = temp[k++];
    }

    public void reverse() {
        for (int i = 0; i < size() >> 1; ++i) {
            int j = size() - 1 - i;
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
        }
    }

    public int countFloor(int key) {
        // counts number of elements <= key provided this list is sorted
        if (isEmpty()) return 0;
        int l = 0, r = size() - 1, mid;
        while (true) {
            mid = l + r >> 1;
            if (l == mid) {
                if (get(r) <= key) return r + 1;
                else if (get(l) > key) return l;
                else return r;
            } else if (get(mid) <= key) l = mid + 1;
            else r = mid;
        }
    }

    int itrInd;

    public Iterator<Integer> iterator() {
        itrInd = 0;
        Iterator<Integer> iterator = new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return itrInd < size();
            }

            @Override
            public Integer next() {
                return get(itrInd++);
            }
        };
        return iterator;
    }

    public MyArrayListInteger clone() {
        return new MyArrayListInteger(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(A[i]).append(", ");
        return "[" + sb.toString().trim() + "]";
    }
}