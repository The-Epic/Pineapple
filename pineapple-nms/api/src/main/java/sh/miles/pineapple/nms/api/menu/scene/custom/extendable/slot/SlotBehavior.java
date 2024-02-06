package sh.miles.pineapple.nms.api.menu.scene.custom.extendable.slot;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Determines behavior for a slot
 */
@ApiStatus.OverrideOnly
public interface SlotBehavior {

    SlotBehavior NONE = new SlotBehavior() {};

    /**
     * Handles Delegated QuickCraft behavior
     *
     * @param inventory the inventory this slot is in
     * @param item      the item to be quick crafted
     * @param amount    the amount being quick crafted
     */
    default void onQuickCraft(@NotNull final Inventory inventory, @NotNull final ItemStack item, final int amount) {
    }

    /**
     * Handles achievement checks if any need to be rewarded at this slot
     *
     * @param item the item to check
     */
    default void checkAchievements(@NotNull final ItemStack item) {
    }

    /**
     * Determines an event to occur when a player takes an item
     *
     * @param player the player to take the item
     * @param item   the item taken
     * @return true if the slot should be changed, otherwise false. By default this method returns true
     */
    default boolean onTake(@NotNull final HumanEntity player, @NotNull final ItemStack item) {
        return true;
    }

    /**
     * Determines whether or not an item may be placed in this slot
     *
     * @param item the item to place
     * @return true if the item can be placed, otherwise false. By default this method returns true
     */
    default boolean mayPlace(@NotNull final ItemStack item) {
        return true;
    }

    /**
     * The action that occurs when an item is set into the slot by a player.
     *
     * @param inventory the inventory
     * @param item      the item to be set
     * @return true if the slot should be changed, otherwise false. By default this method mirrors
     * {@link #onSet(Inventory, ItemStack)}
     */
    default boolean onSetByPlayer(@NotNull final Inventory inventory, ItemStack item) {
        return onSet(inventory, item);
    }

    /**
     * The actions that occur when an item is set into the slot. This includes by plugins
     *
     * @param inventory the inventory
     * @param item      the item to be set
     * @return true if the slot should be changed, otherwise false. By default this method returns true
     */
    default boolean onSet(@NotNull final Inventory inventory, ItemStack item) {
        return true;
    }

    /**
     * An action that occurs when the slot is changed
     *
     * @param inventory the inventory the change occurs in
     */
    default void onChanged(@NotNull final Inventory inventory) {
    }

    /**
     * An action to confirm whether or not picking up an item from this slot should be permitted
     *
     * @param item   the item currently in the slot
     * @param player the player to pick up from the slot
     * @return true if the player can pickup the item, otherwise false. By default this method returns true
     */
    default boolean mayPickup(@NotNull final ItemStack item, @NotNull final HumanEntity player) {
        return true;
    }

    /**
     * Determines whether or not this slot may be modified
     *
     * @param item   the item
     * @param player the player
     * @return true if the slot may be modified, otherwise false. By default this method returns
     * {@link #mayPickup(ItemStack, HumanEntity)} && {@link #mayPlace(ItemStack)}
     */
    default boolean mayModify(@NotNull final ItemStack item, @NotNull final HumanEntity player) {
        return this.mayPickup(item, player) && this.mayPlace(item);
    }

}
