package sh.miles.pineapple.menu.v2;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.api.menu.scene.MenuScene;

import java.util.function.Function;

public abstract class SimpleMenu<T extends MenuScene> implements Menu<T> {

    private final T scene;
    private final Player viewer;
    private final Inventory inventory;
    private final Slot[] slots;

    public SimpleMenu(@NotNull final Function<Player, T> function, @NotNull final Player viewer) {
        this.scene = function.apply(viewer);
        this.viewer = viewer;
        this.inventory = scene.getBukkitView().getTopInventory();
        this.slots = Slot.createEmptyArray(inventory);
    }

    @Override
    public void open() {
        // TODO: add registration to manager
        this.viewer.openInventory(inventory);
    }

    @Override
    public void close() {
        this.viewer.closeInventory();
    }
}
