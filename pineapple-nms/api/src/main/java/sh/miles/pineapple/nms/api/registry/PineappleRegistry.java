package sh.miles.pineapple.nms.api.registry;

import sh.miles.pineapple.collection.registry.Registry;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.api.loader.NMSManager;
import sh.miles.pineapple.nms.api.menu.MenuType;
import sh.miles.pineapple.nms.api.world.damagesource.DamageEffect;
import sh.miles.pineapple.nms.api.world.damagesource.DamageType;

public interface PineappleRegistry {

    Registry<MenuType<?>> MENU = make(MenuType.class);
    Registry<DamageEffect> DAMAGE_EFFECT = make(DamageEffect.class);
    Registry<DamageType> DAMAGE_TYPE = make(DamageType.class);

    private static <T extends RegistryKey> Registry<T> make(Class<? super T> interfaceClass) {
        return NMSManager.getPineapple().getRegistry(interfaceClass);
    }

}
