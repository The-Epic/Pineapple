package sh.miles.pineapple.nms.api.world.damagesource;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.api.registry.PineappleRegistry;

/**
 * An Effect of Damage represented internally within NMS. These Effects Usually display an effect on the screen
 */
public interface DamageEffect extends RegistryKey {

    DamageEffect HURT = get("hurt");
    DamageEffect THORNS = get("thorns");
    DamageEffect DROWNING = get("drowning");
    DamageEffect BURNING = get("burning");
    DamageEffect POKING = get("poking");
    DamageEffect FREEZING = get("freezing");

    /**
     * Gets the sound of this damage effect
     *
     * @return the noise
     */
    Sound sound();

    private static DamageEffect get(String id) {
        return (DamageEffect) PineappleRegistry.DAMAGE_EFFECT.getOrNull(NamespacedKey.minecraft(id).toString());
    }
}
