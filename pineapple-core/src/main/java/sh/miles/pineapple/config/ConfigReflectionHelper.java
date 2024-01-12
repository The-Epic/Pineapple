package sh.miles.pineapple.config;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;

@ApiStatus.Internal
public class ConfigReflectionHelper {

    /**
     * Sets a value of a ConfigField
     *
     * @param field   the field
     * @param value   the value
     * @param wrapped wrapper object
     * @param <T>     wrapper object type
     * @throws ReflectiveOperationException if an error occurs
     */
    public static <T> void setField(ConfigField field, Object value, T wrapped) throws ReflectiveOperationException {
        Field internal = field.getField();
        if (field.isStatic()) {
            internal.set(null, value);
        } else {
            boolean access = internal.canAccess(wrapped);
            if (!access) {
                internal.trySetAccessible();
            }

            internal.set(wrapped, value);

            if (!access) {
                internal.setAccessible(false);
            }
        }
    }

    /**
     * Gets a ConfigField
     *
     * @param field   the field
     * @param wrapped the wrapper object
     * @param <T>     the wrapper object type
     * @return the field value
     */
    public static <T> Object getField(ConfigField field, T wrapped) {
        Field internal = field.getField();
        if (field.isStatic()) {
            try {
                return internal.get(null);
            } catch (ReflectiveOperationException ex) {
                return null;
            }
        } else {
            boolean access = internal.canAccess(wrapped);
            if (!access) {
                internal.trySetAccessible();
            }

            try {
                return internal.get(wrapped);
            } catch (ReflectiveOperationException ex) {
                return null;
            } finally {
                if (!access) {
                    internal.setAccessible(false);
                }
            }
        }
    }
}
