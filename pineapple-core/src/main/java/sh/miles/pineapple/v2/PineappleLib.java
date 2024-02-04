package sh.miles.pineapple.v2;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.nms.annotations.NMS;
import sh.miles.pineapple.nms.api.PineappleNMS;
import sh.miles.pineapple.nms.loader.NMSLoader;

/**
 * The main library class for PineappleLib. That should be loaded using {@link PineappleLib#initialize(Plugin)}
 * <p>
 * Provides ease of access to many features of PineappleLib and puts them all into one place.
 *
 * @since 1.0.0
 */
public final class PineappleLib {

    private static PineappleLib instance;

    private final Plugin plugin;
    private final boolean useNms;
    private final PineappleNMS nmsProvider;

    private PineappleLib(final Plugin plugin, final boolean useNms) {
        this.plugin = plugin;
        this.useNms = useNms;
        if (useNms) {
            NMSLoader.INSTANCE.activate();
        }
        this.nmsProvider = NMSLoader.INSTANCE.getPineapple();
    }

    /**
     * Gets the PineappleNMS provider
     * <p>
     * Given That NMS was disabled this will throw an error
     *
     * @return PineappleNMS
     */
    @NMS
    public static PineappleNMS getNmsProvider() {
        Preconditions.checkArgument(NMSLoader.INSTANCE.isActive(), "PineappleNMS must be loaded to be accessed");
        return instance.nmsProvider;
    }

    /**
     * Initializes PineappleLib
     *
     * @param plugin the plugin
     */
    @NMS
    public static void initialize(@NotNull final Plugin plugin) {
        instance = new PineappleLib(plugin, true);
    }

    /**
     * Initializes PineappleLib
     *
     * @param plugin the plugin
     * @param useNms decides whether or not to use NMS
     */
    public static void initialize(@NotNull final Plugin plugin, final boolean useNms) {
        instance = new PineappleLib(plugin, useNms);
    }
}
