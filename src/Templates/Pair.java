package Templates;

public class Pair<U, V> implements Comparable<Pair<U, V>> {
    U first;
    V second;
    Helper hp = new Helper();

    public Pair(U u, V v) {
        first = u;
        second = v;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public int compareTo(Pair<U, V> p) {
        int cmp = hp.compare(first, p.first);
        if (cmp == 0) {
            cmp = hp.compare(second, p.second);
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}