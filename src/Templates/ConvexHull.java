package Templates;

import java.util.ArrayDeque;
import java.util.Arrays;

public class ConvexHull {
    Point[] P;
    boolean includeLinear;

    public ConvexHull(Point[] p) {
        includeLinear = false;
        P = p.clone();
        Arrays.sort(P);
    }

    public ConvexHull(Point[] p, boolean incLinear) {
        includeLinear = incLinear;
        P = p.clone();
        Arrays.sort(P);
    }

    public long cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    public boolean needToPop(Point prev, Point curr, Point toAdd) {
        if (includeLinear) {
            return cross(prev, curr, toAdd) < 0;
        } else {
            return cross(prev, curr, toAdd) <= 0;
        }
    }

    public ArrayDeque<Point> buildLowerHull() {
        ArrayDeque<Point> hull = new ArrayDeque<>();
        Point last = null, secondLast = null;

        for (int i = 0; i < P.length; ++i) {
            while (last != null && secondLast != null && needToPop(secondLast, last, P[i])) {
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
            while (last != null && secondLast != null && needToPop(secondLast, last, P[i])) {
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
