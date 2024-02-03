package sh.miles.pineapple.nms.impl.v1_20_R3;

import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.ReflectionUtils;
import sh.miles.pineapple.nms.api.PineappleNMS;
import sh.miles.pineapple.nms.api.PineappleUnsafe;
import sh.miles.pineapple.nms.impl.v1_20_R3.internal.ComponentUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

public class PineappleNMSImpl implements PineappleNMS {

    private static final MethodHandle itemStackHandle;
    private static final MethodHandle livingEntityLastDamageSourceHandle;

    static {
        itemStackHandle = ReflectionUtils.getFieldAsGetter(CraftItemStack.class, "handle");
        livingEntityLastDamageSourceHandle = ReflectionUtils.getFieldAsGetter(net.minecraft.world.entity.LivingEntity.class, "cd");
    }

    private final PineappleUnsafeImpl unsafe;

    public PineappleNMSImpl() {
        this.unsafe = new PineappleUnsafeImpl();
    }

    @Nullable
    @Override
    public InventoryView openInventory(@NotNull final Player player, @NotNull final Inventory inventory, @NotNull final BaseComponent title) {
        ServerPlayer nms = ((CraftPlayer) player).getHandle();
        // legacy method to be replaced
        net.minecraft.world.inventory.MenuType<?> mojType = CraftContainer.getNotchInventoryType(inventory);

        if (mojType == null) {
            throw new IllegalArgumentException("could not find menu type for inventory type of " + inventory.getType());
        }
        AbstractContainerMenu menu = new CraftContainer(inventory, nms, nms.nextContainerCounter());
        menu = CraftEventFactory.callInventoryOpenEvent(nms, menu);
        if (menu == null) {
            throw new IllegalStateException("Unable to open menu for unknown reason");
        }

        nms.connection.send(new ClientboundOpenScreenPacket(menu.containerId, mojType, ComponentUtils.toMinecraftChat(title)));
        nms.containerMenu = menu;
        nms.initMenu(menu);
        return nms.containerMenu.getBukkitView();
    }

    @Override
    public ItemStack setItemDisplayName(@NotNull final ItemStack item, final BaseComponent displayName) {
        final CraftItemStack craftItem = ensureCraftItemStack(item);
        final net.minecraft.world.item.ItemStack nmsItem = getItemStackHandle(craftItem);
        nmsItem.setHoverName(ComponentUtils.toMinecraftChat(displayName));
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public ItemStack setItemLore(@NotNull ItemStack item, @NotNull List<BaseComponent> lore) {
        final CraftItemStack craftItem = ensureCraftItemStack(item);
        final net.minecraft.world.item.ItemStack nmsItem = getItemStackHandle(craftItem);

        final CompoundTag tag = nmsItem.getTag() == null ? new CompoundTag() : nmsItem.getTag();
        if (!tag.contains(net.minecraft.world.item.ItemStack.TAG_DISPLAY)) {
            tag.put(net.minecraft.world.item.ItemStack.TAG_DISPLAY, new CompoundTag());
        }

        final CompoundTag displayTag = tag.getCompound(net.minecraft.world.item.ItemStack.TAG_DISPLAY);
        ListTag loreTag = new ListTag();
        for (int i = 0; i < lore.size(); i++) {
            loreTag.add(i, StringTag.valueOf(ComponentUtils.toJsonString(lore.get(i))));
        }

        displayTag.put(net.minecraft.world.item.ItemStack.TAG_LORE, loreTag);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public List<BaseComponent> getItemLore(@NotNull final ItemStack item) {
        final CraftItemStack craftItem = ensureCraftItemStack(item);
        final net.minecraft.world.item.ItemStack nmsItem = getItemStackHandle(craftItem);

        final CompoundTag tag = nmsItem.getTag();
        if (tag == null) {
            return List.of();
        }

        final CompoundTag displayTag = tag.getCompound(net.minecraft.world.item.ItemStack.TAG_DISPLAY);
        if (displayTag == null) {
            return List.of();
        }

        final ListTag loreTag = displayTag.getList(net.minecraft.world.item.ItemStack.TAG_LORE, CraftMagicNumbers.NBT.TAG_LIST);
        if (loreTag == null) {
            return List.of();
        }

        final List<BaseComponent> lore = new ArrayList<>();
        for (int i = 0; i < loreTag.size(); i++) {
            lore.add(ComponentUtils.toBungeeChat(loreTag.getString(i)));
        }

        return lore;
    }

    @NotNull
    @Override
    public byte[] itemToBytes(@NotNull final ItemStack itemStack) {
        CraftItemStack craft = ensureCraftItemStack(itemStack);
        CompoundTag tag = getItemStackHandle(craft).getOrCreateTag();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (DataOutputStream dos = new DataOutputStream(baos)) {
                NbtIo.write(tag, dos);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public ItemStack itemFromBytes(@NotNull final byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            try (DataInputStream dis = new DataInputStream(bais)) {
                return CraftItemStack.asCraftMirror(net.minecraft.world.item.ItemStack.of(NbtIo.read(dis)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public PineappleUnsafe getUnsafe() {
        return this.unsafe;
    }

    private CraftItemStack ensureCraftItemStack(ItemStack item) {
        return item instanceof CraftItemStack craftItem ? craftItem : CraftItemStack.asCraftCopy(item);
    }

    private static net.minecraft.world.item.ItemStack getItemStackHandle(CraftItemStack itemStack) {
        try {
            return (net.minecraft.world.item.ItemStack) itemStackHandle.invokeExact(itemStack);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }
}
