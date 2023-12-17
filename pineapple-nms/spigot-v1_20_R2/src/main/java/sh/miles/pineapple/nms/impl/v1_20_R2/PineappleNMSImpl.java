package sh.miles.pineapple.nms.impl.v1_20_R2;

import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftContainer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.nms.api.PineappleNMS;
import sh.miles.pineapple.nms.api.menu.MenuType;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;
import sh.miles.pineapple.nms.impl.v1_20_R2.inventory.PineappleMenuType;
import sh.miles.pineapple.nms.impl.v1_20_R2.internal.ComponentUtils;

public class PineappleNMSImpl implements PineappleNMS {

    @Nullable
    @Override
    public <T extends MenuScene> T openContainer(@NotNull final HumanEntity human, final MenuType<T> type, final String title) {
        final T scene = type.create(human, title);
        this.openContainer(human, scene);
        return scene;
    }

    @Nullable
    @Override
    public <T extends MenuScene> T openContainer(@NotNull final HumanEntity human, final MenuType<T> type, final BaseComponent... title) {
        final T scene = type.create(human, title);
        this.openContainer(human, scene);
        return scene;
    }

    @NotNull
    @Override
    public MenuScene openContainer(@NotNull final HumanEntity human, @NotNull final MenuScene scene) {
        human.openInventory(scene.getBukkitView());
        return scene;
    }

    @Nullable
    @Override
    public InventoryView openInventory(@NotNull final Player player, @NotNull final Inventory inventory, @NotNull final BaseComponent... title) {
        ServerPlayer nms = ((CraftPlayer) player).getHandle();
        // legacy method to be replaced
        net.minecraft.world.inventory.MenuType<?> mojType = CraftContainer.getNotchInventoryType(inventory);

        if (mojType == null) {
            throw new IllegalArgumentException("could not find menu type for inventory type of " + inventory.getType());
        }
        AbstractContainerMenu menu = new CraftContainer(inventory, nms, nms.nextContainerCounter());
        menu = CraftEventFactory.callInventoryOpenEvent(nms, menu);
        if (menu == null) {
            throw new IllegalStateException("Unable to open menu for unknown reason");
        }

        nms.connection.send(new ClientboundOpenScreenPacket(menu.containerId, mojType, ComponentUtils.toMinecraftChat(title)));
        nms.containerMenu = menu;
        nms.initMenu(menu);
        return nms.containerMenu.getBukkitView();
    }

    @Nullable
    @Override
    public MenuType<?> getMenuType(final String id) {
        return new PineappleMenuType<>(NamespacedKey.minecraft(id), getRegistry(Registries.MENU).get(new ResourceLocation("minecraft:%s".formatted(id))));
    }

    private <T> Registry<T> getRegistry(ResourceKey<? extends Registry<T>> registryKey) {
        return ((CraftServer) Bukkit.getServer()).getHandle().getServer().registryAccess().registryOrThrow(registryKey);
    }
}
