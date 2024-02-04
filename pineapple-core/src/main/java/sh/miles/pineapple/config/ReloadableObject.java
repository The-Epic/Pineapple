package sh.miles.pineapple.config;

import java.io.File;
import java.lang.reflect.Method;

public class ReloadableObject<T> extends ConfigReloadable<T> {
    private final T wrapped;

    /**
     * Creates a new ReloadableObject
     *
     * @param file   the file used to load the object
     * @param object the object
     * @since 1.0.0-SNAPSHOT
     */
    @SuppressWarnings("unchecked")
    public ReloadableObject(File file, T object) {
        super(file, (Class<T>) object.getClass());

        this.wrapped = object;
    }

    @Override
    protected void postLoad(Method postLoadMethod) {
        try {
            boolean access = postLoadMethod.canAccess(this.wrapped);
            if (!access) {
                postLoadMethod.trySetAccessible();
            }

            postLoadMethod.invoke(this.wrapped);

            if (!access) {
                postLoadMethod.setAccessible(false);
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void setField(ConfigField field, Object value) throws ReflectiveOperationException {
        ConfigReflectionHelper.setField(field, value, this.wrapped);
    }

    @Override
    protected Object getField(ConfigField field) {
        return ConfigReflectionHelper.getField(field, this.wrapped);
    }

    public T getWrapped() {
        return this.wrapped;
    }
}
