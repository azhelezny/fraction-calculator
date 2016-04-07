package objects;

import enums.Operations;

public class Fraction {
    private long numerator, denominator = 1;

    public Fraction() {
        this(0, 1);
    }

    public Fraction(long numerator, long denominator) {
        this.denominator = Math.abs(denominator);
        this.numerator = (denominator < 1) ? -numerator : numerator;
        this.simplify();
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    public static Fraction doOperation(Fraction a, Operations operation, Fraction b) {
        if (operation == null || b == null)
            return null;

        if (b.getDenominator() == a.getDenominator())
            return new Fraction(a.getNumerator() + b.getNumerator(), a.getDenominator());

        if (operation.equals(Operations.add) || operation.equals(Operations.sub)) {
            long nok = lcm(a.getDenominator(), b.getDenominator());
            long firstNum = a.getNumerator() * (nok / a.getDenominator());
            long secondNum = b.getNumerator() * (nok / b.getDenominator());

            return new Fraction(firstNum + ((operation.equals(Operations.add)) ? secondNum : -secondNum), nok);
        }

        if (operation.equals(Operations.mult))
            return new Fraction(a.getNumerator() * b.getNumerator(), a.getDenominator() * b.getDenominator());

        if (operation.equals(Operations.div))
            return new Fraction(a.getNumerator() * b.getDenominator(), a.getDenominator() * b.getNumerator());

        return null;
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
