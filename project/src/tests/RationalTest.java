/**
 * Автор: Кухарук Соломія
 * Дата: 30.11.2025
 * Час виконання: 12:43
 *
 * Тестовий клас, що демонструє роботу класу {@code <Rationals>}.
 * Містить приклади створення об’єктів, виконання базових операцій та
 * виведення результатів у консоль.
 */
package tests;

import numbers.Rational;

/**
 * Тестові приклади для класу {@link Rational}.
 */
public class RationalTest {

    /**
     * Точка входу для тестування {@link Rational}.
     *
     * @param args не використовується
     */
    public static void main(String[] args) {
        Rational a = new Rational(1, 2);
        Rational b = new Rational(3, 4);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a / b = " + a.divide(b));

        Rational p = Rational.parse("5/6");
        Rational q = Rational.parse("2");
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("p + q = " + p.add(q));
    }
}
