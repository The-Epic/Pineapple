package sh.miles.pineapple.menu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The MenuManager manages general menu registration.
 *
 * @since 1.0.0-SNAPSHOT
 */
public class MenuManager {

    private final Map<Inventory, Menu<?>> menus;

    public MenuManager(@NotNull final Plugin plugin) {
        this.menus = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), plugin);
    }

    /**
     * Registers the menu with the manager.
     *
     * @param menu the menu to register
     * @since 1.0.0-SNAPSHOT
     */
    public void register(@NotNull final Menu<?> menu) {
        menus.put(menu.getScene().getBukkitView().getTopInventory(), menu);
    }

    /**
     * Unregisters the menu with the manager.
     *
     * @param inventory the inventory to remove
     * @since 1.0.0-SNAPSHOT
     */
    public void unregister(@NotNull final Inventory inventory) {
        menus.remove(inventory);
    }

    /**
     * Gets a menu if it has been registered
     *
     * @param inventory the inventory to get the Menu of
     * @return the possible Menu
     * @since 1.0.0-SNAPSHOT
     */
    public Optional<Menu<?>> getMenu(@NotNull final Inventory inventory) {
        return Optional.ofNullable(menus.get(inventory));
    }


}
