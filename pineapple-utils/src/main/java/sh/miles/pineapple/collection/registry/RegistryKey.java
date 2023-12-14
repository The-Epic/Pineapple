package sh.miles.pineapple.collection.registry;

/**
 * implemented by objects which can be added to Registries
 */
public interface RegistryKey {
    /**
     * @return the key of this object
     */
    String getKey();
}
