package sh.miles.pineapple.menu;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Simple Menu implementation with more specific details
 *
 * @param <T> the MenuScene instance type
 * @since 1.0.0-SNAPSHOT
 */
public abstract class SimpleMenu<T extends MenuScene> implements Menu<T> {

    private final T scene;
    private final Player viewer;
    protected final Inventory inventory;
    private final Map<Integer, MenuItem> slots;
    protected ItemStack backdrop = new ItemStack(Material.AIR);

    protected SimpleMenu(@NotNull final Function<Player, T> function, @NotNull final Player viewer) {
        Preconditions.checkArgument(function != null, "The given function must not be null");
        Preconditions.checkArgument(viewer != null, "The given viewer must not be null");
        this.scene = function.apply(viewer);
        this.viewer = viewer;
        this.inventory = scene.getBukkitView().getTopInventory();
        this.slots = new HashMap<>();
    }

    private void updateMenu() {
        slots.forEach((slot, item) -> inventory.setItem(slot, item.item(viewer)));
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, backdrop);
                continue;
            }

            if (inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, backdrop);
            }
        }
    }

    protected final void setItem(final int slot, @NotNull final MenuItem item) {
        slots.put(slot, item);
    }

    @NotNull
    @Override
    public Optional<MenuItem> getItem(final int slot) {
        return Optional.ofNullable(slots.get(slot));
    }

    @NotNull
    @Override
    public T getScene() {
        return this.scene;
    }

    @Override
    public void open() {
        updateMenu();
        viewer.openInventory(getScene().getBukkitView());
    }

    @Override
    public void close() {
    }

    @Override
    public void handleClick(@NotNull final InventoryClickEvent event) {
        event.setCancelled(true);
        final int slot = event.getSlot();
        getItem(slot).ifPresent(item -> item.click((Player) event.getWhoClicked(), event));
    }

    @Override
    public void handleOpen(@NotNull final InventoryOpenEvent event) {
    }

    @Override
    public void handleClose(@NotNull final InventoryCloseEvent event) {
    }
}
