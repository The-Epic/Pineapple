package sh.miles.pineapple.nms.impl.v1_20_R3.inventory.scene.custom;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.custom.CustomMenuContext;
import sh.miles.pineapple.nms.api.menu.scene.custom.CustomMenuSlot;
import sh.miles.pineapple.nms.api.menu.scene.custom.CustomSlotListener;

public class PineappleCustomSlot extends Slot implements CustomMenuSlot {

    private final CustomMenuContext context;
    private final CustomSlotListener listener;

    public PineappleCustomSlot(@NotNull final CustomMenuContext context, @NotNull final CustomSlotListener listener, final Container container, final int slotIndex, final int screenX, final int screenY) {
        super(container, slotIndex, screenX, screenY);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void checkTakeAchievements(final ItemStack item) {
        // Mirrored
        this.listener.onCheckAchievements(this.context, this, mirror(item));
    }

    @Override
    public void onTake(final Player player, final ItemStack item) {
        if (this.listener.onTakeItem(this.context, this, player.getBukkitEntity(), mirror(item))) {
            super.onTake(player, item);
        }
    }

    @Override
    public boolean mayPlace(final ItemStack item) {
        // Mirrored
        return this.listener.dictateMayPlaceItem(this.context, this, mirror(item));
    }

    @Override
    public void setByPlayer(final ItemStack item) {
        if (this.listener.onSetItemByPlayer(this.context, this, mirror(item))) {
            super.setByPlayer(item);
        }
    }

    @Override
    public void set(final ItemStack item) {
        if (this.listener.onSetItem(this.context, this, mirror(item))) {
            super.set(item);
        }
    }

    @Override
    public boolean mayPickup(final Player player) {
        // Mirrored
        return this.listener.dictateMayPickupItem(this.context, this, player.getBukkitEntity());
    }

    @Override
    public boolean allowModification(final Player player) {
        // Mirrored
        return this.listener.dictateAllowSlotModification(this.context, this, player.getBukkitEntity());
    }

    // Pineapple Required Start

    @Override
    public void onQuickCraftItem(@NotNull final org.bukkit.inventory.ItemStack originalStack, @NotNull final org.bukkit.inventory.ItemStack newStack) {
        onQuickCraft(convert(originalStack), convert(newStack));
    }

    @Override
    public void checkForAchievements(@NotNull final org.bukkit.inventory.ItemStack item) {
        // Mirror
        this.listener.onCheckAchievements(this.context, this, item);
    }

    @Override
    public void onTakeItem(@NotNull final HumanEntity player, @NotNull final org.bukkit.inventory.ItemStack item) {
        this.onTake(((CraftHumanEntity) player).getHandle(), convert(item));
    }

    @Override
    public boolean mayPlaceItem(@NotNull final org.bukkit.inventory.ItemStack item) {
        // mirror
        return this.listener.dictateMayPlaceItem(this.context, this, item);
    }

    @Override
    public void setItemByPlayer(@NotNull final org.bukkit.inventory.ItemStack item) {
        this.setByPlayer(convert(item));
    }

    @Override
    public void setItem(@NotNull final org.bukkit.inventory.ItemStack item) {
        this.set(convert(item));
    }

    @Override
    public boolean mayPickupItem(@NotNull final HumanEntity player) {
        // Mirror
        return this.listener.dictateMayPickupItem(this.context, this, player);
    }

    @Override
    public boolean allowSlotModification(@NotNull final HumanEntity player) {
        // Mirrored
        return this.listener.dictateAllowSlotModification(this.context, this, player);
    }

    @NotNull
    @Override
    public org.bukkit.inventory.ItemStack getBukkitItem() {
        return mirror(getItem());
    }

    private static CraftItemStack mirror(ItemStack itemStack) {
        return CraftItemStack.asCraftMirror(itemStack);
    }

    private static ItemStack convert(@NotNull final org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack);
    }

}
