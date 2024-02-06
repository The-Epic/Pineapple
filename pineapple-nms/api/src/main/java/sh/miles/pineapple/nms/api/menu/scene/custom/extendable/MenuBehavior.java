package sh.miles.pineapple.nms.api.menu.scene.custom.extendable;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.custom.extendable.slot.SlotBehavior;

/**
 * Controls behavior that a Scene can have
 */
public interface MenuBehavior {

    /**
     * Handles quick move behavior. "Quick Move" entails shift clicking items or using num keys
     *
     * @param context the context of the scene
     * @param player  the player who executed the quick move
     * @param item    the item quick moved, which could be air
     * @param rawSlot the raw slot number
     * @return the {@link QuickMoveResult}
     */
    @NotNull
    default QuickMoveResult quickMoveStack(@NotNull final sh.miles.pineapple.nms.api.menu.scene.custom.MenuContext context, @NotNull final HumanEntity player, @NotNull final ItemStack item, final int rawSlot) {
        return new QuickMoveResult(item, true);
    }

    /**
     * Determines whether or not the given session is still valid. An Invalid Session is immediately closed
     *
     * @param context the scene context
     * @param player  the player
     * @return true if the session is valid, otherwise false. By default this method returns true, indicating a valid
     * session.
     */
    default boolean isSessionValid(@NotNull final sh.miles.pineapple.nms.api.menu.scene.custom.MenuContext context, @NotNull final HumanEntity player) {
        return true;
    }

    /**
     * Gets a behavior from the given raw slot
     *
     * @param rawSlot the behavior at the given raw slot
     * @return the behavior
     */
    SlotBehavior getSlotBehavior(final int rawSlot);

    /**
     * Tracks the result of a QuickMove
     *
     * @param item     the item
     * @param delegate whether to delegate to the default chest quick movement
     */
    record QuickMoveResult(@NotNull ItemStack item, boolean delegate) {
    }
}
