package sh.miles.pineapple.nms.impl.v1_20_R1.world.damagesource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;
import sh.miles.pineapple.nms.api.registry.PineappleRegistry;
import sh.miles.pineapple.nms.api.world.damagesource.DamageEffect;
import sh.miles.pineapple.nms.api.world.damagesource.DamageType;

public class PineappleDamageType implements DamageType {

    private final net.minecraft.world.damagesource.DamageType damageType;
    private final NamespacedKey key;

    public PineappleDamageType(NamespacedKey key, net.minecraft.world.damagesource.DamageType damageType) {
        this.damageType = damageType;
        this.key = key;
    }

    @Override
    public float exhaustion() {
        return damageType.exhaustion();
    }

    @Override
    public DamageEffect effect() {
        return PineappleDamageEffect.minecraftToPineapple(damageType.effects());
    }

    @Override
    public String getKey() {
        return key.toString();
    }

    public static DamageType minecraftToPineapple(net.minecraft.world.damagesource.DamageType damageType) {
        Registry<net.minecraft.world.damagesource.DamageType> registry = ((CraftServer) Bukkit.getServer()).getHandle().getServer().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return PineappleRegistry.DAMAGE_TYPE.getOrNull(CraftNamespacedKey.fromMinecraft(registry.getKey(damageType)).toString());
    }
}
