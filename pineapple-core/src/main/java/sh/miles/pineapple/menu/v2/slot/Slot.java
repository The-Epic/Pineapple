package sh.miles.pineapple.menu.v2.slot;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.menu.v2.ClickEvent;

public class Slot {

    protected final Inventory inventory;
    protected final int slot;
    private ClickEvent event;

    public Slot(@NotNull final Inventory inventory, final int slot, @NotNull final ClickEvent event, @NotNull final ItemStack content) {
        Preconditions.checkArgument(slot >= 0, "The provided slot number must be above or equal to 0");
        Preconditions.checkArgument(inventory != null, "The provided Inventory must not be null");
        Preconditions.checkArgument(event != null, "The provided ClickEvent must not be null");
        Preconditions.checkArgument(content != null, "The provided ItemStack must not be null");

        this.inventory = inventory;
        this.slot = slot;
        this.event = event;
        setContent(content);
    }

    public void click(@NotNull final Player viewer, @NotNull final InventoryClickEvent event) {
        this.event.click(viewer, event);
    }

    public void setEvent(@Nullable ClickEvent event) {
        event = event == null ? ClickEvent.EMPTY : event;
        this.event = event;
    }

    @NotNull
    public ItemStack getContent() {
        return inventory.getItem(this.slot);
    }

    public void setContent(@Nullable ItemStack content) {
        content = content == null ? new ItemStack(Material.AIR) : content;
        inventory.setItem(slot, content.clone());
    }

    public boolean hasContent() {
        return !inventory.getItem(slot).getType().isAir();
    }

    public static Slot empty(@NotNull final Inventory inventory, final int slot) {
        return new Slot(inventory, slot, ClickEvent.EMPTY, new ItemStack(Material.AIR));
    }

    public static Slot[] createEmptyArray(@NotNull final Inventory inventory) {
        Slot[] slots = new Slot[inventory.getSize()];
        for (int i = 0; i < inventory.getSize(); i++) {
            slots[i] = new Slot(inventory, i, ClickEvent.EMPTY, new ItemStack(Material.AIR));
        }
        return slots;
    }

}
