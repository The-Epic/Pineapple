package sh.miles.pineapple.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Listener implementation medium for Bukkit
 * <p>
 * This class should not be registered or implemented by plugins. If you need to reflectively access this listener you
 * ARE doing something wrong.
 *
 * @since 1.0.0-SNAPSHOT
 */
@ApiStatus.Internal
class MenuListener implements Listener {

    private final MenuManager manager;

    public MenuListener(MenuManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onClick(@NotNull final InventoryClickEvent event) {
        manager.getMenu(event.getInventory()).ifPresent(menu -> menu.handleClick(event));
    }

    @EventHandler
    public void onOpen(@NotNull final InventoryOpenEvent event) {
        manager.getMenu(event.getInventory()).ifPresent(menu -> menu.handleOpen(event));
    }

    @EventHandler
    public void onClose(@NotNull final InventoryCloseEvent event) {
        manager.getMenu(event.getInventory()).ifPresent(menu -> menu.handleClose(event));
    }

}
