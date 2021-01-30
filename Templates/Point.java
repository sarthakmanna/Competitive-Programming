package Templates;

public class Point implements Comparable<Point> {
    public int getIndex() {
        return index;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    int index;
    long x, y;

    public Point(int idx, long X, long Y) {
        index = idx;
        x = X;
        y = Y;
    }

    public int compareTo(Point p) {
        return x != p.x ? Long.compare(x, p.x) : Long.compare(y, p.y);
    }

    public String toString() {
        return "(" + index + " -> " + x + ", " + y + ")";
    }
}