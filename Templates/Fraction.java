package Templates;

public class Fraction {
    public long getNum() {
        return num;
    }

    public long getDen() {
        return den;
    }

    long num, den;
    Helper hp = new Helper();

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

    public void reduce() {
        long gcd = hp.gcd(num, den);
        num /= gcd;
        den /= gcd;
    }

    public long clVal() {
        return (num + den - 1) / den;
    }

    public long flVal() {
        return num / den;
    }

    public void add(Fraction f, long MOD) {
        num = ((num * f.den) % MOD + (f.num * den) % MOD) % MOD;
        den *= f.den;
        den %= MOD;
    }

    public void multiply(Fraction f, long MOD) {
        num *= f.num;
        num %= MOD;
        den *= f.den;
        den %= MOD;
    }

    public void invert() {
        long temp = num;
        num = den;
        den = temp;
    }

    public void negate() {
        if (den < 0) den = -den;
        else num = -num;
    }

    public double decimalValue() {
        return num / (double) den;
    }

    public long modular_pow(long base, long exponent, long MOD) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (result * base) % MOD;
            exponent = exponent >> 1;
            base = (base * base) % MOD;
        }
        return result;
    }

    public void power(long exp, long MOD) {
        num = modular_pow(num, exp, MOD);
        den = modular_pow(den, exp, MOD);
    }

    public long modValue(long MOD) {
        return getNum() % MOD * hp.invModulo(getDen(), MOD) % MOD;
    }

    public String toString() {
        return num + "/" + den;
    }
}