package sh.miles.pineapple.menu;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.PineappleLib;
import sh.miles.pineapple.menu.slot.BasicSlot;
import sh.miles.pineapple.menu.slot.Slot;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Basic Menu implementation that does the minimum a basic {@link Menu} implementation should be expected of doing.
 *
 * @param <T> the type of menu that the player is viewing
 * @since 1.0.0-SNAPSHOT
 */
public abstract class BasicMenu<T extends MenuScene> implements Menu<T> {

    private static final String OUT_OF_BOUNDS = "The given slot must be within the inventory size range";

    private final T scene;
    private final Player viewer;
    private final Inventory inventory;
    private final Slot[] slots;

    /**
     * Creates a new BasicMenu
     *
     * @param function the function used to create a MenuScene to be displayed to the viewer
     * @param viewer   the viewer of this Menu
     * @since 1.0.0-SNAPSHOT
     */
    protected BasicMenu(@NotNull final Function<Player, T> function, @NotNull final Player viewer) {
        this.scene = function.apply(viewer);
        this.viewer = viewer;
        this.inventory = scene.getBukkitView().getTopInventory();
        this.slots = BasicSlot.createMapping(inventory);
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

    /**
     * Sets the slot at the specified index
     *
     * @param index        the index
     * @param slotSupplier the function used to build the slot
     * @since 1.0.0-SNAPSHOT
     */
    protected void setSlotAt(final int index, @NotNull final Supplier<Slot> slotSupplier) {
        Preconditions.checkArgument(slotSupplier != null, "The given slot must not be null");
        Preconditions.checkArgument(index < slots.length && index > -1, "The given index must be within the inventory size range");

        Slot slot = slotSupplier.get();
        Preconditions.checkArgument(slot != null, OUT_OF_BOUNDS);
        this.slots[index] = slot;
    }

    /**
     * Sets the event at the specified index
     *
     * @param slot  the slot to set the event at
     * @param event the event to set
     * @since 1.0.0-SNAPSHOT
     */
    protected void setEventAt(final int slot, @Nullable final ClickEvent event) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, OUT_OF_BOUNDS);
        this.slots[slot].setEvent(event);
    }

    /**
     * Sets the content at the specified index
     *
     * @param slot    the slot to set the content at
     * @param content the content to set
     * @since 1.0.0-SNAPSHOT
     */
    protected void setContentAt(final int slot, @Nullable ItemStack content) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, OUT_OF_BOUNDS);
        this.slots[slot].setContent(content);
    }

    /**
     * Sets the content at the specified index, only if that slot has no content yet
     *
     * @param slot    the slot to set the content at
     * @param content the content to set
     * @return true if the content was set, otherwise false
     * @since 1.0.0-SNAPSHOT
     */
    protected boolean setContentAtIfAbsent(final int slot, @Nullable ItemStack content) {
        Preconditions.checkArgument(slot < inventory.getSize() && slot > -1, OUT_OF_BOUNDS);

        Slot target = this.slots[slot];
        if (!target.hasContent()) {
            target.setContent(content);
            return true;
        }

        return false;
    }

    /**
     * Sets all details of a slot at the specified index
     *
     * @param slot    the slot to set the details of
     * @param content the content of the slot
     * @param event   the event the slot will execute when clicked
     * @since 1.0.0-SNAPSHOT
     */
    protected void setSlotDetail(final int slot, @Nullable ItemStack content, @Nullable ClickEvent event) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, OUT_OF_BOUNDS);
        this.slots[slot].setDetail(content, event);
    }

    /**
     * Gets a slot at the specified slot
     *
     * @param slot the slot to get
     * @return the slot at the specified slot
     */
    @NotNull
    protected Slot getSlot(final int slot) {
        Preconditions.checkArgument(slot < slots.length && slot > -1, OUT_OF_BOUNDS);
        return this.slots[slot];
    }

    @Override
    public void handleClick(@NotNull final InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
            event.setCancelled(true);
            final int index = event.getSlot();
            this.slots[index].click((Player) event.getWhoClicked(), event);
        }
    }

    @Override
    public void handleOpen(@NotNull final InventoryOpenEvent event) {
    }

    @Override
    public void handleClose(@NotNull final InventoryCloseEvent event) {
    }
}
