/**
 * Автор: Кухарук Соломія
 * Дата: 30.11.2025
 * Час виконання: 13:32
 *
 * Тестовий клас, що демонструє роботу класу {@code <Polynome>}.
 * Містить приклади створення об’єктів, виконання базових операцій та
 * виведення результатів у консоль.
 */

package tests;

import polynominal.Polynome;
import numbers.Rational;
import numbers.Unsigned;
import numbers.BigUnsigned;

import java.io.IOException;

/**
 * Тестові приклади для класу {@link Polynome}.
 */
public class PolynomeTest {

    /**
     * Точка входу для тестування {@link Polynome}.
     *
     * @param args не використовується
     */
    public static void main(String[] args) {
        Polynome p = new Polynome(new double[]{1, -3, 2}); // 1 - 3x + 2x^2
        System.out.println("p(x) = " + p);
        System.out.println("p(0) = " + p.valueAt(0));
        System.out.println("p(1) = " + p.valueAt(1));
        System.out.println("p'(x) = " + p.derivative());
        System.out.println("∫p(x)dx = " + p.integral() + " + C");
        System.out.println("∫[0,1] p(x) dx = " + p.definiteIntegral(0, 1));

        Rational[] rCoeffs = {
                new Rational(1, 2),
                new Rational(-1, 1),
                new Rational(3, 2)
        };
        Polynome pr = new Polynome(rCoeffs);
        System.out.println("pr(x) = " + pr);

        Unsigned[] uCoeffs = {
                new Unsigned(1),
                new Unsigned(2),
                new Unsigned(3)
        };
        Polynome pu = new Polynome(uCoeffs);
        System.out.println("pu(x) = " + pu);

        BigUnsigned[] bCoeffs = {
                new BigUnsigned("1"),
                new BigUnsigned("0"),
                new BigUnsigned("5")
        };
        Polynome pb = new Polynome(bCoeffs);
        System.out.println("pb(x) = " + pb);

        Polynome sum = p.add(pr);
        Polynome diff = p.subtract(pr);
        Polynome prod = p.multiply(pr);
        System.out.println("p + pr = " + sum);
        System.out.println("p - pr = " + diff);
        System.out.println("p * pr = " + prod);

        try {
            p.saveToFile("resources/poly_test.txt");
            Polynome loaded = Polynome.readFromFile("resources/poly_test.txt");
            System.out.println("Зчитаний з файлу поліном: " + loaded);
        } catch (IOException e) {
            System.err.println("Помилка роботи з файлом: " + e.getMessage());
        }
    }
}
