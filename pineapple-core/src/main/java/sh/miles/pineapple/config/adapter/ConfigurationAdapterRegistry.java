package sh.miles.pineapple.config.adapter;

import sh.miles.pineapple.collection.registry.WriteableRegistry;
import sh.miles.pineapple.config.ConfigType;
import sh.miles.pineapple.config.adapter.base.TypeAdapter;

public class ConfigurationAdapterRegistry extends WriteableRegistry<TypeAdapter<?, ?>, ConfigType<?>> {

    public static final ConfigurationAdapterRegistry INSTANCE = new ConfigurationAdapterRegistry();

    private ConfigurationAdapterRegistry() {
        registerPrimitiveAdapter(long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));
        register(new PrimitiveAdapter<>(Long.class, Long::parseLong));

        registerPrimitiveAdapter(int.class, new PrimitiveAdapter<>(Integer.class, Integer::parseInt));
        register(new PrimitiveAdapter<>(Integer.class, Integer::parseInt));

        registerPrimitiveAdapter(double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));
        register(new PrimitiveAdapter<>(Double.class, Double::parseDouble));

        registerPrimitiveAdapter(float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));
        register(new PrimitiveAdapter<>(Float.class, Float::parseFloat));

        registerPrimitiveAdapter(boolean.class, new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));
        register(new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));

        registerPrimitiveAdapter(char.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));
        register(new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));

        register(new StringAdapter());
        register(new NamespacedKeyAdapter());
        register(new MaterialAdapter());
        register(new ChatColorAdapter());
        register(new ItemStackAdapter());
        register(new PineappleComponentAdapter());
    }

    private <S, R> void registerPrimitiveAdapter(Class<R> clazz, TypeAdapter<S, R> adapter) {
        super.registry.put(new ConfigType<>(clazz), adapter);
    }
}
