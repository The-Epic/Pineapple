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
 * @since 1.0.0-SNAPSHOT
 */
public record MenuItem(@NotNull Function<Player, ItemStack> item, BiConsumer<Player, InventoryClickEvent> click) {

    public static BiConsumer<Player, InventoryClickEvent> EMPTY = (p, e) -> {
    };

    public ItemStack item(@NotNull final Player player) {
        return item.apply(player);
    }

    /**
     * Runs the click event for the MenuItem
     *
     * @param player the player of the event
     * @param event  the event
     * @since 1.0.0-SNAPSHOT
     */
    public void click(@NotNull final Player player, @NotNull final InventoryClickEvent event) {
        click.accept(player, event);
    }

    @NotNull
    public static MenuItem adapt(@NotNull final Function<Player, ItemStack> item) {
        return new MenuItem(item, EMPTY);
    }

    /**
     * Builder for the MenuItem
     *
     * @return the newly created MenuItem builder
     * @since 1.0.0-SNAPSHOT
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Function<Player, ItemStack> item;
        private BiConsumer<Player, InventoryClickEvent> click = EMPTY;

        /**
         * Sets the item of this MenuItem Builder
         *
         * @param item the item
         * @return the builder
         * @since 1.0.0-SNAPSHOT
         */
        @NotNull
        public Builder item(@NotNull final Function<Player, ItemStack> item) {
            this.item = item;
            return this;
        }

        /**
         * Sets the click event of this MenuItem Builder
         *
         * @param click the click event
         * @return the builder
         * @since 1.0.0-SNAPSHOT
         */
        @NotNull
        public Builder click(@NotNull final BiConsumer<Player, InventoryClickEvent> click) {
            this.click = click;
            return this;
        }

        /**
         * Builds the current MenuItem Builder into a MenuItem
         *
         * @return the MenuItem created from the builder
         * @throws IllegalArgumentException throws if the item or click event are null
         * @since 1.0.0-SNAPSHOT
         */
        public MenuItem build() throws IllegalArgumentException {
            Preconditions.checkArgument(item != null, "The MenuItem item can not be null");
            Preconditions.checkArgument(click != null, "The MenuItem click event can not be null");
            return new MenuItem(item, click);
        }
    }

}
