package Templates;

import java.util.*;

public class Helper {
    public final long MOD;
    public final int MAXN;
    final Random rnd;

    public Helper() {
        MOD = 1000_000_007;
        MAXN = 1000_006;
        rnd = new Random();
    }

    public Helper(long mod, int maxn) {
        MOD = mod;
        MAXN = maxn;
        rnd = new Random();
    }

    public int[] getSieve() {
        if (sieve == null || sieve.length < MAXN) setSieve();
        return sieve;
    }

    public ArrayList<Integer> getPrimes() {
        if (sieve == null || sieve.length < MAXN) setSieve();
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
                        if (sieve[j] == 0) sieve[j] = i;
                    }
                }
            }
        }
    }

    public ArrayList<Integer> primeFactorise(int num) {
        setSieve();
        ArrayList<Integer> factors = new ArrayList<>();
        for (int prime : primes) {
            if (prime * prime <= num) {
                while (num % prime == 0) {
                    factors.add(prime);
                    num /= prime;
                }
            } else {
                break;
            }
        }
        if (num > 1) factors.add(num);
        return factors;
    }

    public ArrayList<Long> primeFactorise(long num, final int certainty) {
        setSieve();
        ArrayList<Long> factors = new ArrayList<>();
        primeFactorise(num, certainty, factors);
        return factors;
    }

    public void primeFactorise(long num, final int certainty, ArrayList<Long> primeFactors) {
        if (num <= 1) {
            return;
        } else if (isProbablePrime(num, certainty)) {
            primeFactors.add(num);
            return;
        }

        int attempts = certainty;
        while (attempts-- > 0) {
            long factor = getAnyFactor(num, certainty);
            if (factor < num) {
                primeFactorise(factor, certainty, primeFactors);
                primeFactorise(num / factor, certainty, primeFactors);
                return;
            }
        }
        primeFactors.add(num);
    }

    public long getAnyFactor(long num, final int certainty) {
        setSieve();
        if (num <= 1) return num;
        else if (num < MAXN) return sieve[(int) num];

        // Pollard's Rho algorithm to find a factor > MAXN
        // https://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm
        // Using g(x) = (x * x + c) % n
        final long c = getRandomInRange(1, (int) Math.min(Integer.MAX_VALUE - 7, num - 1));
        long x = getRandomInRange(1, (int) Math.min(Integer.MAX_VALUE - 7, num - 1));
        long y = x;
        long d = 1;
        while (d == 1) {
            x = moduloMultiply(x, x, num) + c;
            y = moduloMultiply(y, y, num) + c;
            y = moduloMultiply(y, y, num) + c;
            d = gcd(Math.abs(x - y), num);
        }
        return d;
    }

    public ArrayList<Long> getAllFactors(long num, final int certainty) {
        ArrayList<Long> allFactors = new ArrayList<>();

        ArrayList<Long> primeFactors = primeFactorise(num, certainty);
        Collections.sort(primeFactors);
        fillAllFactors(0, 1, primeFactors, allFactors);

        return allFactors;
    }

    public void fillAllFactors(int idx, long prodSoFar, ArrayList<Long> primeFactors, ArrayList<Long> allFactors) {
        if (idx >= primeFactors.size()) {
            allFactors.add(prodSoFar);
            return;
        }

        final long primeFactor = primeFactors.get(idx);
        int endIdx = idx + 1;
        while (endIdx < primeFactors.size() && primeFactors.get(endIdx) == primeFactor) ++endIdx;

        fillAllFactors(endIdx, prodSoFar, primeFactors, allFactors);
        while (idx++ < endIdx) {
            fillAllFactors(endIdx, prodSoFar *= primeFactor, primeFactors, allFactors);
        }
    }

    public boolean isPrime(int number) {
        setSieve();
        return number > 1 && sieve[number] == number;
    }

    // https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
    public boolean isProbablePrime(long number, int certainty) {
        if (number < MAXN) return isPrime((int) number);
        else if ((number & 1) == 0) return false;

        long d = number - 1;
        int r = 0;
        while ((d & 1) == 0) {
            ++r;
            d >>= 1;
        }
        while (--certainty >= 0) { // witness loop
            long a = getRandomInRange(2, (int) Math.min(Integer.MAX_VALUE - 7, number - 2));
            long x = pow(a, d, number);

            if (x == 1 || x == number - 1) {
            } else {
                boolean witnessFound = false;
                for (int i = 1; i < r && x > 1; ++i) { // loop runs r - 1 times atmost
                    x = moduloMultiply(x, x, number);
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

    public static long[] factorial, invFactorial, invNaturalNum;

    public void setFactorial() {
        factorial = new long[MAXN];
        factorial[0] = 1;
        for (int i = 1; i < MAXN; ++i) {
            factorial[i] = factorial[i - 1] * i % MOD;
        }
    }

    public void setInvNaturalNum() {
        invNaturalNum = new long[MAXN];
        invNaturalNum[0] = invNaturalNum[1] = 1;
        for (int i = 2; i < MAXN; ++i) {
            invNaturalNum[i] = invNaturalNum[(int) (MOD % i)] * (MOD - MOD / i) % MOD;
        }
    }

    public void setInvFactorial() {
        if (invNaturalNum == null) setInvNaturalNum();
        invFactorial = new long[MAXN];
        invFactorial[0] = 1;
        for (int i = 1; i < MAXN; ++i) {
            invFactorial[i] = invFactorial[i - 1] * invNaturalNum[i] % MOD;
        }
    }

    public long getFactorial(int n) {
        if (factorial == null) setFactorial();
        return factorial[n];
    }

    public long getInvModulo(long n) { // MOD must be prime
        if (n >= MAXN) return pow(n, MOD - 2, MOD);
        else if (invNaturalNum == null) setInvNaturalNum();
        return invNaturalNum[(int) n];
    }

    public long getInvModulo(long n, long mod) { // mod must be prime
        if (mod == MOD) return getInvModulo(n);
        else return pow(n, mod - 2, mod);
    }

    public long getInvFactorial(int n) { // MOD must be prime
        if (invFactorial == null) setInvFactorial();
        return invFactorial[n];
    }

    public long ncr(int n, int r) {
        if (r > n || r < 0) return 0;
        if (factorial == null) setFactorial();
        if (invFactorial == null) setInvFactorial();
        long numerator = factorial[n];
        long invDenominator = invFactorial[r] * invFactorial[n - r] % MOD;
        return numerator * invDenominator % MOD;
    }

    public boolean getBitAtPosition(int num, int pos) {
        return ((num >> pos) & 1) > 0;
    }

    public boolean getBitAtPosition(long num, int pos) {
        return ((num >> pos) & 1) > 0;
    }

    public int setBitAtPosition(int num, int pos) {
        return num | (1 << pos);
    }

    public long setBitAtPosition(long num, int pos) {
        return num | (1L << pos);
    }

    public int clearBitAtPosition(int num, int pos) {
        return (Integer.MAX_VALUE ^ (1 << pos)) & num;
    }

    public long clearBitAtPosition(long num, int pos) {
        return (Long.MAX_VALUE ^ (1L << pos)) & num;
    }

    public long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
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

    public long lcm(long a, long b) {
        return a * b / gcd(a, b);
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
            } else break;
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
            } else break;
        }
    }

    public int[] getIndexArrayInt(int size) {
        int[] ret = new int[size];
        for (int i = 0; i < size; ++i) ret[i] = i;
        return ret;
    }

    public Integer[] getIndexArrayInteger(int size) {
        Integer[] ret = new Integer[size];
        for (int i = 0; i < size; ++i) ret[i] = i;
        return ret;
    }

    public void swap(int a, int b, int[] ar) {
        int temp = ar[a]; ar[a] = ar[b]; ar[b] = temp;
    }

    public void swap(int a, int b, Integer[] ar) {
        Integer temp = ar[a]; ar[a] = ar[b]; ar[b] = temp;
    }

    public boolean isSorted(int[] ar) {
        for (int i = 1; i < ar.length; ++i) if (ar[i - 1] > ar[i]) {
            return false;
        }
        return true;
    }

    public boolean isReverseSorted(int[] ar) {
        for (int i = 1; i < ar.length; ++i) if (ar[i - 1] < ar[i]) {
            return false;
        }
        return true;
    }

    public boolean isSorted(long[] ar) {
        for (int i = 1; i < ar.length; ++i) if (ar[i - 1] > ar[i]) {
            return false;
        }
        return true;
    }

    public boolean isReverseSorted(long[] ar) {
        for (int i = 1; i < ar.length; ++i) if (ar[i - 1] < ar[i]) {
            return false;
        }
        return true;
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

    public long floorDiv(long num, long den) {
        if (num >= 0) {
            if (den >= 0) {
                return num / den;
            } else {
                return (num - den - 1) / den;
            }
        } else {
            if (den >= 0) {
                return (num - den + 1) / den;
            } else {
                return num / den;
            }
        }
    }

    public long ceilDiv(long num, long den) {
        if (num >= 0) {
            if (den >= 0) {
                return (num + den - 1) / den;
            } else {
                return num / den;
            }
        } else {
            if (den >= 0) {
                return num / den;
            } else {
                return (num + den + 1) / den;
            }
        }
    }

    public int floorDiv(int num, int den) {
        if (num >= 0) {
            if (den >= 0) {
                return num / den;
            } else {
                return (num - den - 1) / den;
            }
        } else {
            if (den >= 0) {
                return (num - den + 1) / den;
            } else {
                return num / den;
            }
        }
    }

    public int ceilDiv(int num, int den) {
        if (num >= 0) {
            if (den >= 0) {
                return (num + den - 1) / den;
            } else {
                return num / den;
            }
        } else {
            if (den >= 0) {
                return num / den;
            } else {
                return (num + den + 1) / den;
            }
        }
    }

    public long moduloMultiply(long a, long b, long m) {
        long q = (long) ((double) a * b / m);
        long r = (long) ((double) a * b - (double) q * m);
        return r < 0 ? r + m : r;
    }

    public int getRandomInRange(int l, int r) {
        return rnd.nextInt(r - l + 1) + l;
    }

    public int compare(Object a, Object b) {
        if (a instanceof Integer) {
            return Integer.compare((Integer) a, (Integer) b);
        } else if (a instanceof Long) {
            return Long.compare((Long) a, (Long) b);
        } else if (a instanceof String) {
            return ((String) a).compareTo((String) b);
        } else if (a instanceof Float) {
            return Float.compare((Float) a, (Float) b);
        } else if (a instanceof Double) {
            return Double.compare((Double) a, (Double) b);
        } else {
            return 5 / 0;
        }
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
}