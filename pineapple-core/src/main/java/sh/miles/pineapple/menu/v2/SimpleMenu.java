package sh.miles.pineapple.menu.v2;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.PineappleLib;
import sh.miles.pineapple.menu.v2.slot.Slot;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.function.Function;

public abstract class SimpleMenu<T extends MenuScene> implements Menu<T> {

    private final T scene;
    private final Player viewer;
    private final Inventory inventory;
    private final Slot[] slots;

    public SimpleMenu(@NotNull final Function<Player, T> function, @NotNull final Player viewer) {
        this.scene = function.apply(viewer);
        this.viewer = viewer;
        this.inventory = scene.getBukkitView().getTopInventory();
        this.slots = Slot.createEmptyArray(inventory);
        init(); // setup everything prior to first open
    }

    @Override
    public final void open() {
        PineappleLib.getMenuManager().register(this);
        this.viewer.openInventory(inventory);
    }

    @Override
    public final void close() {
        this.viewer.closeInventory();
        PineappleLib.getMenuManager().unregister(this.inventory);
    }

    @NotNull
    @Override
    public T getScene() {
        return this.scene;
    }

    protected void setSlotAt(final int index, @NotNull final Slot slot) {
        Preconditions.checkArgument(slot != null, "The given slot must not be null");
        Preconditions.checkArgument(index < slots.length && index > -1, "The given index must be within the inventory size range");

        this.slots[index] = slot;
    }

    protected void setEventAt(final int slot, @Nullable final ClickEvent event) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, "The given slot must be within the inventory size range");
        this.slots[slot].setEvent(event);
    }

    protected void setContentAt(final int slot, @Nullable ItemStack content) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, "The given slot must be within the inventory size range");
        this.slots[slot].setContent(content);
    }

    protected void setSlotDetail(final int slot, @Nullable ItemStack content, @Nullable ClickEvent event) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, "The given slot must be within the inventory size range");
        this.slots[slot].setEvent(event);
        this.slots[slot].setContent(content);
    }

    @Override
    public void handleClick(@NotNull final InventoryClickEvent event) {
        event.setCancelled(true);
        final int index = event.getSlot();
        this.slots[index].click((Player) event.getWhoClicked(), event);
    }
}
