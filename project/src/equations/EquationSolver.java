package equations;

import numbers.Rational;

/**
 * Набір статичних методів для розв’язання лінійних та квадратних рівнянь.
 */
public class EquationSolver {

    /**
     * Розв’язує лінійне рівняння {@code a x + b = 0} у раціональних числах.
     *
     * @param a коефіцієнт при {@code x}
     * @param b вільний член
     * @return розв’язок {@code x} як {@link Rational}
     * @throws ArithmeticException якщо {@code a == 0}
     */
    public static Rational solveLinear(Rational a, Rational b) {
        if (a.getNumerator() == 0) {
            throw new ArithmeticException("Coefficient a must not be zero");
        }
        Rational minusB = new Rational(-b.getNumerator(), b.getDenominator());
        return minusB.divide(a);
    }

    /**
     * Розв’язує квадратне рівняння {@code a x^2 + b x + c = 0}.
     *
     * @param a коефіцієнт при {@code x^2}
     * @param b коефіцієнт при {@code x}
     * @param c вільний член
     * @return масив коренів (0, 1 або 2 елементи)
     */
    public static double[] solveQuadratic(double a, double b, double c) {
        if (a == 0.0) {
            if (b == 0.0) {
                return new double[]{}; // немає однозначного рішення
            }
            return new double[]{-c / b};
        }
        double d = b * b - 4.0 * a * c;
        if (d < 0.0) {
            return new double[]{};
        } else if (Math.abs(d) < 1e-12) {
            return new double[]{-b / (2.0 * a)};
        } else {
            double sqrtD = Math.sqrt(d);
            double x1 = (-b + sqrtD) / (2.0 * a);
            double x2 = (-b - sqrtD) / (2.0 * a);
            return new double[]{x1, x2};
        }
    }
}
