package sh.miles.pineapple.menu.v2.slot;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.menu.v2.ClickEvent;

import java.util.List;

public class SyncedSlot extends Slot {

    private final List<Inventory> syncedInventories;
    private ItemStack lastContent = new ItemStack(Material.AIR);

    public SyncedSlot(@NotNull final List<Inventory> syncedInventories, final int syncedSlot, @NotNull final ClickEvent event, @NotNull final ItemStack content) {
        super(syncedInventories.get(0), syncedSlot, event, content);
        this.syncedInventories = syncedInventories;
    }

    public void syncWithAll() {
        setSyncedContent(this.lastContent);
    }

    public void setSyncedContent(@Nullable final ItemStack content) {
        this.lastContent = content == null ? new ItemStack(Material.AIR) : content;

        for (Inventory syncedInventory : syncedInventories) {
            syncedInventory.setItem(this.slot, this.lastContent);
        }
    }

    public void addSyncedInventory(@NotNull final Inventory inventory) {
        this.syncedInventories.remove(inventory);
    }

    public void removeSyncedInventory(@NotNull final Inventory inventory) {
        this.syncedInventories.remove(inventory);
    }

    @NotNull
    public List<Inventory> getSyncedInventories() {
        return syncedInventories;
    }
}
