package sh.miles.pineapple.config.adapter;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import sh.miles.pineapple.chat.PineappleChat;
import sh.miles.pineapple.config.adapter.base.TypeAdapter;
import sh.miles.pineapple.item.ItemBuilder;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class ItemStackAdapter implements TypeAdapter<Map<String, Object>, ItemStack> {

    private static final String ITEM_TYPE_KEY = "item_type";
    private static final String AMOUNT_KEY = "amount";
    private static final String NAME_KEY = "name";
    private static final String LORE_KEY = "lore";
    private static final String ENCHANTMENTS_KEY = "enchantments";
    private static final String ITEM_FLAGS_KEY = "item_flags";
    private static final String MODEL_DATA_KEY = "model_data";
    private static final String MODIFIERS_KEY = "attributes";
    private static final String DYE_COLOR_KEY = "dye_color";
    private static final String POTION_COLOR_KEY = "potion_color";
    private static final String POTION_DATA_KEY = "potion_data";
    private static final String POTION_EFFECTS_KEY = "potion_effects";
    private static final String UNBREAKABLE_KEY = "unbreakable";
    private static final String SKULL_TEXTURE_KEY = "skull_texture";
    private static final String STORED_ENCHANTMENTS_KEY = "stored_enchantments";

    @Override
    @SuppressWarnings("unchecked")
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<ItemStack> getRuntimeType() {
        return ItemStack.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemStack read(final Map<String, Object> value) {
        Material itemType = Material.matchMaterial((String) value.get(ITEM_TYPE_KEY));
        int amount = Integer.parseInt((String) value.getOrDefault(AMOUNT_KEY, "1"));
        final ItemBuilder builder = ItemBuilder.of(itemType, amount);
        if (value.containsKey(NAME_KEY)) {
            builder.name(PineappleChat.parse((String) value.get(NAME_KEY)));
        }

        if (value.containsKey(LORE_KEY)) {
            List<BaseComponent> lore = ((List<String>) value.get(LORE_KEY)).stream().map(PineappleChat::parse).toList();
            builder.lore(lore);
        }

        if (value.containsKey(ENCHANTMENTS_KEY)) {
            Map<Enchantment, Integer> enchantments = ((Map<String, Object>) value.get(ENCHANTMENTS_KEY)).entrySet().stream()
                    .collect(Collectors.toMap(
                            (entry) -> Enchantment.getByKey(NamespacedKey.minecraft(entry.getKey())),
                            (entry) -> Integer.parseInt((String) entry.getValue())
                    ));
            builder.enchantments(enchantments);
        }

        if (value.containsKey(ITEM_FLAGS_KEY)) {
            ItemFlag[] flags = ((List<String>) value.get(ITEM_FLAGS_KEY)).stream().map(ItemFlag::valueOf).toArray(ItemFlag[]::new);
            builder.flags(flags);
        }

        if (value.containsKey(MODEL_DATA_KEY)) {
            int modelData = Integer.parseInt((String) value.get(MODEL_DATA_KEY));
            builder.modelData(modelData);
        }

        if (value.containsKey(MODIFIERS_KEY)) {
            // TODO: impl later
        }

        if (value.containsKey(DYE_COLOR_KEY)) {
            Color color = Color.decode((String) value.get(DYE_COLOR_KEY));
            builder.dyeColor(org.bukkit.Color.fromRGB(color.getRed(), color.getBlue(), color.getGreen()));
        }

        if (value.containsKey(POTION_COLOR_KEY)) {
            Color color = Color.decode((String) value.get(POTION_COLOR_KEY));
            builder.potionColor(org.bukkit.Color.fromRGB(color.getRed(), color.getBlue(), color.getGreen()));
        }

        if (value.containsKey(POTION_DATA_KEY)) {
            Map<String, Object> data = ((Map<String, Object>) value.get(POTION_DATA_KEY));
            final PotionData potionData = new PotionData(
                    PotionType.valueOf((String) data.get("potion_type")),
                    Boolean.parseBoolean((String) data.get("extended")),
                    Boolean.parseBoolean((String) data.get("upgraded"))
            );
            builder.potionData(potionData);
        }

        if (value.containsKey(POTION_EFFECTS_KEY)) {
            PotionEffect[] effects = ((List<Map<String, Object>>) value.get(POTION_EFFECTS_KEY)).stream().map(PotionEffect::new).toArray(PotionEffect[]::new);
            builder.potionEffects(effects);
        }

        if (value.containsKey(UNBREAKABLE_KEY)) {
            builder.unbreakable();
        }

        if (value.containsKey(SKULL_TEXTURE_KEY)) {
            builder.skullTexture((String) value.get(SKULL_TEXTURE_KEY));
        }

        if (value.containsKey(STORED_ENCHANTMENTS_KEY)) {
            Map<Enchantment, Integer> enchantments = ((Map<String, Object>) value.get(ENCHANTMENTS_KEY)).entrySet().stream()
                    .collect(Collectors.toMap(
                            (entry) -> Enchantment.getByKey(NamespacedKey.minecraft(entry.getKey())),
                            (entry) -> Integer.parseInt((String) entry.getValue())
                    ));
            builder.storedEnchantments(enchantments);
        }

        return builder.build();
    }

    @Override
    public Map<String, Object> write(final ItemStack value, Map<String, Object> existing, final boolean replace) {
        if (existing == null) {
            existing = new LinkedHashMap<>();
        }

        if (!existing.containsKey(ITEM_TYPE_KEY) || replace) {
            existing.put(ITEM_TYPE_KEY, value.getType());
        }

        if (!existing.containsKey(AMOUNT_KEY) || replace) {
            existing.put(AMOUNT_KEY, value.getAmount());
        }

        final ItemMeta meta = value.getItemMeta();

        if ((!existing.containsKey(NAME_KEY) || replace) && meta.hasDisplayName()) {
            existing.put(NAME_KEY, meta.getDisplayName());
        }

        if ((!existing.containsKey(LORE_KEY) || replace) && meta.hasLore()) {
            existing.put(LORE_KEY, meta.getLore());
        }

        if ((!existing.containsKey(ENCHANTMENTS_KEY) || replace) && meta.hasEnchants()) {
            final Map<String, Integer> formatted = meta.getEnchants().entrySet().stream().collect(Collectors.toMap(
                    (entry) -> entry.getKey().getKey().getKey(),
                    Map.Entry::getValue
            ));
            existing.put(ENCHANTMENTS_KEY, formatted);
        }

        if ((!existing.containsKey(ITEM_FLAGS_KEY) || replace) && !meta.getItemFlags().isEmpty()) {
            existing.put(ITEM_FLAGS_KEY, meta.getItemFlags().stream().map(ItemFlag::name).toList());
        }

        if (meta instanceof LeatherArmorMeta armorMeta && (!existing.containsKey(DYE_COLOR_KEY) || replace)) {
            final org.bukkit.Color color = armorMeta.getColor();
            existing.put(DYE_COLOR_KEY, "#" + Integer.toHexString(new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()));
        }

        if (meta instanceof PotionMeta potionMeta) {
            if (!existing.containsKey(POTION_COLOR_KEY) || replace) {
                final org.bukkit.Color color = potionMeta.getColor();
                existing.put(POTION_COLOR_KEY, "#" + Integer.toHexString(new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()));
            }

            if (!existing.containsKey(POTION_DATA_KEY) || replace) {
                final Map<String, Object> data = new HashMap<>();
                PotionData potionData = potionMeta.getBasePotionData();
                data.put("potion_type", potionData.getType());
                data.put("extended", potionData.isExtended());
                data.put("upgraded", potionData.isUpgraded());
                existing.put(POTION_DATA_KEY, data);
            }

            if (!existing.containsKey(POTION_EFFECTS_KEY) || replace) {
                existing.put(POTION_EFFECTS_KEY, potionMeta.getCustomEffects().stream().map(PotionEffect::serialize).toList());
            }
        }

        if (meta.isUnbreakable() && (!existing.containsKey(UNBREAKABLE_KEY) || replace)) {
            existing.put(UNBREAKABLE_KEY, true);
        }

        if (meta instanceof SkullMeta skullMeta && (!existing.containsKey(SKULL_TEXTURE_KEY) || replace)) {
            existing.put(SKULL_TEXTURE_KEY, skullMeta.getOwnerProfile().getTextures().getSkin().toString());
        }

        if (meta instanceof EnchantmentStorageMeta storageMeta && (!existing.containsKey(STORED_ENCHANTMENTS_KEY) || replace)) {
            final Map<String, Integer> formatted = storageMeta.getStoredEnchants().entrySet().stream().collect(Collectors.toMap(
                    (entry) -> entry.getKey().getKey().getKey(),
                    Map.Entry::getValue
            ));
            existing.put(STORED_ENCHANTMENTS_KEY, formatted);
        }

        return existing;
    }
}
