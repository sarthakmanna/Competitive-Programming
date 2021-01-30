package Templates;

public class Pair<U, V> {
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

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
