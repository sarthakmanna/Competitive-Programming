package Templates;

public class Pair<U, V> implements Comparable<Pair<U, V>> {
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

    U first;
    V second;

    public Pair(U u, V v) {
        first = u;
        second = v;
    }

    public int compareTo(Pair<U, V> p) {
        if (first instanceof Integer) {
            return Integer.compare((int) first, (int) p.getFirst());
        } else if (first instanceof Long) {
            return Long.compare((long) first, (long) p.getFirst());
        } else if (first instanceof String) {
            return ((String) first).compareTo((String) p.getFirst());
        } else {
            return 5 / 0;
        }
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
