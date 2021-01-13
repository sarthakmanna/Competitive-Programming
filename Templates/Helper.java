package Templates;

import java.util.*;

public class Helper {
    final long MOD;
    final int MAXN;
    final Random rnd;

    public Helper(long mod, int maxn) {
        MOD = mod;
        MAXN = maxn;
        rnd = new Random();
    }

    public static int[] getSieve() {
        return sieve;
    }

    public static ArrayList<Integer> getPrimes() {
        return primes;
    }

    public static int[] sieve;
    public static ArrayList<Integer> primes;

    public void setSieve() {
        if (sieve == null || sieve.length < MAXN) {
            primes = new ArrayList<>();
            sieve = new int[MAXN];
            int i, j;
            for (i = 2; i < MAXN; ++i) {
                if (sieve[i] == 0) {
                    primes.add(i);
                    for (j = i; j < MAXN; j += i) {
                        sieve[j] = i;
                    }
                }
            }
        }
    }

    public boolean isPrime(int number) {
        setSieve();
        return number <= 1 ? false : sieve[number] == number;
    }

    // https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
    public boolean isProbablePrime(int number, int certainty) {
        if (number < MAXN) return isPrime(number);
        else if ((number & 1) == 0) return false;

        long d = number - 1;
        int r = 0;
        while ((d & 1) == 0) {
            ++r;
            d >>= 1;
        }
        while (--certainty >= 0) { // witness loop
            long a = getRandomInRange(2, Math.min(Integer.MAX_VALUE - 7, number - 2));
            long x = pow(a, d, number);

            if (x == 1 || x == number - 1) {
                continue;
            } else {
                boolean witnessFound = false;
                for (int i = 1; i < r && x > 1; ++i) { // loop runs r - 1 times atmost
                    x = x * x % number;
                    if (x == number - 1) {
                        witnessFound = true;
                        break;
                    }
                }
                if (!witnessFound) return false;
            }
        }
        return true;
    }

    public static long[] factorial;

    public void setFactorial() {
        factorial = new long[MAXN];
        factorial[0] = 1;
        for (int i = 1; i < MAXN; ++i) factorial[i] = factorial[i - 1] * i % MOD;
    }

    public long getFactorial(int n) {
        if (factorial == null) setFactorial();
        return factorial[n];
    }

    public long ncr(int n, int r) {
        if (r > n) return 0;
        if (factorial == null) setFactorial();
        long numerator = factorial[n];
        long denominator = factorial[r] * factorial[n - r] % MOD;
        return numerator * pow(denominator, MOD - 2, MOD) % MOD;
    }

    public String joinElements(long... ar) {
        StringBuilder sb = new StringBuilder();
        for (long itr : ar) sb.append(itr).append(" ");
        return sb.toString().trim();
    }

    public String joinElements(int... ar) {
        StringBuilder sb = new StringBuilder();
        for (int itr : ar) sb.append(itr).append(" ");
        return sb.toString().trim();
    }

    public String joinElements(String... ar) {
        StringBuilder sb = new StringBuilder();
        for (String itr : ar) sb.append(itr).append(" ");
        return sb.toString().trim();
    }

    public String joinElements(Object... ar) {
        StringBuilder sb = new StringBuilder();
        for (Object itr : ar) sb.append(itr).append(" ");
        return sb.toString().trim();
    }

    public long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    public long gcd(long... ar) {
        long ret = ar[0];
        for (long itr : ar) ret = gcd(ret, itr);
        return ret;
    }

    public int gcd(int... ar) {
        int ret = ar[0];
        for (int itr : ar) ret = gcd(ret, itr);
        return ret;
    }

    public long lcm(long... ar) {
        long ret = ar[0];
        for (long itr : ar) ret = lcm(ret, itr);
        return ret;
    }

    public long max(long... ar) {
        long ret = ar[0];
        for (long itr : ar) ret = Math.max(ret, itr);
        return ret;
    }

    public int max(int... ar) {
        int ret = ar[0];
        for (int itr : ar) ret = Math.max(ret, itr);
        return ret;
    }

    public long min(long... ar) {
        long ret = ar[0];
        for (long itr : ar) ret = Math.min(ret, itr);
        return ret;
    }

    public int min(int... ar) {
        int ret = ar[0];
        for (int itr : ar) ret = Math.min(ret, itr);
        return ret;
    }

    public long sum(long... ar) {
        long sum = 0;
        for (long itr : ar) sum += itr;
        return sum;
    }

    public long sum(int... ar) {
        long sum = 0;
        for (int itr : ar) sum += itr;
        return sum;
    }

    public void shuffle(int[] ar) {
        int r;
        for (int i = 0; i < ar.length; ++i) {
            r = rnd.nextInt(ar.length);
            if (r != i) {
                ar[i] ^= ar[r];
                ar[r] ^= ar[i];
                ar[i] ^= ar[r];
            }
        }
    }

    public void shuffle(long[] ar) {
        int r;
        for (int i = 0; i < ar.length; ++i) {
            r = rnd.nextInt(ar.length);
            if (r != i) {
                ar[i] ^= ar[r];
                ar[r] ^= ar[i];
                ar[i] ^= ar[r];
            }
        }
    }

    public void reverse(int[] ar) {
        int r;
        for (int i = 0; i < ar.length; ++i) {
            r = ar.length - 1 - i;
            if (r > i) {
                ar[i] ^= ar[r];
                ar[r] ^= ar[i];
                ar[i] ^= ar[r];
            }
        }
    }

    public void reverse(long[] ar) {
        int r;
        for (int i = 0; i < ar.length; ++i) {
            r = ar.length - 1 - i;
            if (r > i) {
                ar[i] ^= ar[r];
                ar[r] ^= ar[i];
                ar[i] ^= ar[r];
            }
        }
    }

    public long pow(long base, long exp, long MOD) {
        base %= MOD;
        long ret = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) ret = ret * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return ret;
    }

    public long invModulo(long num, long MOD) { // MOD must be prime
        return pow(num, MOD - 2, MOD);
    }

    public int getRandomInRange(int l, int r) {
        return rnd.nextInt(r - l + 1) + l;
    }
}