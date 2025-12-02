package numbers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Беззнакове ціле число типу {@code long}, яке не може бути від’ємним.
 */
public class Unsigned implements Numeric, Serializable {

    private static final long serialVersionUID = 1L;

    private long value;

    /**
     * Створює число 0.
     */
    public Unsigned() {
        this(0);
    }

    /**
     * Створює беззнакове ціле число.
     *
     * @param value значення (має бути {@code >= 0})
     * @throws IllegalArgumentException якщо {@code value < 0}
     */
    public Unsigned(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Unsigned value must be >= 0");
        }
        this.value = value;
    }

    /**
     * Створює об’єкт з рядка.
     *
     * @param s рядок з цілим числом
     * @return екземпляр {@link Unsigned}
     * @throws NumberFormatException якщо формат рядка некоректний
     */
    public static Unsigned parse(String s) {
        long v = Long.parseLong(s.trim());
        return new Unsigned(v);
    }

    /**
     * Повертає внутрішнє значення.
     *
     * @return значення типу {@code long}
     */
    public long getValue() {
        return value;
    }

    /**
     * Додає інше беззнакове число.
     *
     * @param other доданок
     * @return новий об’єкт {@link Unsigned} – результат додавання
     * @throws ArithmeticException при переповненні діапазону {@code long}
     */
    public Unsigned add(Unsigned other) {
        long v = this.value + other.value;
        if (v < 0) {
            throw new ArithmeticException("Overflow in Unsigned.add");
        }
        return new Unsigned(v);
    }

    /**
     * Віднімає інше беззнакове число.
     *
     * @param other від’ємник
     * @return новий об’єкт {@link Unsigned} – результат віднімання
     * @throws ArithmeticException якщо результат від’ємний
     */
    public Unsigned subtract(Unsigned other) {
        long v = this.value - other.value;
        if (v < 0) {
            throw new ArithmeticException("Result of Unsigned.subtract is negative");
        }
        return new Unsigned(v);
    }

    /**
     * Множить на інше беззнакове число.
     *
     * @param other множник
     * @return новий об’єкт {@link Unsigned} – результат множення
     * @throws ArithmeticException при переповненні діапазону {@code long}
     */
    public Unsigned multiply(Unsigned other) {
        long v = this.value * other.value;
        if (v < 0) {
            throw new ArithmeticException("Overflow in Unsigned.multiply");
        }
        return new Unsigned(v);
    }

    /**
     * Ділить на інше беззнакове число.
     *
     * @param other дільник
     * @return новий об’єкт {@link Unsigned} – результат ділення
     * @throws ArithmeticException якщо дільник дорівнює нулю
     */
    public Unsigned divide(Unsigned other) {
        if (other.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        long v = this.value / other.value;
        return new Unsigned(v);
    }

    /**
     * Повертає значення як {@code double}.
     *
     * @return десяткове представлення числа
     */
    @Override
    public double toDouble() {
        return (double) value;
    }

    /**
     * Записує беззнакове число у текстовий файл.
     * Формат запису збігається з {@link #toString()}.
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
     * Зчитує беззнакове число з першого рядка текстового файлу.
     * Формат рядка має відповідати {@link #parse(String)}.
     *
     * @param fileName шлях до файлу
     * @return об’єкт {@link Unsigned}
     * @throws IOException           у разі помилки читання
     * @throws NumberFormatException якщо формат рядка некоректний
     */
    public static Unsigned readFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return new Unsigned(0);
            }
            return Unsigned.parse(line);
        }
    }

    /**
     * Повертає рядкове представлення числа.
     *
     * @return текстова форма значення
     */
    @Override
    public String toString() {
        return Long.toString(value);
    }
}
