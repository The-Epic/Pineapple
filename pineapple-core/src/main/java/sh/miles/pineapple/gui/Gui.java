package sh.miles.pineapple.gui;

import org.bukkit.entity.Player;

/**
 * Represents a visible, item holding, display to the player
 *
 * @since 1.0.0-SNAPSHOT
 */
public interface Gui {

    /**
     * Opens the GUI interaction screen
     *
     * @since 1.0.0-SNAPSHOT
     */
    void open();

    /**
     * Closes the GUI interaction screen
     *
     * @since 1.0.0-SNAPSHOT
     */
    void close();

    /**
     * Gets the viewer of this Gui
     *
     * @return the viewer
     * @since 1.0.0-SNAPSHOT
     */
    Player viewer();
}
