package sh.miles.pineapple.menu.slot;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.menu.ClickEvent;

/**
 * A Basic slot implementation that does the minimum implementation based on the expectation of an implementation of
 * {@link Slot}
 *
 * @since 1.0.0
 */
public class BasicSlot implements Slot {

    protected final int slotIndex;
    protected final Inventory inventory;
    @NotNull
    protected ItemStack content;
    protected ClickEvent event;
    protected boolean autoSync;

    /**
     * Creates a new BasicSlot
     *
     * @param inventory the inventory
     * @param slotIndex the slotIndex
     */
    public BasicSlot(@NotNull final Inventory inventory, final int slotIndex) {
        this.slotIndex = slotIndex;
        this.inventory = inventory;
        this.content = new ItemStack(Material.AIR);
        this.event = ClickEvent.EMPTY;
        this.autoSync = true;
    }

    @Override
    public void click(@NotNull final Player viewer, @NotNull final InventoryClickEvent event) {
        this.event.click(viewer, event);
    }

    @Override
    public void pushContent() {
        this.inventory.setItem(this.slotIndex, this.content);
    }

    @Override
    @NotNull
    public ItemStack getContent() {
        return content;
    }

    @Override
    public int getSlotIndex() {
        return this.slotIndex;
    }

    @Override
    public boolean hasContent() {
        return !this.content.getType().isAir();
    }

    @Override
    public boolean isAutoSync() {
        return autoSync;
    }

    @Override
    public void setContent(final ItemStack content) {
        this.content = content == null ? new ItemStack(Material.AIR) : content;
        if (autoSync) {
            pushContent();
        }
    }

    @Override
    public void setDetail(@Nullable final ItemStack content, @Nullable final ClickEvent event) {
        setContent(content);
        setEvent(event);
    }

    @Override
    public void setEvent(@Nullable final ClickEvent event) {
        this.event = event == null ? ClickEvent.EMPTY : event;
    }

    @Override
    public void setAutoSync(final boolean autoSync) {
        this.autoSync = autoSync;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Maps the size of an inventory a slot array
     *
     * @param inventory the inventory
     * @return the slots
     */
    public static Slot[] createMapping(@NotNull final Inventory inventory) {
        Slot[] slots = new Slot[inventory.getSize()];
        for (int i = 0; i < inventory.getSize(); i++) {
            slots[i] = new BasicSlot(inventory, i);
        }
        return slots;
    }
}
