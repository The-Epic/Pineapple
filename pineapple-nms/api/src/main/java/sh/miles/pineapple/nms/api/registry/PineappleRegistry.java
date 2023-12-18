package sh.miles.pineapple.nms.api.registry;

import org.bukkit.NamespacedKey;
import sh.miles.pineapple.collection.registry.Registry;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.api.loader.NMSManager;
import sh.miles.pineapple.nms.api.menu.MenuType;
import sh.miles.pineapple.nms.api.world.damagesource.DamageEffect;
import sh.miles.pineapple.nms.api.world.damagesource.DamageType;

public interface PineappleRegistry {

    Registry<MenuType<?>, NamespacedKey> MENU = make(MenuType.class);
    Registry<DamageEffect, NamespacedKey> DAMAGE_EFFECT = make(DamageEffect.class);
    Registry<DamageType, NamespacedKey> DAMAGE_TYPE = make(DamageType.class);

    private static <T extends RegistryKey<NamespacedKey>> Registry<T, NamespacedKey> make(Class<? super T> interfaceClass) {
        return NMSManager.getPineapple().getRegistry(interfaceClass);
    }

}
