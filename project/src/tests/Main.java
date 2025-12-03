/**
 * Автор: Кухарук Соломія
 * Дата: 30.11.2025
 * Час виконання: 16:13і
 *
 * Головний клас програми. Забезпечує взаємодію з користувачем та демонструє
 * роботу всіх частин проєкту:
 *
 * <ul>
 *   <li>вибір режиму тестування: введення з консолі або зчитування з файлу;</li>
 *   <li>побудову полінома та обчислення похідної, інтегралів і значень;</li>
 *   <li>демонстрацію роботи класів {@code Rational}, {@code Unsigned},
 *       {@code BigUnsigned};</li>
 *   <li>розв’язання лінійних і квадратних рівнянь;</li>
 *   <li>виведення усіх результатів у файл {@code resources/result.txt}.</li>
 * </ul>
 *
 * Клас є точкою входу у програму та використовується для тестування
 * всієї реалізованої функціональності.
 */

package tests;

import numbers.BigUnsigned;
import numbers.Rational;
import numbers.Unsigned;
import polynominal.Polynome;
import equations.EquationSolver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Головний клас тестування модуля чисел та поліномів.
 *
 * <p>При запуску програма пропонує обрати режим:
 * <ul>
 *     <li>введення даних з консолі;</li>
 *     <li>введення даних з тестового файлу {@code resources/NZ_test.txt}.</li>
 * </ul>
 */
public class Main {

