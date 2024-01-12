package sh.miles.pineapple;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.config.ConfigurationManager;
import sh.miles.pineapple.menu.manage.MenuManager;

import java.util.logging.Logger;

/**
 * The main library class for PineappleLib. That should be loaded using {@link PineappleLib#initialize(Plugin)}
 * <p>
 * Provides ease of access to many features of PineappleLib and puts them all into one place.
 *
 * @since 1.0.0
 */
public class PineappleLib {
    private static PineappleLib instance;

    private final Plugin plugin;
    private final ConfigurationManager configManager;
    private final MenuManager menuManager;

    private PineappleLib(Plugin plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigurationManager(plugin);
        this.menuManager = new MenuManager(plugin);
    }

    @NotNull
    public static Logger getLogger() {
        return getInstance().plugin.getLogger();
    }

    @NotNull
    public static ConfigurationManager getConfigurationManager() {
        return getInstance().configManager;
    }

    @NotNull
    public static MenuManager getMenuManager() {
        return getInstance().menuManager;
    }

    /**
     * Initializes PineappleLib using the provided plugin
     *
     * @param plugin the plugin to initialize PineappleLib
     */
    public static void initialize(Plugin plugin) {
        Preconditions.checkArgument(instance == null, "PineappleLib is already initialized");
        instance = new PineappleLib(plugin);
    }

    /**
     * Cleans up PineappleLib by setting the underlying instance to null
     */
    public static void cleanup() {
        Preconditions.checkArgument(instance != null, "PineappleLib is not initialized so it can not be cleaned up");
        instance = null;
    }

    /**
     * Gets the current PineappleLib instance or throws if one does not exist
     *
     * @return the PineappleLib instance
     * @throws IllegalStateException throws if {@link PineappleLib#initialize(Plugin)} has not yet been called
     */
    @NotNull
    public static PineappleLib getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Not initialized");
        }

        return instance;
    }
}
