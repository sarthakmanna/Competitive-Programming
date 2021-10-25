package Templates;

import java.util.ArrayDeque;
import java.util.Arrays;

public class ConvexHull {
    Pair<Long, Long>[] P;
    boolean includeLinear;

    public ConvexHull(Pair<Long, Long>[] p) {
        includeLinear = false;
        P = p.clone();
        Arrays.sort(P);
    }

    public ConvexHull(Pair<Long, Long>[] p, boolean incLinear) {
        includeLinear = incLinear;
        P = p.clone();
        Arrays.sort(P);
    }

    public long cross(Pair<Long, Long> O, Pair<Long, Long> A, Pair<Long, Long> B) {
        return (A.getFirst() - O.getFirst()) * (B.getSecond() - O.getSecond())
                - (A.getSecond() - O.getSecond()) * (B.getFirst() - O.getFirst());
    }

    public boolean needToPop(Pair<Long, Long> prev, Pair<Long, Long> curr, Pair<Long, Long> toAdd) {
        if (includeLinear) {
            return cross(prev, curr, toAdd) < 0;
        } else {
            return cross(prev, curr, toAdd) <= 0;
        }
    }

    public ArrayDeque<Pair<Long, Long>> buildLowerHull() {
        ArrayDeque<Pair<Long, Long>> hull = new ArrayDeque<>();
        Pair<Long, Long> last = null, secondLast = null;

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

    public ArrayDeque<Pair<Long, Long>> buildUpperHull() {
        ArrayDeque<Pair<Long, Long>> hull = new ArrayDeque<>();
        Pair<Long, Long> last = null, secondLast = null;

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
