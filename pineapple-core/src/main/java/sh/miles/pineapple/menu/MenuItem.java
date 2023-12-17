package sh.miles.pineapple.menu;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Items that can be interacted with inside of a menu
 *
 * @param item  the item
 * @param click the event to be triggered on click
 */
public record MenuItem(@NotNull Function<Player, ItemStack> item, BiConsumer<Player, InventoryClickEvent> click) {

    public ItemStack item(@NotNull final Player player) {
        return item.apply(player);
    }

    public void click(@NotNull final Player player, @NotNull final InventoryClickEvent event) {
        click.accept(player, event);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Function<Player, ItemStack> item;
        private BiConsumer<Player, InventoryClickEvent> click = (p, e) -> {
        };

        @NotNull
        public Builder item(@NotNull final Function<Player, ItemStack> item) {
            this.item = item;
            return this;
        }

        @NotNull
        public Builder click(@NotNull final BiConsumer<Player, InventoryClickEvent> click) {
            this.click = click;
            return this;
        }

        public MenuItem build() {
            Preconditions.checkArgument(item != null, "The MenuItem item can not be null");
            Preconditions.checkArgument(click != null, "The MenuItem click event can not be null");
            return new MenuItem(item, click);
        }
    }

}
