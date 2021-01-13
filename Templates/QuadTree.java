package Templates;

import java.util.*;

public class QuadTree {
    int N, H;

    public int getN() {
        return N;
    }

    public int getH() {
        return H;
    }

    public long[][][] getTree() {
        return tree;
    }

    long[][][] tree;

    public QuadTree(int r, int c) {
        int n = Math.max(r, c);
        N = H = 1;
        while (N < n) {
            N <<= 1;
            ++H;
        }
        tree = new long[H][N][N];
    }

    public void pointUpdate(int r, int c, long value) {
        tree[H - 1][r][c] += value;

        for (int h = H - 1; h > 0; --h) {
            int px = r >> 1, py = c >> 1;
            tree[h - 1][px][py] += value;
            r = px;
            c = py;
        }
    }

    public long rangeQuery(int qtlx, int qtly, int qbrx, int qbry, int key) {
        return rangeQuery(0, 0, 0, 0, 0,
                N - 1, N - 1, qtlx, qtly, qbrx, qbry, key);
    }

    private long rangeQuery(int h, int px, int py, int tlx, int tly, int brx, int bry,
                            int qtlx, int qtly, int qbrx, int qbry, int key) {
        if (qbrx < tlx || qbry < tly || qtlx > brx || qtly > bry) {
            return 0;
        } else if (qtlx <= tlx && qtly <= tly && qbrx >= brx && qbry >= bry) {
            return tree[h][px][py];
        } else {
            int px2 = px << 1, py2 = py << 1, midx = tlx + brx >> 1, midy = tly + bry >> 1;
            return rangeQuery(h + 1, px2, py2, tlx, tly, midx, midy, qtlx, qtly, qbrx, qbry, key)
                    + rangeQuery(h + 1, px2, py2 + 1, tlx, midy + 1, midx, bry, qtlx, qtly, qbrx, qbry, key)
                    + rangeQuery(h + 1, px2 + 1, py2, midx + 1, tly, brx, midy, qtlx, qtly, qbrx, qbry, key)
                    + rangeQuery(h + 1, px2 + 1, py2 + 1, midx + 1, midy + 1, brx, bry, qtlx, qtly, qbrx, qbry, key);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (long[][] matrix : tree) {
            for (long[] row : matrix) {
                sb.append(Arrays.toString(row)).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}