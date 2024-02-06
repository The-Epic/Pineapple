package sh.miles.pineapple.nms.impl.v1_20_R3.inventory.scene.custom;

import com.google.common.base.Preconditions;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.custom.extendable.MenuBehavior;
import sh.miles.pineapple.nms.api.menu.scene.custom.extendable.slot.SlotBehavior;
import sh.miles.pineapple.nms.impl.v1_20_R3.inventory.scene.custom.slot.PineappleSlotCustom;

public class PineappleCustomMenu extends AbstractContainerMenu implements sh.miles.pineapple.nms.api.menu.scene.custom.MenuContext {

    // CraftBukkit Required Start
    private InventoryView bukkitView; // cache view
    private final Inventory playerInventory; // used to obtain player instance
    // CraftBukkit Required End

    // Pineapple Required Start
    private final CraftInventory craftInventory; // used for custom slot implementation
    private final MenuBehavior sceneBehavior; // used for custom behaviors implementation
    // Pineapple Required End

    private final Container container;
    private final int rows;

    public PineappleCustomMenu(@NotNull final MenuBehavior behavior, @NotNull final MenuType<?> type, @NotNull final Container container, @NotNull final Inventory playerInventory, final int syncId) {
        super(type, syncId);
        Preconditions.checkArgument(container.getContainerSize() % 9 == 0 && container.getContainerSize() / 9 < 7, "The given container is not within the bounds to conform a slot amount of %d is invalid".formatted(container.getContainerSize()));
        this.container = container;
        this.craftInventory = new CraftInventory(container);
        this.playerInventory = playerInventory;
        this.rows = this.container.getContainerSize() / 9;
        this.sceneBehavior = behavior;
        container.startOpen(playerInventory.player);

        final int offset = (rows - 4) * 18;
        // Create Slots For Chest
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < 9; column++) {
                final int index = column + row * 9;
                this.addSlot(new PineappleSlotCustom(this.craftInventory, index, this.sceneBehavior.getSlotBehavior(index), 8 + column * 18, 18 + row * 18));
            }
        }

        // Create Slots For Player Inventory
        final CraftInventoryPlayer craftInventoryPlayer = (CraftInventoryPlayer) this.playerInventory.player.getBukkitEntity().getInventory();
        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                final int index = column + row * 9 + 9;
                this.addSlot(new PineappleSlotCustom(craftInventoryPlayer, index, this.sceneBehavior.getSlotBehavior(index), 8 +column * 18, 103 + row * 18 + offset));
            }
        }

        for(int row = 0; row < 9; ++row) {
            this.addSlot(new PineappleSlotCustom(craftInventoryPlayer, row, this.sceneBehavior.getSlotBehavior(row), 8 + row * 18, 161 + offset));
        }
    }

    public PineappleCustomMenu(@NotNull final MenuBehavior behavior, @NotNull final MenuType<?> type, @NotNull final Inventory playerInventory, final int rows, final int syncId) {
        this(behavior, type, new SimpleContainer(rows * 9), playerInventory, syncId);
    }

    @Override
    public InventoryView getBukkitView() {
        if (this.bukkitView == null) {
            bukkitView = new CraftInventoryView(playerInventory.player.getBukkitEntity(), this.craftInventory, this);
        }

        return bukkitView;
    }

    @Override
    public ItemStack quickMoveStack(final Player player, final int i) {
        MenuBehavior.QuickMoveResult result = this.sceneBehavior.quickMoveStack(this, player.getBukkitEntity(), CraftItemStack.asCraftMirror(slots.get(i).getItem()), i);
        if (result.delegate()) {
            return quickMoveStackChest(player, i);
        }
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
        return this.sceneBehavior.isSessionValid(this, player.getBukkitEntity());
    }

    @Override
    public void setChanged(final int rawSlot) {
        slots.get(rawSlot).setChanged();

    }

    @Override
    public boolean moveItemStackTo(final org.bukkit.inventory.ItemStack item, final int rawSlot0, final int rawSlot1, final boolean flag) {
        return super.moveItemStackTo(CraftItemStack.asNMSCopy(item), rawSlot0, rawSlot0, flag);
    }

    @Override
    public @NotNull SlotBehavior getBehavior(final int rawSlot) {
        return this.sceneBehavior.getSlotBehavior(rawSlot);
    }
}
