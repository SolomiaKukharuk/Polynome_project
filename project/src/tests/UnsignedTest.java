/**
 * Автор: Кухарук Соломія
 * Дата: 30.11.2025
 * Час виконання: 13:05
 *
 * Тестовий клас, що демонструє роботу класу {@code <Unsigned>}.
 * Містить приклади створення об’єктів, виконання базових операцій та
 * виведення результатів у консоль.
 */
package tests;

import numbers.Unsigned;

/**
 * Тестові приклади для класу {@link Unsigned}.
 */
public class UnsignedTest {

    /**
     * Точка входу для тестування {@link Unsigned}.
     *
     * @param args не використовується
     */
    public static void main(String[] args) {
        Unsigned a = new Unsigned(10);
        Unsigned b = new Unsigned(3);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a / b = " + a.divide(b));

        Unsigned c = Unsigned.parse("25");
        System.out.println("c = " + c);
    }
}
