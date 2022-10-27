package Templates;

import java.util.Arrays;

public class PrefixSumArray {
    int N;
    long[] A, P;
    private boolean isReady;

    public PrefixSumArray(int size) {
        N = size;
        A = new long[N];
        P = new long[N];
        isReady = true;
    }

    public void precompute() {
        if (isReady) return;
        P[0] = A[0];
        for (int i = 1; i < N; ++i) P[i] = P[i - 1] + A[i];
        isReady = true;
    }

    public void update(int idx, long value) {
        A[idx] = value;
        isReady = false;
    }

    public long elementAtIndex(int idx) {
        return A[idx];
    }

    public long query(int l, int r) {
        precompute();
        long retValue = P[r];
        if (l > 0) retValue -= P[l - 1];
        return retValue;
    }

    public String toString() {
        return Arrays.toString(A);
    }
}
