package sh.miles.pineapple.nms.v1_20_R3.inventory.scene;

import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryView;
import sh.miles.nms.api.menu.MenuType;
import sh.miles.nms.api.menu.scene.MenuScene;
import sh.miles.pineapple.nms.v1_20_R3.inventory.PineappleMenuType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class PineappleSceneFactory {

    private static final Map<MenuType<?>, Function<CraftInventoryView, MenuScene>> factory = new HashMap<>();

    static {
        factory.put(MenuType.ANVIL, PineappleAnvilScene::new);
    }

    @SuppressWarnings("unchecked")
    public static <T extends MenuScene> T make(MenuType<T> type, CraftInventoryView view) {
        return (T) factory.getOrDefault(((PineappleMenuType<T>) type).getHandle(), PineappleMenuScene::new).apply(view);
    }
}
