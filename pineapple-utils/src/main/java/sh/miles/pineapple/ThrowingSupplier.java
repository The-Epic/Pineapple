package sh.miles.pineapple;

import org.jetbrains.annotations.ApiStatus;

/**
 * A supplier that can throw a checked exception
 *
 * @param <R> the type to be supplied
 */
@ApiStatus.Internal
@FunctionalInterface
public interface ThrowingSupplier<R> {

    /**
     * Gets the return type by applying the code inside the get method
     *
     * @return the return type
     * @throws Exception the checked exception thrown
     */
    R get() throws Exception;
}
