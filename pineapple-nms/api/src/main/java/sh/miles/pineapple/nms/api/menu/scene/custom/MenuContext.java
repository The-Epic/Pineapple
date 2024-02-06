package sh.miles.pineapple.nms.api.menu.scene.custom;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.custom.extendable.slot.SlotBehavior;

/**
 * Gives Context to a menu for certain operations
 */
public interface MenuContext {

    /**
     * Sends Changed behavior
     *
     * @param rawSlot the slot to set changed
     */
    void setChanged(final int rawSlot);

    /**
     * Moves an item stack from rawSlot0 to rawSlot1
     *
     * @param item     the item to move
     * @param rawSlot0 the first raw slot
     * @param rawSlot1 the second raw slot
     * @param flag     some flag
     * @return whether the move was successful
     */
    boolean moveItemStackTo(ItemStack item, int rawSlot0, int rawSlot1, boolean flag);

    /**
     * Gets the possible behavior of a raw slot
     * <p>
     * Implementations of this method MUST NEVER RETURN NULL
     *
     * @param rawSlot the raw slot
     * @return possible slot behavior
     */
    @NotNull
    SlotBehavior getBehavior(int rawSlot);
}
