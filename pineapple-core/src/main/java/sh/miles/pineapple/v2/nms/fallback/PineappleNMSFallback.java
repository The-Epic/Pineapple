package sh.miles.pineapple.v2.nms.fallback;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.chat.bungee.builder.BungeeComponentBuilder;
import sh.miles.pineapple.nms.api.PineappleNMS;
import sh.miles.pineapple.nms.api.PineappleUnsafe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PineappleNMSFallback implements PineappleNMS {

    public static PineappleNMSFallback INSTANCE = new PineappleNMSFallback();

    private PineappleNMSFallback() {
    }

    @Nullable
    @Override
    public InventoryView openInventory(@NotNull final Player player, @NotNull final Inventory inventory, @NotNull final BaseComponent title) {
        var view = player.openInventory(inventory);
        view.setTitle(title.toLegacyText());
        return view;
    }

    @NotNull
    @Override
    public ItemStack setItemDisplayName(@NotNull final ItemStack item, final BaseComponent displayName) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName.toLegacyText());
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    @Override
    public ItemStack setItemLore(@NotNull final ItemStack item, final List<BaseComponent> lore) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(lore.stream().map((bc) -> bc.toLegacyText()).toList());
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    @Override
    public List<BaseComponent> getItemLore(@NotNull final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        return meta.getLore().stream().map(TextComponent::fromLegacyText).map(BungeeComponentBuilder::unarray).collect(Collectors.toList());
    }

    @NotNull
    @Override
    public byte[] itemToBytes(@NotNull final ItemStack itemStack) {
        final var baos = new ByteArrayOutputStream();
        try (var boos = new BukkitObjectOutputStream(baos)) {
            boos.writeObject(itemStack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

    @NotNull
    @Override
    public ItemStack itemFromBytes(@NotNull final byte[] bytes) {
        final var bais = new ByteArrayInputStream(bytes);
        try (final var bois = new BukkitObjectInputStream(bais)) {
            return (ItemStack) bois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public PineappleUnsafe getUnsafe() {
        throw new UnsupportedOperationException("Can not obtain unsafe values with fallback NMS implementation");
    }
}
