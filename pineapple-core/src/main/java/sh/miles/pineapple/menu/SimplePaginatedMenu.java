package sh.miles.pineapple.menu;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A Simple paginated Menu Implementation to be extended by a plugin
 *
 * @param <T> the type of MenuScene
 * @since 1.0.0-SNAPSHOT
 */
public abstract class SimplePaginatedMenu<T extends MenuScene> implements Menu<T> {

    private final T scene;
    private final Player viewer;
    protected final Inventory inventory;
    private final List<MenuItem[]> pages;
    private final List<ItemStack> backdrop;
    private final Map<Integer, MenuItem> currentPage;
    private int currentPageIndex;

    protected SimplePaginatedMenu(@NotNull final Function<Player, T> function, @NotNull final Player viewer) {
        this.scene = function.apply(viewer);
        this.viewer = viewer;
        this.inventory = this.scene.getBukkitView().getTopInventory();
        this.pages = new ArrayList<>();
        this.backdrop = new ArrayList<>();
        this.currentPage = new HashMap<>();
        this.currentPageIndex = 0;

        addPage();
    }

    private void updatePage() {
        final MenuItem[] page = pages.get(this.currentPageIndex);
        for (int i = 0; i < page.length; i++) {
            final MenuItem item = page[i];
            if (item == null) {
                continue;
            }

            currentPage.put(i, item);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, backdrop.get(this.currentPageIndex));
                continue;
            }

            if (inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, backdrop.get(this.currentPageIndex));
            }
        }
    }

    /**
     * Changes the page to the next page
     *
     * @param wrap true if it should wrap back to 0 given the page is the max page.
     * @since 1.0.0-SNAPSHOT
     */
    protected void nextPage(boolean wrap) {
        if (this.currentPageIndex + 1 > pages.size()) {
            if (!wrap) {
                throw new IllegalStateException("Can not navigate to next page if one does not exist");
            }
            currentPageIndex = 0;
        } else {
            currentPageIndex += 1;
        }

        updatePage();
    }

    /**
     * Changes the page to the previous page
     *
     * @param wrap true if it should wrap back to max given the page is the 0th page.
     * @since 1.0.0-SNAPSHOT
     */
    protected void previousPage(boolean wrap) {
        if (this.currentPageIndex == 0) {
            if (!wrap) {
                throw new IllegalStateException("Can not navigate to previous page if you are one one exist");
            }
            currentPageIndex = this.pages.size() - 1;
        } else {
            currentPageIndex -= 1;
        }

        updatePage();
    }

    /**
     * Sets an item on the given page
     *
     * @param index the index in which the item should be set
     * @param item  the item to set
     * @param page  the page to set the item on
     * @since 1.0.0-SNAPSHOT
     */
    protected void setItem(final int index, @NotNull final MenuItem item, final int page) {
        Preconditions.checkArgument(index > 0 && index < inventory.getSize(), "The given index must be within the bounds of the inventories size, %d", inventory.getSize());
        Preconditions.checkArgument(page >= 0 && page < pages.size(), "The given page index must be within the bounds of the pages size, %d", pages.size());
        this.pages.get(page)[index] = item;
    }

    /**
     * Sets the backdrop of the given page
     *
     * @param item the item to set as the backdrop
     * @param page the page number
     * @since 1.0.0-SNAPSHOT
     */
    protected void setBackDrop(@NotNull final ItemStack item, final int page) {
        Preconditions.checkArgument(page >= 0 && page < pages.size(), "The given page index must be within the bounds of the pages size, %d", pages.size());
        this.backdrop.set(page, item);
    }

    /**
     * Adds a new page
     */
    protected void addPage() {
        this.pages.add(new MenuItem[this.inventory.getSize()]);
    }

    /**
     * Gets an item at the given slot of the current page
     *
     * @param slot the slot the slot
     * @return the optional MenuItem
     * @since 1.0.0-SNAPSHOT
     */
    @NotNull
    @Override
    public Optional<MenuItem> getItem(final int slot) {
        return Optional.ofNullable(this.currentPage.get(slot));
    }

    /**
     * Gets the current scene
     *
     * @return the scene
     */
    @NotNull
    @Override
    public T getScene() {
        return this.scene;
    }

    /**
     * Opens the page for the given player
     *
     * @since 1.0.0-SNAPSHOT
     */
    @Override
    public final void open() {
        updatePage();
        viewer.openInventory(inventory);
    }

    /**
     * Closes the Inventory for the given player
     *
     * @since 1.0.0-SNAPSHOT
     */
    @Override
    public final void close() {
        viewer.closeInventory();
    }

    @Override
    public void handleClick(@NotNull final InventoryClickEvent event) {
        event.setCancelled(true);
        final int slot = event.getSlot();
        getItem(slot).ifPresent(item -> item.click((Player) event.getWhoClicked(), event));
    }

    @Override
    public void handleOpen(@NotNull final InventoryOpenEvent event) {
    }

    @Override
    public void handleClose(@NotNull final InventoryCloseEvent event) {
    }
}
