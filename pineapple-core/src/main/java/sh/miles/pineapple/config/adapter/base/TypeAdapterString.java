package sh.miles.pineapple.config.adapter.base;

public interface TypeAdapterString<S, R> extends TypeAdapter<S, R> {

    R fromString(String value);

    String toString(R value);
}
