package Templates;

public class Tuple<A, B, C> implements Comparable<Tuple<A, B, C>> {
    A first;
    B second;
    C third;
    Helper hp = new Helper();

    public Tuple(A a, B b, C c) {
        first = a; second = b; third = c;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public void setThird(C third) {
        this.third = third;
    }

    @Override
    public int compareTo(Tuple<A, B, C> tuple) {
        int cmp = hp.compare(first, tuple.first);
        if (cmp == 0) {
            cmp = hp.compare(second, tuple.second);
        }
        if (cmp == 0) {
            cmp = hp.compare(third, tuple.third);
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", "+ third + ")";
    }
}
