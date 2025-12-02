package numbers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Беззнакове ціле число необмеженого розміру на основі {@link BigInteger}.
 */
public class BigUnsigned implements Numeric, Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger value;

    /**
     * Створює число 0.
     */
    public BigUnsigned() {
        this(BigInteger.ZERO);
    }

    /**
     * Створює число з {@link BigInteger}.
     *
     * @param value значення (має бути {@code >= 0})
     * @throws IllegalArgumentException якщо {@code value} від’ємне
     */
    public BigUnsigned(BigInteger value) {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("BigUnsigned must be >= 0");
        }
        this.value = value;
    }

    /**
     * Створює число з рядка.
     *
     * @param s рядок з цілим числом
     * @throws NumberFormatException якщо формат рядка некоректний
     */
    public BigUnsigned(String s) {
        this(new BigInteger(s.trim()));
    }

    /**
     * Парсить рядок у {@link BigUnsigned}.
     *
     * @param s рядок з цілим числом
     * @return новий екземпляр {@link BigUnsigned}
     */
    public static BigUnsigned parse(String s) {
        return new BigUnsigned(s);
    }

    /**
     * Повертає внутрішнє значення {@link BigInteger}.
     *
     * @return значення типу {@link BigInteger}
     */
    public BigInteger getValue() {
        return value;
    }

    /**
     * Додає інше число {@link BigUnsigned}.
     *
     * @param other доданок
     * @return новий об’єкт {@link BigUnsigned} – результат додавання
     */
    public BigUnsigned add(BigUnsigned other) {
        return new BigUnsigned(this.value.add(other.value));
    }

    /**
     * Віднімає інше число {@link BigUnsigned}.
     *
     * @param other від’ємник
     * @return новий об’єкт {@link BigUnsigned} – результат віднімання
     * @throws ArithmeticException якщо результат від’ємний
     */
    public BigUnsigned subtract(BigUnsigned other) {
        BigInteger res = this.value.subtract(other.value);
        if (res.signum() < 0) {
            throw new ArithmeticException("Result of BigUnsigned.subtract is negative");
        }
        return new BigUnsigned(res);
    }

    /**
     * Множить на інше число {@link BigUnsigned}.
     *
     * @param other множник
     * @return новий об’єкт {@link BigUnsigned} – результат множення
     */
    public BigUnsigned multiply(BigUnsigned other) {
        return new BigUnsigned(this.value.multiply(other.value));
    }

    /**
     * Ділить на інше число {@link BigUnsigned}.
     *
     * @param other дільник
     * @return новий об’єкт {@link BigUnsigned} – результат ділення
     * @throws ArithmeticException якщо дільник дорівнює нулю
     */
    public BigUnsigned divide(BigUnsigned other) {
        if (other.value.signum() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new BigUnsigned(this.value.divide(other.value));
    }

    /**
     * Повертає значення як {@code double}.
     *
     * @return десяткове представлення числа
     */
    @Override
    public double toDouble() {
        return value.doubleValue();
    }

    /**
     * Записує число {@link BigUnsigned} у текстовий файл.
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
     * Зчитує число {@link BigUnsigned} з першого рядка текстового файлу.
     * Формат рядка має відповідати {@link #parse(String)}.
     *
     * @param fileName шлях до файлу
     * @return об’єкт {@link BigUnsigned}
     * @throws IOException           у разі помилки читання
     * @throws NumberFormatException якщо формат рядка некоректний
     */
    public static BigUnsigned readFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return new BigUnsigned("0");
            }
            return BigUnsigned.parse(line);
        }
    }

    /**
     * Повертає рядкове представлення числа.
     *
     * @return текстова форма значення
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
