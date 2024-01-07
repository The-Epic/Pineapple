package sh.miles.pineapple.json.adapter;

import com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;

/**
 * Medium class used to register all TypeAdapter's provided by pineapple to a GsonBuilder
 *
 * @since 1.0.0-SNAPSHOT
 */
public final class JsonAdapters {

    private JsonAdapters() {
    }

    /**
     * Registers all TypeAdapter's to a GsonBuilder
     *
     * @param builder the GsonBuilder to register them too
     * @since 1.0.0-SNAPSHOT
     */
    public static void registerAll(GsonBuilder builder) {
        builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
    }

}
