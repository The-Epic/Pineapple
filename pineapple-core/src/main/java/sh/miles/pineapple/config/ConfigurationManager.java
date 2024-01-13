package sh.miles.pineapple.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import sh.miles.pineapple.chat.PineappleComponent;
import sh.miles.pineapple.collection.WeightedRandom;
import sh.miles.pineapple.config.adapter.CollectionAdapter;
import sh.miles.pineapple.config.adapter.ColorAdapter;
import sh.miles.pineapple.config.adapter.ConfigSerializable;
import sh.miles.pineapple.config.adapter.ConfigSerializableAdapter;
import sh.miles.pineapple.config.adapter.EnumAdapter;
import sh.miles.pineapple.config.adapter.ItemStackAdapter;
import sh.miles.pineapple.config.adapter.MapAdapter;
import sh.miles.pineapple.config.adapter.MaterialAdapter;
import sh.miles.pineapple.config.adapter.NamespacedKeyAdapter;
import sh.miles.pineapple.config.adapter.NativeAdapter;
import sh.miles.pineapple.config.adapter.PineappleComponentAdapter;
import sh.miles.pineapple.config.adapter.PrimitiveAdapter;
import sh.miles.pineapple.config.adapter.StringAdapter;
import sh.miles.pineapple.config.adapter.TypeAdapter;
import sh.miles.pineapple.config.adapter.TypeAdapterString;
import sh.miles.pineapple.config.adapter.WeightedRandomAdapter;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The ConfigurationManager handles all config creation, loading and TypeAdapters
 *
 * @since 1.0.0-SNAPSHOT
 */
public class ConfigurationManager {

    private final Map<ConfigType<?>, TypeAdapter<?, ?>> adapters = new HashMap<>();

    /**
     * Creates a new configuration manager
     *
     * @param plugin the plugin to use
     * @since 1.0.0-SNAPSHOT
     */
    @ApiStatus.Internal
    public ConfigurationManager(Plugin plugin) {

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
        registerTypeAdapter(ChatColor.class, new ColorAdapter());
        registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
        registerTypeAdapter(PineappleComponent.class, new PineappleComponentAdapter());

    }

    /**
     * Register a TypeAdapter for data (de)serialization
     *
     * @param clazz   runtime class
     * @param adapter TypeAdapter that handles serialization/deserialization
     * @param <S>     serialized type
     * @param <R>     runtime class
     * @since 1.0.0-SNAPSHOT
     */
    public <S, R> void registerTypeAdapter(Class<R> clazz, TypeAdapter<S, R> adapter) {
        adapters.put(new ConfigType<>(clazz), adapter);
    }

    /**
     * Create an instance based config
     *
     * @param file   the file to save/load
     * @param target instance of config class
     * @param <T>    config class
     * @return ConfigReloadable instance
     * @since 1.0.0-SNAPSHOT
     */
    public <T> ReloadableObject<T> createReloadable(File file, T target) {
        return new ReloadableObject<>(file, target);
    }

    /**
     * @param file   the file to save/load
     * @param target config class
     * @param <T>    config class
     * @return ConfigReloadable instance
     * @since 1.0.0-SNAPSHOT
     */
    public <T> ReloadableClass<T> createStaticReloadable(File file, Class<T> target) {
        return new ReloadableClass<>(file, target);
    }

    /**
     * Gets a TypeAdapterString for the given class
     *
     * @param clazz runtime class
     * @param <S>   serialized type
     * @param <R>   runtime class
     * @return TypeAdapterString instance
     * @since 1.0.0-SNAPSHOT
     */
    public <S, R> TypeAdapterString<S, R> getStringAdapter(Class<R> clazz) {
        return getStringAdapter(new ConfigType<>(clazz));
    }

    /**
     * Gets a TypeAdapterString for the given type
     *
     * @param type config type
     * @param <S>  serialized type
     * @param <R>  runtime class
     * @return TypeAdapterString instance
     * @since 1.0.0-SNAPSHOT
     */
    @SuppressWarnings("unchecked")
    public <S, R> TypeAdapterString<S, R> getStringAdapter(ConfigType<R> type) {
        TypeAdapter<?, R> adapter = getAdapter(type);

        if (adapter instanceof TypeAdapterString<?, ?> stringAdapter) {
            return (TypeAdapterString<S, R>) stringAdapter;
        }

        return null;
    }

    /**
     * Gets the type adapter for the given class
     *
     * @param clazz runtime class
     * @param <S>   serialized type
     * @param <R>   runtime class
     * @return TypeAdapter instance
     * @since 1.0.0-SNAPSHOT
     */
    public <S, R> TypeAdapter<S, R> getAdapter(Class<R> clazz) {
        return getAdapter(new ConfigType<>(clazz));
    }

    /**
     * Gets the type adapter for given type
     *
     * @param type config type
     * @param <S>  serialized type
     * @param <R>  runtime class
     * @return TypeAdapter instance
     * @since 1.0.0-SNAPSHOT
     */
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
}
