package sh.miles.pineapple.nms.impl.v1_20_R3.inventory.scene.custom.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.custom.extendable.slot.SlotBehavior;

public class PineappleSlotCustom extends Slot {

    private final CraftInventory inventory;
    private final SlotBehavior behavior;

    public PineappleSlotCustom(final CraftInventory inventory, final int index, @NotNull final SlotBehavior behavior, final int screenX, final int screenY) {
        super(inventory.getInventory(), index, screenX, screenY);
        this.inventory = inventory;
        this.behavior = behavior;
    }

    @Override
    protected void onQuickCraft(final ItemStack item, final int amount) {
        behavior.onQuickCraft(this.inventory, CraftItemStack.asCraftMirror(item), amount);
    }

    @Override
    protected void checkTakeAchievements(final ItemStack item) {
        behavior.checkAchievements(CraftItemStack.asCraftMirror(item));
    }

    @Override
    public void onTake(final Player player, final ItemStack item) {
        final CraftItemStack craftStack = CraftItemStack.asCraftMirror(item);
        if (behavior.onTake(player.getBukkitEntity(), craftStack)) {
            super.onTake(player, CraftItemStack.asNMSCopy(craftStack));
        }
    }

    @Override
    public boolean mayPlace(final ItemStack item) {
        return behavior.mayPlace(CraftItemStack.asCraftMirror(item));
    }

    @Override
    public void set(final ItemStack item) {
        final CraftItemStack craftStack = CraftItemStack.asCraftMirror(item);
        if (behavior.onSet(this.inventory, craftStack)) {
            super.set(CraftItemStack.asNMSCopy(craftStack));
        }
    }

    @Override
    public boolean mayPickup(final Player player) {
        return behavior.mayPickup(CraftItemStack.asCraftMirror(getItem()), player.getBukkitEntity());
    }

    @Override
    public boolean allowModification(final Player player) {
        return behavior.mayModify(CraftItemStack.asCraftMirror(getItem()), player.getBukkitEntity());
    }

}
