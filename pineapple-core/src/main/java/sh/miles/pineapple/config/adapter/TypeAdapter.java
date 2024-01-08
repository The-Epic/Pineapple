package sh.miles.pineapple.config.adapter;

public interface TypeAdapter<S, R> {

    Class<S> getSavedType();

    Class<R> getRuntimeType();

    R read(S value);

    S write(R value, S existing, boolean replace);
}
