package sh.miles.pineapple.json.adapter;

import com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;

public final class JsonAdapters {

    private JsonAdapters() {
    }

    public static void registerAll(GsonBuilder builder) {
        builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
    }

}
