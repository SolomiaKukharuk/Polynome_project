/**
 * Автор: Кухарук Соломія
 * Дата: 30.11.2025
 * Час виконання: 13:54
 *
 * Тестовий клас, що демонструє роботу класу {@code <BigUnsigned>}.
 * Містить приклади створення об’єктів, виконання базових операцій та
 * виведення результатів у консоль.
 */
package tests;

import numbers.BigUnsigned;

/**
 * Тестові приклади для класу {@link BigUnsigned}.
 */
public class BigUnsignedTest {

    /**
     * Точка входу для тестування {@link BigUnsigned}.
     *
     * @param args не використовується
     */
    public static void main(String[] args) {
        BigUnsigned a = new BigUnsigned("10000000000000000000");
        BigUnsigned b = new BigUnsigned("2");

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a / b = " + a.divide(b));
    }
}
