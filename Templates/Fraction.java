package Templates;

public class Fraction {
    public long getNum() {
        return num;
    }

    public long getDen() {
        return den;
    }

    long num, den;

    public Fraction(long n, long d) {
        num = n;
        den = d;
    }

    public Fraction(double n, int places) {
        num = (long) n;
        den = 1;
        n -= (long) n;
        for (; places > 0; --places) {
            n *= 10;
            num = (10 * num) + (long) n;
            n -= (long) n;
            den *= 10;
        }
    }

    long gcd(long a, long b) {
        long temp;
        a = Math.abs(a);
        b = Math.abs(b);
        while (b > 0) {
            temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    void reduce() {
        long gcd = gcd(num, den);
        num /= gcd;
        den /= gcd;
    }

    long clVal() {
        long val = (long) decimalValue();
        while (num > val * den)
            ++val;
        while (num <= (val - 1) * den)
            --val;
        return val;
    }

    long flVal() {
        long val = (long) decimalValue();
        while (num < val * den)
            --val;
        while (num >= (val + 1) * den)
            ++val;
        return val;
    }

    void add(Fraction f, long MOD) {
        num = ((num * f.den) % MOD + (f.num * den) % MOD) % MOD;
        den *= f.den;
        den %= MOD;
    }

    void multiply(Fraction f, long MOD) {
        num *= f.num;
        num %= MOD;
        den *= f.den;
        den %= MOD;
    }

    void invert() {
        long temp = num;
        num = den;
        den = temp;
    }

    void negate() {
        if (den < 0) den = -den;
        else num = -num;
    }

    double decimalValue() {
        return num / (double) den;
    }

    long modular_pow(long base, long exponent, long MOD) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (result * base) % MOD;
            exponent = exponent >> 1;
            base = (base * base) % MOD;
        }
        return result;
    }

    void power(long exp, long MOD) {
        num = modular_pow(num, exp, MOD);
        den = modular_pow(den, exp, MOD);
    }

    long modInverse(long num, long MOD) {
        return modular_pow(num, MOD - 2, MOD);
    }

    public String toString() {
        return num + "/" + den;
    }
}