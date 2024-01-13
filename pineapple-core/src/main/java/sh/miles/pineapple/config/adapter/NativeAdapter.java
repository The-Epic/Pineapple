package sh.miles.pineapple.config.adapter;

import org.bukkit.configuration.MemorySection;
import sh.miles.pineapple.PineappleLib;
import sh.miles.pineapple.config.ConfigField;
import sh.miles.pineapple.config.ConfigReflectionHelper;
import sh.miles.pineapple.config.ConfigType;
import sh.miles.pineapple.config.adapter.base.TypeAdapter;
import sh.miles.pineapple.config.annotation.ConfigEntry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

class NativeAdapter<T> implements TypeAdapter<Map<String, Object>, T> {
    private final Class<T> clazz;
    private final List<ConfigField> fields = new ArrayList<>();

    /**
     * Creates a new NativeAdapter
     *
     * @param clazz the class
     */
    public NativeAdapter(Class<T> clazz) {
        this.clazz = clazz;

        cacheFields(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<T> getRuntimeType() {
        return this.clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T read(Map<String, Object> value) {
        T object;
        try {
            Constructor<T> constructor = this.clazz.getDeclaredConstructor();
            object = constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            PineappleLib.getLogger().log(Level.WARNING, "Failed to create instance");
            return null;
        }

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            if (!value.containsKey(path)) {
                PineappleLib.getLogger().log(Level.WARNING, "No configuration entry found for {0}", path);
                continue;
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) PineappleLib.getConfigurationManager().getAdapter(type);
            if (adapter == null) {
                PineappleLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getClass());
                continue;
            }

            Class<Object> saved = adapter.getSavedType();
            Object savedValue = getSavedValue(value, path, saved);
            if (!saved.isInstance(savedValue)) {
                PineappleLib.getLogger().log(Level.WARNING, "Wrong saved type for {0}, {1} != {2}", new Object[]{path, savedValue.getClass(), saved});
                continue;
            }

            Object readValue = adapter.read(saved.cast(savedValue));

            if (readValue == null) {
                PineappleLib.getLogger().log(Level.WARNING, "Failed to read value for {0} of type {1}", new Object[]{path, type.getType()});
                continue;
            }

            try {
                ConfigReflectionHelper.setField(field, readValue, object);
            } catch (ReflectiveOperationException ex) {
                PineappleLib.getLogger().log(Level.WARNING, "Failed to read value for {0} of type {1}", new Object[]{path, type.getType()});
                ex.printStackTrace();
            }
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> write(T value, Map<String, Object> map, boolean replace) {
        if (map == null) {
            map = new LinkedHashMap<>();
        }

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) PineappleLib.getConfigurationManager().getAdapter(type);
            if (adapter == null) {
                PineappleLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getType());
                continue;
            }

            Object writeValue = ConfigReflectionHelper.getField(field, value);
            if (writeValue == null) {
                PineappleLib.getLogger().log(Level.WARNING, "null");
                continue;
            }

            Class<Object> saved = adapter.getSavedType();
            Class<Object> runtime = adapter.getRuntimeType();
            if (!runtime.isInstance(writeValue)) {
                PineappleLib.getLogger().log(Level.WARNING, "Wrong runtime type for {0}, {1} != {2}", new Object[]{path, writeValue.getClass(), runtime});
                continue;
            }

            Object existing = getSavedValue(map, path, saved);
            if (existing != null && !saved.isInstance(existing)) {
                PineappleLib.getLogger().log(Level.WARNING, "Wrong saved type for {0}, {1} != {2}", new Object[]{path, existing.getClass(), saved});
                existing = null;
            }

            Object val = adapter.write(runtime.cast(writeValue), saved.cast(existing), replace);
            if (val != null) {
                map.put(path, val);
            }
        }
        return map;
    }

    private Object getSavedValue(Map<String, Object> value, String path, Class<Object> expectedType) {
        Object saved = value.get(path);

        if (saved instanceof MemorySection section && Map.class.isAssignableFrom(expectedType)) {
            return section.getValues(false);
        }
        return saved;
    }

    private void cacheFields(Class<? super T> clazz) {
        int index = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) {
                continue;
            }

            this.fields.add(index++, new ConfigField(field));
        }

        Class<? super T> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            cacheFields(superClass);
        }
    }
}
