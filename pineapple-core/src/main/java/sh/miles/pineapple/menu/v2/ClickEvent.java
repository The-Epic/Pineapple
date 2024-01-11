package sh.miles.pineapple.menu.v2;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface ClickEvent {
    ClickEvent EMPTY = (viewer, event) -> {};
    ClickEvent CLICKABLE = (viewer, event) -> {};


    void click(@NotNull final Player viewer, @NotNull final InventoryClickEvent event);
}
