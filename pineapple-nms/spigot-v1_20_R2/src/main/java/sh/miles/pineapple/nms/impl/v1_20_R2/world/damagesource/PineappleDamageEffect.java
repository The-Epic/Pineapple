package sh.miles.pineapple.nms.impl.v1_20_R2.world.damagesource;

import net.minecraft.world.damagesource.DamageEffects;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_20_R2.CraftSound;
import sh.miles.pineapple.nms.api.registry.PineappleRegistry;
import sh.miles.pineapple.nms.api.world.damagesource.DamageEffect;

public record PineappleDamageEffect(NamespacedKey key, DamageEffects effects) implements DamageEffect {

    @Override
    public Sound sound() {
        return CraftSound.getBukkit(effects.sound());
    }

    @Override
    public String getKey() {
        return key.toString();
    }

    public static DamageEffect minecraftToPineapple(DamageEffects effects) {
        NamespacedKey key = NamespacedKey.minecraft(effects.toString().toLowerCase());
        return PineappleRegistry.DAMAGE_EFFECT.getOrNull(key.toString());
    }
}
