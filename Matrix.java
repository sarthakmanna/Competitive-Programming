class Matrix {
    long[][] multiply(long[][] A, long[][] B, long MOD)   // returns (A x B) % MOD
    {
        if (A[0].length != B.length)
            return null;
        long[][] ret = new long[A.length][B[0].length];
        int i, j, k;
        
        for (i = 0; i < A.length; ++i)
            for (j = 0; j < A[i].length; ++j)
                A[i][j] = (A[i][j] % MOD + MOD) % MOD;
        for (i = 0; i < B.length; ++i)
            for (j = 0; j < B[i].length; ++j)
                B[i][j] = (B[i][j] % MOD + MOD) % MOD;
            
        for (i = 0; i < ret.length; ++i)
            for (j = 0; j < ret[i].length; ++j)
                for (k = 0; k < B.length; ++k)
                    ret[i][j] = (ret[i][j] + (A[i][k] * B[k][j]) % MOD) % MOD;
        return ret;
    }

    long[][] power(long[][] ar, long exp, long MOD) // 'ar' must be square matrix
    {
        long[][] ret = generateUnit(ar.length);
        while (exp > 0) {
            if ((exp & 1) == 1)
                ret = multiply(ret, ar, MOD);
            exp >>= 1;
            ar = multiply(ar, ar, MOD);
        }
        return ret;
    }

    long[][] generateUnit(int len) {
        long[][] ret = new long[len][len];
        for (int i = 0; i < len; ++i) ret[i][i] = 1;
        return ret;
    }

    long[][] add(long[][] A, long[][] B)    // 'A' & 'B' must have same dimensions
    {
        int i, j;
        long[][] ret = new long[A.length][A[0].length];
        for (i = 0; i < ret.length; ++i)
            for (j = 0; j < ret[i].length; ++j)
                ret[i][j] = A[i][j] + B[i][j];
        return ret;
    }
}
