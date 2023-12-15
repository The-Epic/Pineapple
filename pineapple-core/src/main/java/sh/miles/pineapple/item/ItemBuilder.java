package sh.miles.pineapple.item;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public class ItemBuilder {

    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

    private ItemStack stack;
    private ItemMeta meta;

    private ItemBuilder() {
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.stack = new ItemStack(material, amount);
        this.meta = stack.getItemMeta();
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder modifyStack(ItemStack stack) {
        ItemBuilder builder = new ItemBuilder();
        builder.stack = stack;
        builder.meta = stack.getItemMeta();
        return builder;
    }

    public static ItemBuilder modifyStackClone(ItemStack stack) {
        return modifyStack(stack.clone());
    }

    public <T extends ItemMeta> ItemBuilder modify(Class<T> metaType, Consumer<T> action) {
        if (!metaType.isAssignableFrom(meta.getClass())) {
            return this;
        }

        action.accept(metaType.cast(meta));
        return this;
    }

    public ItemBuilder name(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<String> itemLore = getLore();
        itemLore.addAll(lore);

        meta.setLore(itemLore);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder texture(String texture) {
        if (this.stack.getType() != Material.PLAYER_HEAD) {
            return this;
        }

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(texture.getBytes()));
        SkullMeta skullMeta = (SkullMeta) this.meta;
        skullMeta.setOwnerProfile(profile);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder storedEnchantment(Enchantment enchantment, int level) {
        return modify(EnchantmentStorageMeta.class, meta -> meta.addStoredEnchant(enchantment, level, true));
    }

    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantmentAndLevel) {
        for (Map.Entry<Enchantment, Integer> entry : enchantmentAndLevel.entrySet()) {
            this.meta.addEnchant(entry.getKey(), entry.getValue(),true);
        }
        return this;
    }

    public ItemBuilder unbreakable() {
        this.meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        this.meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    public ItemBuilder modelData(int index) {
        this.meta.setCustomModelData(index);
        return this;
    }

    public ItemBuilder modifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder skullTexture(String texture) {
        return modify(SkullMeta.class, meta -> {
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(texture.getBytes()));
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URL(TEXTURE_URL + texture));
            } catch (MalformedURLException ignored) {
                return;
            }

            profile.setTextures(textures);
            ((SkullMeta) this.meta).setOwnerProfile(profile);
        });
    }

    public ItemBuilder skullTexture(Player player) {
        if (!(this.meta instanceof SkullMeta)) return this;
        ((SkullMeta)this.meta).setOwningPlayer(player);
        return this;
    }

    public ItemBuilder dyeColor(Color color) {
        if (!(this.meta instanceof LeatherArmorMeta))
            return this;

        ((LeatherArmorMeta) this.meta).setColor(color);
        return this;
    }

    public ItemBuilder potionColor(int red, int green, int blue) {
        if (!(this.meta instanceof PotionMeta))
            return this;

        ((PotionMeta) this.meta).setColor(Color.fromRGB(red, green, blue));
        return this;
    }

    public ItemBuilder potionData(PotionData data) {
        if (!(this.meta instanceof PotionMeta))
            return this;

        ((PotionMeta) this.meta).setBasePotionData(data);
        return this;
    }

    public ItemBuilder potionEffect(PotionEffect effect) {
        if (!(this.meta instanceof PotionMeta))
            return this;

        ((PotionMeta) this.meta).addCustomEffect(effect, true);
        return this;
    }

    public ItemBuilder potionEffects(PotionEffect... effects) {
        if (!(this.meta instanceof PotionMeta potionMeta))
            return this;

        for (PotionEffect effect : effects) {
            potionMeta.addCustomEffect(effect, true);
        }

        return this;
    }

    private List<String> getLore() {
        return meta.hasLore() ? meta.getLore() : new ArrayList<>();
    }

    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}