    /**
     * Точка входу в програму.
     *
     * @param args не використовується
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Polynome p;

        System.out.println("=== Тестовий модуль чисел та поліномів ===");
        System.out.println("Оберіть режим тестування:");
        System.out.println("1 - введення з консолі");
        System.out.println("2 - введення даних з тестового файлу resources/NZ_test.txt");
        System.out.print("Ваш вибір: ");

        int mode = scanner.nextInt();
        scanner.nextLine(); // з’їсти кінець рядка

        try {
            if (mode == 1) {
                p = readPolynomeFromConsole(scanner);
            } else if (mode == 2) {
                p = Polynome.readFromFile("resources/NZ_test.txt");
                System.out.println("Поліном з файлу: " + p);
            } else {
                System.out.println("Невідомий режим. Завершення роботи.");
                return;
            }

            StringBuilder log = new StringBuilder();
            log.append("=== Результати роботи програми ===\n\n");

            demonstratePolynome(p, scanner, log);
            demonstrateNumbers(log);
            demonstrateEquations(log);

            saveResultsToFile("resources/result.txt", log.toString());
            System.out.println("\nРезультати записано у файл: resources/result.txt");

        } catch (IOException e) {
            System.err.println("Помилка роботи з файлом: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Помилка формату даних: " + e.getMessage());
        }
    }

    /**
     * Зчитує поліном з консолі у форматі:
     * коефіцієнти від x^0 до x^n через пробіл в одному рядку.
     *
     * @param scanner сканер для читання з консолі
     * @return об’єкт {@link Polynome}
     */
    private static Polynome readPolynomeFromConsole(Scanner scanner) {
        System.out.println("Введіть коефіцієнти поліному через пробіл (від x^0 до x^n):");
        String line = scanner.nextLine();
        String[] parts = line.trim().split("\\s+");
        double[] coeffs = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            coeffs[i] = Double.parseDouble(parts[i]);
        }
        return new Polynome(coeffs);
    }

    /**
     * Демонструє роботу з поліномом:
     * вивід самого полінома, похідної, інтегралів та значень.
     *
     * @param p       поліном
     * @param scanner сканер для читання меж інтегрування
     * @param log     буфер для запису результатів
     */
    private static void demonstratePolynome(Polynome p, Scanner scanner, StringBuilder log) {
        System.out.println("\n=== Поліном ===");
        System.out.println("P(x) = " + p);
        log.append("P(x) = ").append(p).append("\n");

        System.out.print("Введіть значення x для обчислення P(x): ");
        double x = scanner.nextDouble();
        double value = p.valueAt(x);
        System.out.println("P(" + x + ") = " + value);
        log.append("P(").append(x).append(") = ").append(value).append("\n");

        Polynome derivative = p.derivative();
        System.out.println("P'(x) = " + derivative);
        log.append("P'(x) = ").append(derivative).append("\n");

        Polynome integral = p.integral();
        System.out.println("∫P(x)dx = " + integral + " + C");
        log.append("∫P(x)dx = ").append(integral).append(" + C\n");

        System.out.print("Введіть a для визначеного інтегралу: ");
        double a = scanner.nextDouble();
        System.out.print("Введіть b для визначеного інтегралу: ");
        double b = scanner.nextDouble();

        double area = p.definiteIntegral(a, b);
        System.out.println("∫[" + a + "," + b + "] P(x) dx = " + area);
        log.append("∫[").append(a).append(",").append(b).append("] P(x) dx = ").append(area).append("\n");
    }

    /**
     * Демонструє роботу з числовими типами:
     * {@link Rational}, {@link Unsigned}, {@link BigUnsigned}.
     *
     * @param log буфер для запису результатів
     */
    private static void demonstrateNumbers(StringBuilder log) {
        log.append("\n=== Тест числових типів ===\n");

        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(3, 4);
        log.append("Rational r1 = ").append(r1).append("\n");
        log.append("Rational r2 = ").append(r2).append("\n");
        log.append("r1 + r2 = ").append(r1.add(r2)).append("\n");
        log.append("r1 - r2 = ").append(r1.subtract(r2)).append("\n");
        log.append("r1 * r2 = ").append(r1.multiply(r2)).append("\n");
        log.append("r1 / r2 = ").append(r1.divide(r2)).append("\n");

        Unsigned u1 = new Unsigned(10);
        Unsigned u2 = new Unsigned(3);
        log.append("\nUnsigned u1 = ").append(u1).append("\n");
        log.append("Unsigned u2 = ").append(u2).append("\n");
        log.append("u1 + u2 = ").append(u1.add(u2)).append("\n");
        log.append("u1 - u2 = ").append(u1.subtract(u2)).append("\n");
        log.append("u1 * u2 = ").append(u1.multiply(u2)).append("\n");
        log.append("u1 / u2 = ").append(u1.divide(u2)).append("\n");

        BigUnsigned b1 = new BigUnsigned("10000000000000000000");
        BigUnsigned b2 = new BigUnsigned("2");
        log.append("\nBigUnsigned b1 = ").append(b1).append("\n");
        log.append("BigUnsigned b2 = ").append(b2).append("\n");
        log.append("b1 + b2 = ").append(b1.add(b2)).append("\n");
        log.append("b1 - b2 = ").append(b1.subtract(b2)).append("\n");
        log.append("b1 * b2 = ").append(b1.multiply(b2)).append("\n");
        log.append("b1 / b2 = ").append(b1.divide(b2)).append("\n");
    }

    /**
     * Демонструє роботу розв’язувача рівнянь {@link EquationSolver}.
     *
     * @param log буфер для запису результатів
     */
    private static void demonstrateEquations(StringBuilder log) {
        log.append("\n=== Тест розв’язання рівнянь ===\n");

        Rational a = new Rational(2, 1);
        Rational b = new Rational(3, 1);
        Rational solution = EquationSolver.solveLinear(a, b);
        log.append("Рівняння 2x + 3 = 0, x = ").append(solution).append("\n");

        double[] roots = EquationSolver.solveQuadratic(1, -3, 2);
        log.append("Рівняння x^2 - 3x + 2 = 0, корені = ").append(Arrays.toString(roots)).append("\n");
    }

    /**
     * Записує текстові результати у файл.
     *
     * @param fileName шлях до файлу
     * @param content  вміст, який потрібно записати
     * @throws IOException у разі помилки запису
     */
    private static void saveResultsToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
}
