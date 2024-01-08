package sh.miles.pineapple.config;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import sh.miles.pineapple.chat.PineappleComponent;
import sh.miles.pineapple.collection.WeightedRandom;
import sh.miles.pineapple.config.adapter.*;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ConfigurationManager {

    private final Map<ConfigType<?>, TypeAdapter<?, ?>> adapters = new HashMap<>();
    private final Logger logger;

    public ConfigurationManager(Plugin plugin) {
        this.logger = plugin.getLogger();

        registerTypeAdapter(long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));
        registerTypeAdapter(Long.class, new PrimitiveAdapter<>(Long.class, Long::parseLong));

        registerTypeAdapter(int.class, new PrimitiveAdapter<>(Integer.class, Integer::parseInt));
        registerTypeAdapter(Integer.class, new PrimitiveAdapter<>(Integer.class, Integer::parseInt));

        registerTypeAdapter(double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));
        registerTypeAdapter(Double.class, new PrimitiveAdapter<>(Double.class, Double::parseDouble));

        registerTypeAdapter(float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));
        registerTypeAdapter(Float.class, new PrimitiveAdapter<>(Float.class, Float::parseFloat));

        registerTypeAdapter(boolean.class, new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));
        registerTypeAdapter(Boolean.class, new PrimitiveAdapter<>(Boolean.class, Boolean::parseBoolean));

        registerTypeAdapter(char.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));
        registerTypeAdapter(Character.class, new PrimitiveAdapter<>(Character.class, s -> s.charAt(0)));

        registerTypeAdapter(String.class, new StringAdapter());
        registerTypeAdapter(NamespacedKey.class, new NamespacedKeyAdapter());
        registerTypeAdapter(Material.class, new MaterialAdapter());
        registerTypeAdapter(Color.class, new ColorAdapter());
        registerTypeAdapter(PineappleComponent.class, new PineappleComponentAdapter());

    }

    public <S, R> void registerTypeAdapter(Class<R> clazz, TypeAdapter<S, R> adapter) {
        adapters.put(new ConfigType<>(clazz), adapter);
    }

    public <T> ReloadableObject<T> createReloadable(File file, T target) {
        return new ReloadableObject<>(file, target);
    }

    public <T> ReloadableClass<T> createStaticReloadable(File file, Class<T> target) {
        return new ReloadableClass<>(file, target);
    }

    public <S, R> TypeAdapterString<S, R> getStringAdapter(Class<R> clazz) {
        return getStringAdapter(new ConfigType<>(clazz));
    }

    @SuppressWarnings("unchecked")
    public <S, R> TypeAdapterString<S, R> getStringAdapter(ConfigType<R> type) {
        TypeAdapter<?, R> adapter = getAdapter(type);

        if (adapter instanceof TypeAdapterString<?, ?> stringAdapter) {
            return (TypeAdapterString<S, R>) stringAdapter;
        }

        return null;
    }

    public <S, R> TypeAdapter<S, R> getAdapter(Class<R> clazz) {
        return getAdapter(new ConfigType<>(clazz));
    }

    @SuppressWarnings("unchecked")
    public <S, R> TypeAdapter<S, R> getAdapter(ConfigType<R> type) {
        TypeAdapter<?, ?> adapter = this.adapters.get(type);
        if (adapter == null) {
            adapter = createAdapter(type);
            this.adapters.put(type, adapter);
        }

        return (TypeAdapter<S, R>) adapter;
    }

    private TypeAdapter<?, ?> createAdapter(ConfigType<?> type) {
        if (ConfigSerializable.class.isAssignableFrom(type.getType())) {
            return new ConfigSerializableAdapter<>(type);
        }

        if (WeightedRandom.class.isAssignableFrom(type.getType())) {
            return new WeightedRandomAdapter<>(type);
        }

        if (Enum.class.isAssignableFrom(type.getType())) {
            return new EnumAdapter<>(type.getType());
        }

        if (Collection.class.isAssignableFrom(type.getType())) {
            return new CollectionAdapter<>(type);
        }

        if (Map.class.isAssignableFrom(type.getType())) {
            return new MapAdapter<>(type);
        }

        return new NativeAdapter<>(type.getType());
    }

    public Logger getLogger() {
        return this.logger;
    }
}
