package sh.miles.pineapple.menu.v2;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.util.InventoryUtils;

import java.util.List;

public interface ClickEvent {

    ClickEvent EMPTY = (viewer, event) -> {};
    ClickEvent CLICKABLE = (viewer, event) -> event.setCancelled(false);
    ClickEvent PICKUP_ONLY = (viewer, event) -> event.setCancelled(!InventoryUtils.isPickupAction(event.getAction()));

    void click(@NotNull final Player viewer, @NotNull final InventoryClickEvent event);
}
