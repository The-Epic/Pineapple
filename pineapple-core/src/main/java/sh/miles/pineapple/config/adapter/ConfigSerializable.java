package sh.miles.pineapple.config.adapter;

/**
 * A static deserialize(type) method is needed for this to function
 *
 * @param <S> the saved type
 */
public interface ConfigSerializable<S> {
    S serialize(S existing, boolean replace);
}
