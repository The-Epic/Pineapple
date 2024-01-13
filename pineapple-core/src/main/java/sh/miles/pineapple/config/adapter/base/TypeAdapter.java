package sh.miles.pineapple.config.adapter.base;

import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.config.ConfigType;

public interface TypeAdapter<S, R> extends RegistryKey<ConfigType<?>> {

    Class<S> getSavedType();

    Class<R> getRuntimeType();

    R read(S value);

    S write(R value, S existing, boolean replace);

    @Override
    default ConfigType<R> getKey() {
        return new ConfigType<>(getRuntimeType());
    }
}
