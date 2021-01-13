package Templates;

import java.util.ArrayDeque;
import java.util.Arrays;

public class ConvexHull {
    Point[] P;

    public ConvexHull(Point[] p) {
        P = p.clone();
        Arrays.sort(P);
    }

    public long cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    public ArrayDeque<Point> buildLowerHull() {
        ArrayDeque<Point> hull = new ArrayDeque<>();
        Point last = null, secondLast = null;

        for (int i = 0; i < P.length; ++i) {
            while (last != null && secondLast != null && cross(secondLast, last, P[i]) <= 0) {
                last = secondLast;
                secondLast = hull.pollLast();
            }
            if (secondLast != null) hull.addLast(secondLast);
            secondLast = last;
            last = P[i];
        }
        if (secondLast != null) hull.addLast(secondLast);
        if (last != null) hull.addLast(last);

        return hull;
    }

    public ArrayDeque<Point> buildUpperHull() {
        ArrayDeque<Point> hull = new ArrayDeque<>();
        Point last = null, secondLast = null;

        for (int i = P.length; i >= 0; --i) {
            while (last != null && secondLast != null && cross(secondLast, last, P[i]) <= 0) {
                last = secondLast;
                secondLast = hull.pollLast();
            }
            if (secondLast != null) hull.addLast(secondLast);
            secondLast = last;
            last = P[i];
        }
        if (secondLast != null) hull.addLast(secondLast);
        if (last != null) hull.addLast(last);

        return hull;
    }

    public static class Point implements Comparable<Point> {
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
}