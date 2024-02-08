package sh.miles.pineapple.nms.impl.v1_20_R3.inventory.scene.custom;

import com.google.common.base.Preconditions;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.custom.CustomMenuContext;
import sh.miles.pineapple.nms.api.menu.scene.custom.CustomMenuListener;
import sh.miles.pineapple.nms.api.menu.scene.custom.CustomMenuSlot;

import javax.annotation.Nullable;

public class PineappleCustomMenu extends AbstractContainerMenu implements CustomMenuContext {
    // CraftBukkit Required Start
    private InventoryView bukkitView; // cache view
    private final Inventory playerInventory; // used to obtain player instance
    // CraftBukkit Required End

    // Pineapple Required Start
    private final CraftInventory craftInventory; // used for custom slot implementation
    private final CustomMenuListener menuListener; // used for custom behaviors implementation
    // Pineapple Required End

    private final Container container;
    private final int rows;

    public PineappleCustomMenu(@NotNull CustomMenuListener menuListener, @Nullable final MenuType<?> containers, @NotNull final Container container, @NotNull final Inventory playerInventory, final int syncId) {
        super(containers, syncId);
        Preconditions.checkArgument(container.getContainerSize() % 9 == 0 && container.getContainerSize() / 9 < 7, "The given container is not within the bounds to conform a slot amount of %d is invalid".formatted(container.getContainerSize()));
        this.container = container;
        this.rows = this.container.getContainerSize() / 9;

        // CraftBukkit Required Start
        this.playerInventory = playerInventory;
        // CraftBukkit Required End

        // Pineapple Required Start
        this.craftInventory = new CraftInventory(container);
        this.menuListener = menuListener;
        // Pineapple Required End

        final int offset = (rows - 4) * 18;

        // Create Slots For Chest
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < 9; column++) {
                final int index = column + row * 9;
                this.addSlot(new PineappleCustomSlot(this, this.menuListener.getSlotListener(index), this.container, index, 8 + column * 18, 18 + row * 18));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                final int index = column + row * 9 + 9;
                this.addSlot(new Slot(this.container, index, 8 + column * 18, 103 + row * 18 + offset));
            }
        }

        for (int row = 0; row < 9; ++row) {
            this.addSlot(new Slot(this.container, row, 8 + row * 18, 161 + offset));
        }
    }

    @Override
    public ItemStack quickMoveStack(final Player player, final int rawSlot) {
        final var result = menuListener.quickMoveItem(this, player.getBukkitEntity(), rawSlot);
        if (result.shouldDelegateToDefault()) {
            System.out.println("delegated");
            return quickMoveStackChest(player, rawSlot);
        }

        if (result.shouldCancel()) {
            System.out.println("canceled");
            return ItemStack.EMPTY;
        }

        System.out.println("finished");
        return CraftItemStack.asNMSCopy(result.item());
    }

    private ItemStack quickMoveStackChest(final Player player, final int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            itemStack = slotItem.copy();
            if (i < this.rows * 9) {
                if (!this.moveItemStackTo(slotItem, this.rows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(final Player player) {
        return this.menuListener.dictateValidity(this, player.getBukkitEntity());
    }

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    // CraftBukkit Required Start
    @Override
    public InventoryView getBukkitView() {
        if (this.bukkitView == null) {
            bukkitView = new CraftInventoryView(playerInventory.player.getBukkitEntity(), this.craftInventory, this);
        }

        return bukkitView;
    }
    // CraftBukkit Required End

    // Pineapple Required Start
    @Override
    public boolean mergeItemStackBetween(@NotNull final org.bukkit.inventory.ItemStack item, final int startIndex, final int endIndex, final boolean reverse) {
        return super.moveItemStackTo(CraftItemStack.asNMSCopy(item), startIndex, endIndex, reverse);
    }

    @Override
    public void slotsChanged() {
        super.slotsChanged(this.container);
    }

    @NotNull
    @Override
    public CustomMenuSlot getMenuSlot(final int rawSlot) {
        return (CustomMenuSlot) this.slots.get(rawSlot);
    }

    @NotNull
    @Override
    public org.bukkit.inventory.Inventory getBukkitContainer() {
        return this.craftInventory;
    }

    @Override
    public int getRowAmount() {
        return this.rows;
    }

    @Override
    public int getSlotAmount() {
        return this.slots.size();
    }
    // Pineapple Required End


}
