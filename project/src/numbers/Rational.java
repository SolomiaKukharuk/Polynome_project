package numbers;
/**
 * Автор: Кухарук Соломія
 * Дата: 25.11.2025
 * Час виконання: 19:26
 *
 * Клас {@code Rational} реалізує раціональне число у вигляді дробу p/q.
 * Забезпечує автоматичне скорочення дробу, коректну роботу зі знаком,
 * арифметичні операції, перетворення у формат {@code double}, парсинг
 * зі строкового представлення, а також запис і читання з текстового файлу.
 *
 * Клас реалізує інтерфейс {@code Numeric} та інтерфейс {@code Serializable}.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Раціональне число вигляду {@code numerator/denominator}.
 *
 * <p>Число автоматично скорочується до нескоротного вигляду.
 */
public class Rational implements Numeric, Serializable {

    private static final long serialVersionUID = 1L;

    private long numerator;
    private long denominator;

    /**
     * Створює раціональне число {@code 0/1}.
     */
    public Rational() {
        this(0, 1);
    }

    /**
     * Створює раціональне число {@code numerator/denominator}.
     *
     * @param numerator   чисельник
     * @param denominator знаменник; не повинен дорівнювати нулю
     * @throws ArithmeticException якщо {@code denominator == 0}
     */
    public Rational(long numerator, long denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator must not be zero");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        long g = gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / g;
        this.denominator = denominator / g;
    }

    /**
     * Створює раціональне число з рядка формату {@code "a/b"} або {@code "a"}.
     *
     * @param s рядок з представленням числа
     * @return екземпляр {@link Rational}
     * @throws NumberFormatException якщо рядок має некоректний формат
     */
    public static Rational parse(String s) {
        s = s.trim();
        if (s.contains("/")) {
            String[] parts = s.split("/");
            long n = Long.parseLong(parts[0].trim());
            long d = Long.parseLong(parts[1].trim());
            return new Rational(n, d);
        } else {
            long n = Long.parseLong(s);
            return new Rational(n, 1);
        }
    }

    /**
     * Обчислює найбільший спільний дільник двох чисел.
     *
     * @param a перше число (невід’ємне)
     * @param b друге число (невід’ємне)
     * @return НСД чисел {@code a} та {@code b}
     */
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    /**
     * Повертає чисельник.
     *
     * @return чисельник дробу
     */
    public long getNumerator() {
        return numerator;
    }

    /**
     * Повертає знаменник.
     *
     * @return знаменник дробу
     */
    public long getDenominator() {
        return denominator;
    }

    /**
     * Додає до поточного числа інше раціональне число.
     *
     * @param other число, яке додається
     * @return новий об’єкт {@link Rational} – результат додавання
     */
    public Rational add(Rational other) {
        long n = this.numerator * other.denominator + other.numerator * this.denominator;
        long d = this.denominator * other.denominator;
        return new Rational(n, d);
    }

    /**
     * Віднімає з поточного числа інше раціональне число.
     *
     * @param other число, яке віднімається
     * @return новий об’єкт {@link Rational} – результат віднімання
     */
    public Rational subtract(Rational other) {
        long n = this.numerator * other.denominator - other.numerator * this.denominator;
        long d = this.denominator * other.denominator;
        return new Rational(n, d);
    }

    /**
     * Множить поточне число на інше раціональне число.
     *
     * @param other множник
     * @return новий об’єкт {@link Rational} – результат множення
     */
    public Rational multiply(Rational other) {
        long n = this.numerator * other.numerator;
        long d = this.denominator * other.denominator;
        return new Rational(n, d);
    }

    /**
     * Ділить поточне число на інше раціональне число.
     *
     * @param other дільник
     * @return новий об’єкт {@link Rational} – результат ділення
     * @throws ArithmeticException якщо дільник дорівнює нулю
     */
    public Rational divide(Rational other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Division by zero");
        }
        long n = this.numerator * other.denominator;
        long d = this.denominator * other.numerator;
        return new Rational(n, d);
    }

    /**
     * Повертає значення числа у вигляді {@code double}.
     *
     * @return десяткове представлення числа
     */
    @Override
    public double toDouble() {
        return (double) numerator / (double) denominator;
    }

    /**
     * Записує раціональне число у текстовий файл.
     * Формат запису збігається з {@link #toString()}:
     * {@code "numerator/denominator"} або просто чисельник.
     *
     * @param fileName шлях до файлу
     * @throws IOException у разі помилки запису
     */
    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(this.toString());
            writer.newLine();
        }
    }

    /**
     * Зчитує раціональне число з першого рядка текстового файлу.
     * Формат рядка має відповідати {@link #parse(String)}.
     *
     * @param fileName шлях до файлу
     * @return об’єкт {@link Rational}
     * @throws IOException           у разі помилки читання
     * @throws NumberFormatException якщо формат рядка некоректний
     */
    public static Rational readFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return new Rational(0, 1);
            }
            return Rational.parse(line);
        }
    }

    /**
     * Повертає рядкове представлення раціонального числа.
     *
     * @return {@code "numerator/denominator"} або просто чисельник, якщо знаменник 1
     */
    @Override
    public String toString() {
        if (denominator == 1) {
            return Long.toString(numerator);
        }
        return numerator + "/" + denominator;
    }
}
