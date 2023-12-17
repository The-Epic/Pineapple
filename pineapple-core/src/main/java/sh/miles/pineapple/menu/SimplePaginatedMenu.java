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
import sh.miles.nms.api.menu.scene.MenuScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class SimplePaginatedMenu<T extends MenuScene> implements Menu {

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

    protected void nextPage(boolean wrap) {
        if (this.currentPageIndex + 1 > pages.size()) {
            if (!wrap) {
                throw new IllegalStateException("Can not navigate to next page if one does not exist");
            }
            currentPageIndex = 0;
            return;
        }

        currentPageIndex += 1;
    }

    protected void previousPage(boolean wrap) {
        if (this.currentPageIndex == 0) {
            if (!wrap) {
                throw new IllegalStateException("Can not navigate to previous page if you are one one exist");
            }
            currentPageIndex = this.pages.size() - 1;
            return;
        }

        currentPageIndex -= 1;
    }

    protected void setItem(int index, MenuItem item, int page) {
        Preconditions.checkArgument(index > 0 && index < inventory.getSize(), "The given index must be within the bounds of the inventories size, %d", inventory.getSize());
        Preconditions.checkArgument(page >= 0 && page < pages.size(), "The given page index must be within the bounds of the pages size, %d", pages.size());
        this.pages.get(page)[index] = item;
    }

    protected void addPage() {
        this.pages.add(new MenuItem[this.inventory.getSize()]);
    }

    @NotNull
    @Override
    public Optional<MenuItem> getItem(final int slot) {
        return Optional.ofNullable(this.currentPage.get(slot));
    }

    @NotNull
    @Override
    public T getScene() {
        return this.scene;
    }

    @Override
    public void open() {
        updatePage();
        viewer.openInventory(inventory);
    }

    @Override
    public void close() {
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
