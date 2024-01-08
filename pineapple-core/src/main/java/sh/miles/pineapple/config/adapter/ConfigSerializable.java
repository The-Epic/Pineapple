package sh.miles.pineapple.config.adapter;

public interface ConfigSerializable<S> {
    S serialize(S existing, boolean replace);
}
