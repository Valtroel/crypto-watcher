package by.kulevets.cryptocurrencywatcher.service.validation;


/**
 * The interface Validation.
 *
 * @param <T> the type parameter
 * @author Vladislav Kulevets
 * @since 27.01.2022
 */
public interface Validation<T> {
    /**
     * Is valid boolean.
     *
     * @param t the t
     * @return the boolean
     */
    boolean isValid(T t);
}
