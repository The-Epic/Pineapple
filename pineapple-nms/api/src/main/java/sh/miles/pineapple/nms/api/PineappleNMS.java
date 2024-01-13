package sh.miles.pineapple.nms.api;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.collection.registry.FrozenRegistry;
import sh.miles.pineapple.collection.registry.RegistryKey;
import sh.miles.pineapple.nms.api.menu.MenuType;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;
import sh.miles.pineapple.nms.api.world.damagesource.DamageType;

import java.util.List;

/**
 * Pineapple NMS adapts a bunch of useful stuffs from NMS so that we can use it. Don't tell MD_5 he won't be happy.
 *
 * @since 1.0.0
 */
public interface PineappleNMS {

    /**
     * Opens a newly created empty menu for the given player
     *
     * @param human the human to open the menu for
     * @param type  the type of menu to open
     * @param title the title of the menu to open
     * @param <T>   The ContainerScene type held by the menu
     * @return the menu scene
     * @since 1.0.0
     */
    @Nullable
    <T extends MenuScene> T openContainer(@NotNull final HumanEntity human, MenuType<T> type, String title);


    /**
     * Opens a newly created empty menu for the given player
     *
     * @param human the human to open the menu for
     * @param type  the type of menu to open
     * @param title the title to give the menu
     * @param <T>   The ContainerScene type held by the menu
     * @return the menu scene
     * @since 1.0.0
     */
    @Nullable
    <T extends MenuScene> T openContainer(@NotNull final HumanEntity human, MenuType<T> type, BaseComponent... title);


    /**
     * Opens a container scene for the given player
     *
     * @param human the player to open the scene for
     * @param scene the scene to open
     * @return the player to open the container for
     * @since 1.0.0
     */
    @NotNull
    MenuScene openContainer(@NotNull final HumanEntity human, @NotNull final MenuScene scene);

    /**
     * Opens an inventory with a base component title. Note this implementation uses CraftContainer so Inventories
     * opened in this way will not work as intended
     *
     * @param player    the player
     * @param inventory the inventory to open
     * @param title     the title to use
     * @return the legacy bukkit inventory view associated with this task
     */
    @Nullable
    InventoryView openInventory(@NotNull final Player player, @NotNull final Inventory inventory, @NotNull BaseComponent... title);

    /**
     * Renames the given item stack with the base component
     *
     * @param item        the item to rename
     * @param displayName the display name
     * @return the item with its display name changed
     */
    ItemStack setItemDisplayName(@NotNull final ItemStack item, BaseComponent displayName);

    /**
     * Sets the lore of the given item stack with the base component list
     *
     * @param item the item to set the lore of
     * @param lore the lore
     * @return the item with its lore changed
     */
    ItemStack setItemLore(@NotNull final ItemStack item, List<BaseComponent> lore);

    /**
     * Gets the lore of the given item stack and returns the component list
     *
     * @param item the item to get the lore of
     * @return the lore
     */
    List<BaseComponent> getItemLore(@NotNull final ItemStack item);

    /**
     * Gets the last DamageType that an entity experienced
     *
     * @param entity the entity
     * @return the last damage type or null
     */
    @Nullable
    DamageType getEntityLastDamageType(@NotNull final LivingEntity entity);

    @ApiStatus.Internal
    @NotNull
    <T extends RegistryKey<NamespacedKey>> FrozenRegistry<T, NamespacedKey> getRegistry(Class<? super T> clazz);

    /**
     * Converts the given ItemStack into bytes
     *
     * @param itemStack the item stack to convert to bytes
     * @return the bytes
     */
    @NotNull
    byte[] itemToBytes(@NotNull final ItemStack itemStack);

    /**
     * Converts teh given bytes into an ItemStack
     *
     * @param bytes the bytes to convert into an ItemStack
     * @return the ItemStack
     */
    @NotNull
    ItemStack itemFromBytes(@NotNull final byte[] bytes);

}
