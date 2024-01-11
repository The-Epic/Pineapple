package sh.miles.pineapple.menu.v2;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.menu.MenuItem;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.Optional;

/**
 * Represents a simple outline for a menu.
 *
 * @param <T> the type of MenuScene
 * @since 1.0.0-SNAPSHOT
 */
public interface Menu<T extends MenuScene> {

    /**
     * Used to initialize and add items
     *
     * @since 1.0.0-SNAPSHOT
     */
    void init();

    /**
     * Opens the menu for the viewer
     *
     * @since 1.0.0-SNAPSHOT
     */
    void open();

    /**
     * Closes the menu for the viewer
     *
     * @since 1.0.0-SNAPSHOT
     */
    void close();

    /**
     * Gets the scene associated with this menu
     *
     * @return the menu scene
     * @since 1.0.0-SNAPSHOT
     */
    @NotNull
    T getScene();

    /**
     * Handles the click event
     *
     * @param event the click event
     * @since 1.0.0-SNAPSHOT
     */
    void handleClick(@NotNull final InventoryClickEvent event);

    /**
     * Handles the open event
     *
     * @param event the open event
     * @since 1.0.0-SNAPSHOT
     */
    void handleOpen(@NotNull final InventoryOpenEvent event);

    /**
     * Handles the close event
     *
     * @param event the close event
     * @since 1.0.0-SNAPSHOT
     */
    void handleClose(@NotNull final InventoryCloseEvent event);
}

