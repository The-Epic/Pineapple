package sh.miles.pineapple.nms.api.registry;

import org.bukkit.NamespacedKey;
import sh.miles.pineapple.collection.registry.Registry;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.loader.NMSLoader;
import sh.miles.pineapple.nms.api.menu.MenuType;

public interface PineappleRegistry {

    Registry<MenuType<?>, NamespacedKey> MENU = make(MenuType.class);

    private static <T extends RegistryKey<NamespacedKey>> Registry<T, NamespacedKey> make(Class<? super T> interfaceClass) {
        return NMSLoader.getPineapple().getUnsafe().getRegistry(interfaceClass);
    }

}
