package sh.miles.pineapple.nms.impl.v1_20_R1.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;
import sh.miles.pineapple.collection.registry.FrozenRegistry;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.api.menu.MenuType;
import sh.miles.pineapple.nms.api.world.damagesource.DamageEffect;
import sh.miles.pineapple.nms.impl.v1_20_R1.inventory.PineappleMenuType;
import sh.miles.pineapple.nms.impl.v1_20_R1.world.damagesource.PineappleDamageEffect;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class PineappleNmsRegistry<P extends RegistryKey<NamespacedKey>, M> extends FrozenRegistry<P, NamespacedKey> {

    public PineappleNmsRegistry(Class<? super P> interfaceClass, Registry<M> minecraftRegistry, BiFunction<NamespacedKey, M, P> minecraftToBukkit) {
        super(() -> {
                    try {
                        Class.forName(interfaceClass.getName());
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Could not load registry class " + interfaceClass, e);
                    }
                    return minecraftRegistry.keySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    CraftNamespacedKey::fromMinecraft,
                                    (ResourceLocation key) -> minecraftToBukkit.apply(CraftNamespacedKey.fromMinecraft(key), minecraftRegistry.get(key))
                            ));
                }
        );
    }

    public PineappleNmsRegistry(Class<? super P> interfaceClass, M[] minecraftEnumEntries, BiFunction<NamespacedKey, M, P> minecraftToBukkit) {
        super(() -> {
            try {
                Class.forName(interfaceClass.getName());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not load registry class " + interfaceClass, e);
            }
            return Arrays.stream(minecraftEnumEntries).collect(Collectors.toMap(
                    (M value) -> NamespacedKey.minecraft(value.toString().toLowerCase()),
                    (M value) -> minecraftToBukkit.apply(NamespacedKey.minecraft(value.toString().toLowerCase()), value)
            ));
        });
    }

    public static <P extends RegistryKey<NamespacedKey>> FrozenRegistry<?, ?> makeRegistry(Class<? super P> interfaceClass) {
        final RegistryAccess access = ((CraftServer) Bukkit.getServer()).getHandle().getServer().registryAccess();
        if (interfaceClass == MenuType.class) {
            return new PineappleNmsRegistry<>(MenuType.class, access.registryOrThrow(Registries.MENU), PineappleMenuType::new);
        }
        if (interfaceClass == DamageEffect.class) {
            return new PineappleNmsRegistry<>(DamageEffect.class, DamageEffects.values(), PineappleDamageEffect::new);
        }

        throw new IllegalStateException("No such registry %s found".formatted(interfaceClass.getName()));
    }
}
