package sh.miles.pineapple.menu.slot;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.menu.ClickEvent;

/**
 * A basic outline for what a Slot implementation should be capable of
 *
 * @since 1.0.0
 */
public interface Slot {

    /**
     * Calls logic that usually proceeds an inventory click
     *
     * @param viewer the viewer
     * @param event  the click event
     * @since 1.0.0
     */
    void click(@NotNull final Player viewer, @NotNull final InventoryClickEvent event);

    /**
     * Pushes the held content within the slot forcefully to it's owning inventory
     *
     * @since 1.0.0
     */
    void pushContent();

    /**
     * Checks whether or not this slot is set to autoSync with its owning inventory.
     *
     * @return true if this slot should auto sync, otherwise false
     * @since 1.0.0
     */
    boolean isAutoSync();

    /**
     * Checks whether or not this slot has content. A slot is considered to have no content if its containing stack is
     * air
     *
     * @return true if the slot has content, otherwise false
     * @since 1.0.0
     */
    boolean hasContent();

    /**
     * Gets the content of this slot. An Empty item stack indicates that the slot is currently empty
     *
     * @return the item stack content
     * @since 1.0.0
     */
    @NotNull
    ItemStack getContent();

    /**
     * Gets the owning inventory of this slot
     *
     * @return the owning inventory
     * @since 1.0.0
     */
    @NotNull
    Inventory getInventory();

    /**
     * Gets the index of this slot on the owning inventory
     *
     * @return the slot index
     * @since 1.0.0
     */
    int getSlotIndex();

    /**
     * Sets the event on this slot
     *
     * @param event the event
     * @since 1.0.0
     */
    void setEvent(@Nullable ClickEvent event);

    /**
     * Sets the content on this slot
     *
     * @param content the content
     * @since 1.0.0
     */
    void setContent(@Nullable ItemStack content);

    /**
     * Sets the details of this slot
     *
     * @param content the content
     * @param event   the click event
     */
    void setDetail(@Nullable ItemStack content, @Nullable ClickEvent event);

    /**
     * Sets whether or not this slot should auto sync.
     *
     * @param autoSync true to auto sync, otherwise false
     * @since 1.0.0
     */
    void setAutoSync(final boolean autoSync);
}
