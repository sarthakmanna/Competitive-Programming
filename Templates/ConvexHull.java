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

        for (int i = P.length - 1; i >= 0; --i) {
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
}
