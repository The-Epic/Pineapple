package sh.miles.pineapple.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import sh.miles.nms.api.menu.scene.MenuScene;

import java.util.Optional;

/**
 * Represents a simple outline for a menu.
 *
 * @param <T> the type of MenuScene
 */
public interface Menu<T extends MenuScene> {

    /**
     * Opens the menu for the viewer
     */
    void open();

    /**
     * Closes the menu for the viewer
     */
    void close();

    /**
     * Gets the item at the given slot if any
     *
     * @param slot the slot
     * @return the item wrapped in an optional
     */
    @NotNull
    Optional<MenuItem> getItem(int slot);

    /**
     * Gets the scene associated with this menu
     *
     * @return the menu scene
     */
    @NotNull
    T getScene();

    /**
     * Handles the click event
     *
     * @param event the click event
     */
    void handleClick(@NotNull final InventoryClickEvent event);

    /**
     * Handles the open event
     *
     * @param event the open event
     */
    void handleOpen(@NotNull final InventoryOpenEvent event);

    /**
     * Handles the close event
     *
     * @param event the close event
     */
    void handleClose(@NotNull final InventoryCloseEvent event);
}
