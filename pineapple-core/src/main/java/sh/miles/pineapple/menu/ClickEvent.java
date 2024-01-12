package sh.miles.pineapple.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.util.InventoryUtils;

public interface ClickEvent {

    ClickEvent EMPTY = (viewer, event) -> {};
    ClickEvent CLICKABLE = (viewer, event) -> event.setCancelled(false);
    ClickEvent PICKUP_ONLY = (viewer, event) -> event.setCancelled(!InventoryUtils.isPickupAction(event.getAction()));

    void click(@NotNull final Player viewer, @NotNull final InventoryClickEvent event);
}
