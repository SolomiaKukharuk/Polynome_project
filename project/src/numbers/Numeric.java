package numbers;

/**
 * Базовий інтерфейс для всіх числових типів у проєкті.
 *
 * <p>Дозволяє отримати значення числа у вигляді {@code double}.
 */
public interface Numeric {

    /**
     * Повертає значення числа у форматі {@code double}.
     *
     * @return значення числа як {@code double}
     */
    double toDouble();
}
