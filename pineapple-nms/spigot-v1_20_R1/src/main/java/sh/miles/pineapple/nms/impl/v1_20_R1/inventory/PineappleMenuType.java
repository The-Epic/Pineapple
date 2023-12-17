package sh.miles.pineapple.nms.impl.v1_20_R1.inventory;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftInventoryView;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;
import sh.miles.pineapple.nms.impl.v1_20_R1.internal.ComponentUtils;
import sh.miles.pineapple.nms.impl.v1_20_R1.inventory.scene.PineappleSceneFactory;

public record PineappleMenuType<T extends MenuScene>(NamespacedKey key,
                                                     MenuType<?> handle) implements sh.miles.pineapple.nms.api.menu.MenuType<T> {

    @Override
    public T create(@NotNull final HumanEntity player, @NotNull final String title) {
        return create(player, TextComponent.fromLegacyText(title));
    }

    @Override
    public T create(@NotNull final HumanEntity player, @NotNull final BaseComponent... title) {
        Preconditions.checkArgument(player != null, "there must be a valid player to use");
        Preconditions.checkArgument(title != null, "there must be a valid title to use");
        final AbstractContainerMenu menu = MenuBuilder.INSTANCE.build((CraftHumanEntity) player, this.handle);
        menu.setTitle(ComponentUtils.toMinecraftChat(title));
        menu.checkReachable = false;
        return PineappleSceneFactory.make(this, (CraftInventoryView) menu.getBukkitView());
    }

    public MenuType<?> getHandle() {
        return handle;
    }

    @NotNull
    @Override
    public String getKey() {
        return key.toString();
    }
}
