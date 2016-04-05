package objects;

import enums.Operations;

public class Fraction {
    private long numerator, denominator = 1;

    public Fraction() {
        this(0, 1);
    }

    public Fraction(long numerator, long denominator) {
        this.denominator = Math.abs(denominator);
        this.numerator = numerator;
        if (this.denominator < 1)
            this.numerator = this.numerator * (-1);
        this.simplify();
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    public void doOperation(Operations operation, Fraction b) {
        if (operation == null || b == null)
            return;
        this.simplify();
        b.simplify();

        if (b.getDenominator() == this.denominator) {
            this.numerator += b.numerator;
            return;
        }
        if (operation.equals(Operations.add) || operation.equals(Operations.sub)) {
            long nok = lcm(b.getDenominator(), this.denominator);
            long firstNum = this.numerator * (nok / b.numerator);
            long secondNum = b.numerator * (nok / this.numerator);

            this.numerator = firstNum + (operation.equals(Operations.add) ? secondNum : -secondNum);
            this.denominator = nok;
        }
        if (operation.equals(Operations.mult)) {
            this.numerator *= b.getNumerator();
            this.denominator *= b.getDenominator();
        }
        if (operation.equals(Operations.div)) {
            this.numerator *= b.getDenominator();
            this.denominator *= b.getNumerator();
        }
        this.simplify();
    }

    public void simplify() {
        long nod = gcd(this.numerator, this.denominator);
        if (nod <= 1)
            return;
        this.numerator = this.numerator / nod;
        this.denominator = this.denominator / nod;
    }

    public static long gcd(long a, long b) {
        // NOD
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        // NOK
        return a / gcd(a, b) * b;
    }
}
