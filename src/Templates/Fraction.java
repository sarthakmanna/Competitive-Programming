package Templates;

public class Fraction implements Comparable<Fraction> {
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
        reduce();
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

    public int compareTo(Fraction f) {
        long lcm = hp.lcm(den, f.den);
        return Long.compare(lcm / den * num, lcm / f.den * f.num);
    }

    public void reduce() {
        long gcd = hp.gcd(num, den);
        num /= gcd;
        den /= gcd;
    }

    public long ceillValue() {
        return (num + den - 1) / den;
    }

    public long floorValue() {
        return num / den;
    }

    public void add(Fraction f) {
        num = num * f.den + f.num * den;
        den *= f.den;
        reduce();
    }

    public void addModulo(Fraction f, long MOD) {
        num = ((num * f.den) % MOD + (f.num * den) % MOD) % MOD;
        den *= f.den;
        den %= MOD;
    }

    public void multiply(Fraction f) {
        num *= f.num;
        den *= f.den;
        reduce();
    }

    public void multiplyModulo(Fraction f, long MOD) {
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

    public void power(long exp, long MOD) {
        num = hp.pow(num, exp, MOD);
        den = hp.pow(den, exp, MOD);
    }

    public long modValue(long MOD) {
        return getNum() % MOD * hp.getInvModulo(getDen(), MOD) % MOD;
    }

    public String toString() {
        return num + "/" + den;
    }
}