package polynomial;

import numbers.BigUnsigned;
import numbers.Numeric;
import numbers.Rational;
import numbers.Unsigned;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Поліном однієї змінної з коефіцієнтами типу {@code double}.
 *
 * <p>Коефіцієнт з індексом {@code i} відповідає множнику при {@code x^i}.
 */
public class Polynome implements Numeric, Serializable {

    private static final long serialVersionUID = 1L;

    private double[] coeffs;

    /**
     * Створює нульовий поліном {@code 0}.
     */
    public Polynome() {
        this.coeffs = new double[]{0.0};
    }

    /**
     * Створює поліном з масиву коефіцієнтів.
     *
     * @param coeffs масив коефіцієнтів від {@code x^0} до {@code x^n}
     * @throws NullPointerException якщо {@code coeffs == null}
     */
    public Polynome(double[] coeffs) {
        if (coeffs == null) {
            throw new NullPointerException("coeffs must not be null");
        }
        this.coeffs = trimTrailingZeros(coeffs);
    }

    /**
     * Копіювальний конструктор.
     *
     * @param other поліном, що копіюється
     * @throws NullPointerException якщо {@code other == null}
     */
    public Polynome(Polynome other) {
        this(other.coeffs);
    }

    /**
     * Створює поліном з масиву раціональних коефіцієнтів.
     *
     * @param coeffs масив {@link Rational}
     */
    public Polynome(Rational[] coeffs) {
        double[] c = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            c[i] = coeffs[i].toDouble();
        }
        this.coeffs = trimTrailingZeros(c);
    }

    /**
     * Створює поліном з масиву {@link Unsigned}.
     *
     * @param coeffs масив {@link Unsigned}
     */
    public Polynome(Unsigned[] coeffs) {
        double[] c = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            c[i] = coeffs[i].toDouble();
        }
        this.coeffs = trimTrailingZeros(c);
    }

    /**
     * Створює поліном з масиву {@link BigUnsigned}.
     *
     * @param coeffs масив {@link BigUnsigned}
     */
    public Polynome(BigUnsigned[] coeffs) {
        double[] c = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            c[i] = coeffs[i].toDouble();
        }
        this.coeffs = trimTrailingZeros(c);
    }

    /**
     * Обрізає нульові коефіцієнти з високих степенів.
     *
     * @param a початковий масив коефіцієнтів
     * @return новий масив без зайвих нулів у кінці
     */
    private double[] trimTrailingZeros(double[] a) {
        int last = a.length - 1;
        while (last > 0 && Math.abs(a[last]) < 1e-12) {
            last--;
        }
        double[] res = new double[last + 1];
        System.arraycopy(a, 0, res, 0, last + 1);
        return res;
    }

    /**
     * Повертає степінь полінома.
     *
     * @return степінь полінома (ціле число {@code >= 0})
     */
    public int degree() {
        return coeffs.length - 1;
    }

    /**
     * Обчислює значення полінома у точці {@code x}.
     *
     * @param x значення аргумента
     * @return значення {@code P(x)}
     */
    public double valueAt(double x) {
        double res = 0.0;
        double pow = 1.0;
        for (double c : coeffs) {
            res += c * pow;
            pow *= x;
        }
        return res;
    }

    /**
     * Обчислює похідну полінома.
     *
     * @return новий об’єкт {@link Polynome}, що є похідною від поточного
     */
    public Polynome derivative() {
        if (coeffs.length == 1) {
            return new Polynome(new double[]{0.0});
        }
        double[] d = new double[coeffs.length - 1];
        for (int i = 1; i < coeffs.length; i++) {
            d[i - 1] = coeffs[i] * i;
        }
        return new Polynome(d);
    }

    /**
     * Обчислює невизначений інтеграл полінома з константою інтегрування 0.
     *
     * @return новий об’єкт {@link Polynome}, що є первісною
     */
    public Polynome integral() {
        double[] in = new double[coeffs.length + 1];
        in[0] = 0.0;
        for (int i = 0; i < coeffs.length; i++) {
            in[i + 1] = coeffs[i] / (i + 1);
        }
        return new Polynome(in);
    }

    /**
     * Обчислює визначений інтеграл на відрізку [{@code a}, {@code b}].
     *
     * @param a ліва межа інтегрування
     * @param b права межа інтегрування
     * @return значення визначеного інтегралу
     */
    public double definiteIntegral(double a, double b) {
        Polynome F = integral();
        return F.valueAt(b) - F.valueAt(a);
    }

    /**
     * Додає інший поліном до поточного.
     *
     * @param other доданок
     * @return новий об’єкт {@link Polynome} – результат додавання
     */
    public Polynome add(Polynome other) {
        int n = Math.max(this.coeffs.length, other.coeffs.length);
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            double c1 = i < this.coeffs.length ? this.coeffs[i] : 0.0;
            double c2 = i < other.coeffs.length ? other.coeffs[i] : 0.0;
            res[i] = c1 + c2;
        }
        return new Polynome(res);
    }

    /**
     * Віднімає інший поліном із поточного.
     *
     * @param other від’ємник
     * @return новий об’єкт {@link Polynome} – результат віднімання
     */
    public Polynome subtract(Polynome other) {
        int n = Math.max(this.coeffs.length, other.coeffs.length);
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            double c1 = i < this.coeffs.length ? this.coeffs[i] : 0.0;
            double c2 = i < other.coeffs.length ? other.coeffs[i] : 0.0;
            res[i] = c1 - c2;
        }
        return new Polynome(res);
    }

    /**
     * Множить поліном на інший поліном.
     *
     * @param other множник
     * @return новий об’єкт {@link Polynome} – результат множення
     */
    public Polynome multiply(Polynome other) {
        double[] res = new double[this.coeffs.length + other.coeffs.length - 1];
        for (int i = 0; i < this.coeffs.length; i++) {
            for (int j = 0; j < other.coeffs.length; j++) {
                res[i + j] += this.coeffs[i] * other.coeffs[j];
            }
        }
        return new Polynome(res);
    }

    /**
     * Записує поліном у текстовий файл. Коефіцієнти записуються в один рядок через пробіл.
     *
     * @param fileName шлях до файлу
     * @throws IOException у разі помилки запису у файл
     */
    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < coeffs.length; i++) {
                writer.write(Double.toString(coeffs[i]));
                if (i < coeffs.length - 1) {
                    writer.write(" ");
                }
            }
            writer.newLine();
        }
    }

    /**
     * Зчитує поліном з текстового файлу. Передбачається, що перший рядок містить
     * коефіцієнти через пробіл.
     *
     * @param fileName шлях до файлу
     * @return новий об’єкт {@link Polynome}
     * @throws IOException           у разі помилки читання з файлу
     * @throws NumberFormatException якщо формат коефіцієнтів некоректний
     */
    public static Polynome readFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return new Polynome();
            }
            String[] parts = line.trim().split("\\s+");
            double[] c = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                c[i] = Double.parseDouble(parts[i]);
            }
            return new Polynome(c);
        }
    }

    /**
     * Повертає значення полінома у точці {@code x = 1}.
     *
     * <p>Використовується для задоволення інтерфейсу {@link Numeric}.
     *
     * @return значення {@code P(1)}
     */
    @Override
    public double toDouble() {
        return valueAt(1.0);
    }

    /**
     * Повертає текстове подання полінома у звичному вигляді.
     *
     * @return рядкове представлення полінома
     */
    @Override
    public String toString() {
        if (coeffs.length == 1 && Math.abs(coeffs[0]) < 1e-12) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = coeffs.length - 1; i >= 0; i--) {
            double c = coeffs[i];
            if (Math.abs(c) < 1e-12) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(c >= 0 ? " + " : " - ");
                c = Math.abs(c);
            } else if (c < 0) {
                sb.append("-");
                c = -c;
            }
            if (i == 0) {
                sb.append(c);
            } else if (i == 1) {
                if (Math.abs(c - 1.0) > 1e-12) {
                    sb.append(c).append("x");
                } else {
                    sb.append("x");
                }
            } else {
                if (Math.abs(c - 1.0) > 1e-12) {
                    sb.append(c).append("x^").append(i);
                } else {
                    sb.append("x^").append(i);
                }
            }
        }
        return sb.toString();
    }
}
