package sh.miles.pineapple.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An Option that can contain or not contain a value
 *
 * @param <E> the entry type
 */
public sealed class Option<E> permits Option.None, Option.Some {

    /**
     * Maps the given option to another value if available. Otherwise None is returned
     *
     * @param mapper the mapper
     * @param <R>    the returned type
     * @return the new option
     */
    public <R> Option<R> map(@NotNull final Function<E, R> mapper) {
        if (!(this instanceof Some<E> some)) {
            return new None<>();
        }

        return new Some<>(mapper.apply(some.some));
    }

    /**
     * Gets some value or throws
     *
     * @return the value
     * @throws IllegalStateException if no value was found
     */
    public E orThrow() throws IllegalStateException {
        if (!(this instanceof Some<E> some)) {
            throw new IllegalStateException("Unable to find some value");
        }

        return some.some;
    }

    /**
     * Represents some value
     *
     * @param <E> the entry type
     */
    public static final class Some<E> extends Option<E> {

        private final E some;

        public Some(E some) {
            this.some = Objects.requireNonNull(some);
        }

        public E some() {
            return this.some;
        }

    }

    /**
     * Represents no value
     *
     * @param <E> arbitrary type
     */
    public static final class None<E> extends Option<E> {
    }

}
