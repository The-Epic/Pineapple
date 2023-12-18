package sh.miles.pineapple.collection.registry;

/**
 * implemented by objects which can be added to Registries
 */
public interface RegistryKey<K> {
    /**
     * @return the key of this object
     */
    K getKey();
}
