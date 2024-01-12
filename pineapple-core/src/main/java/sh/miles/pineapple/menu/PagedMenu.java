package sh.miles.pineapple.menu;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.PineappleLib;
import sh.miles.pineapple.menu.slot.BasicSlot;
import sh.miles.pineapple.menu.slot.Slot;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Creates a menu that can have multiple pages
 *
 * @param <T> the type of MenuScene
 * @since 1.0.0-SNAPSHOT
 */
public abstract class PagedMenu<T extends MenuScene> implements Menu<T> {

    protected static final int NO_PAGE_SELECTED = -1;

    private final T scene;
    private final Player viewer;
    private final Inventory inventory;
    private final List<Slot[]> pages;

    private int maxPage = 0;
    private int currentPageIndex = NO_PAGE_SELECTED;

    /**
     * Creates a new PagedMenu
     *
     * @param function the function used to create a MenuScene to be displayed to the viewer
     * @param viewer   the viewer of this Menu
     * @since 1.0.0-SNAPSHOT
     */
    protected PagedMenu(@NotNull final Function<Player, T> function, @NotNull final Player viewer) {
        this.scene = function.apply(viewer);
        this.viewer = viewer;
        this.inventory = scene.getBukkitView().getTopInventory();
        this.pages = new ArrayList<>();
        init();
    }

    @Override
    public void open() {
        PineappleLib.getMenuManager().register(this);
        this.viewer.openInventory(inventory);
    }

    @Override
    public void close() {
        this.viewer.closeInventory();
        PineappleLib.getMenuManager().unregister(this.inventory);
    }

    @NotNull
    @Override
    public T getScene() {
        return scene;
    }

    /**
     * Attempts to navigate to the next page
     *
     * @param wrap whether or not to wrap to the first if the max page has been hit
     */
    protected void nextPage(boolean wrap) {
        Preconditions.checkArgument(maxPage != 0 && (wrap || (this.currentPageIndex + 1 < this.maxPage)), "The next page must either wrap or the current page must be less than the maximum page");
        final int oldPage = this.currentPageIndex;
        this.currentPageIndex = currentPageIndex + 1;
        if (this.currentPageIndex >= maxPage) {
            this.currentPageIndex = 0;
        }

        if (oldPage == currentPageIndex) {
            return;
        }

        handleSlotChanges(oldPage, this.currentPageIndex);
    }

    /**
     * Attempts to navigate to the previous page
     *
     * @param wrap whether or not to wrap to the first if the max page has been hit
     */
    protected void previousPage(boolean wrap) {
        Preconditions.checkArgument(maxPage != 0 && (wrap || (this.currentPageIndex - 1 > -1)), "The next page must either wrap or the current page must be less than the maximum page");
        final int oldPage = this.currentPageIndex;
        this.currentPageIndex = currentPageIndex - 1;
        if (this.currentPageIndex < 0) {
            this.currentPageIndex = 0;
        }

        if (oldPage == currentPageIndex) {
            return;
        }

        handleSlotChanges(oldPage, this.currentPageIndex);
    }

    private void handleSlotChanges(int oldPage, int newPage) {
        Slot[] oldSlots = pages.get(oldPage);
        for (final Slot slot : oldSlots) {
            slot.setAutoSync(false);
        }

        Slot[] newSlots = pages.get(newPage);
        for (final Slot newSlot : newSlots) {
            newSlot.setAutoSync(true);
            newSlot.pushContent();
        }
    }

    /**
     * Adds a new page
     */
    protected void addPage() {
        pages.add(BasicSlot.createMapping(this.inventory));
        this.maxPage += 1;
        if (this.currentPageIndex == NO_PAGE_SELECTED) {
            this.currentPageIndex = this.maxPage - 1;
        }
    }

    /**
     * Sets the slot at the specified index on the specified pages
     *
     * @param index        the index
     * @param slotSupplier the function used to build the slot
     * @param pages        the pages to set the slot on
     * @since 1.0.0-SNAPSHOT
     */
    protected void setSlotAt(final int index, Supplier<Slot> slotSupplier, int... pages) {
        final Slot slot = slotSupplier.get();
        for (final int page : pages) {
            Slot[] slots = this.pages.get(page);
            slots[index] = slot;
        }
    }

    /**
     * Sets the event at the specified index on the specified page
     *
     * @param slot  the slot to set the event at
     * @param event the event to set
     * @param page  the page to set the event on
     * @since 1.0.0-SNAPSHOT
     */
    protected void setEventAt(final int slot, @Nullable final ClickEvent event, int page) {
        Preconditions.checkArgument(slot < inventory.getSize() && slot > -1, "The given slot must be within the inventory size range");
        Preconditions.checkArgument(page < pages.size() && page > -1);
        this.pages.get(page)[slot].setEvent(event);
    }

    /**
     * Sets the content at the specified index on the specified page
     *
     * @param slot    the slot to set the content at
     * @param content the content to set
     * @param page    the page to set the content on
     * @since 1.0.0-SNAPSHOT
     */
    protected void setContentAt(final int slot, @Nullable ItemStack content, int page) {
        Preconditions.checkArgument(slot < inventory.getSize() && slot > -1, "The given slot must be within the inventory size range");
        Preconditions.checkArgument(page < pages.size() && page > -1);
        this.pages.get(page)[slot].setContent(content);
    }

    /**
     * Sets the content at the specified index on the specified page, only if that slot has no content yet
     *
     * @param slot    the slot to set the content at
     * @param content the content to set
     * @param page    the page to set the content on
     * @since 1.0.0-SNAPSHOT
     */
    protected void setContentAtIfAbsent(final int slot, @Nullable ItemStack content, int page) {
        Preconditions.checkArgument(slot < inventory.getSize() && slot > -1, "The given slot must be within the inventory size range");
        Preconditions.checkArgument(page < pages.size() && page > -1);

        Slot target = this.pages.get(page)[slot];
        if (!target.hasContent()) {
            target.setContent(content);
        }
    }

    /**
     * Sets all details of a slot at the specified index on the specified page
     *
     * @param slot    the slot to set the details of
     * @param content the content of the slot
     * @param event   the event the slot will execute when clicked
     * @param page    the page to set the slot details on
     * @since 1.0.0-SNAPSHOT
     */
    protected void setSlotDetail(final int slot, @Nullable ItemStack content, @Nullable ClickEvent event, int page) {
        Preconditions.checkArgument(slot < inventory.getSize() && slot > -1, "The given slot must be within the inventory size range");
        Preconditions.checkArgument(page < pages.size() && page > -1);
        this.pages.get(page)[slot].setEvent(event);
        this.pages.get(page)[slot].setContent(content);
    }

    @Override
    public void handleClick(@NotNull final InventoryClickEvent event) {
        event.setCancelled(true);
        final int index = event.getSlot();
        this.pages.get(this.currentPageIndex)[index].click((Player) event.getWhoClicked(), event);
    }

    @Override
    public void handleOpen(@NotNull final InventoryOpenEvent event) {
    }

    @Override
    public void handleClose(@NotNull final InventoryCloseEvent event) {
    }
}
