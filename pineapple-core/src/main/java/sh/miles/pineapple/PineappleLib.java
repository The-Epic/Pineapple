package sh.miles.pineapple;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.bstats.BStatsManager;
import sh.miles.pineapple.config.ConfigurationManager;
import sh.miles.pineapple.json.JsonAdapter;
import sh.miles.pineapple.json.JsonHelper;
import sh.miles.pineapple.menu.MenuManager;
import sh.miles.pineapple.task.work.ServerThreadTicker;

import java.util.List;
import java.util.logging.Logger;

public class PineappleLib {
    private static PineappleLib instance;

    private final Plugin plugin;
    private final ConfigurationManager configManager;
    private final MenuManager menuManager;
    private final ServerThreadTicker threadTicker;
    private BStatsManager bStatsManager;

    private PineappleLib(Plugin plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigurationManager(plugin);
        this.menuManager = new MenuManager(plugin);
        this.threadTicker = new ServerThreadTicker(plugin);
    }

    public static void initialize(Plugin plugin) {
        instance = new PineappleLib(plugin);
        // Add inv events
    }

    public static void cleanup() {
        instance = null;
    }

    public void enableBStats(int pluginId) {
        if (bStatsManager != null) {
            throw new IllegalStateException("BStats is already enabled");
        }
        bStatsManager = new BStatsManager((JavaPlugin) plugin, pluginId);
    }

    @NotNull
    public static PineappleLib getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Not initialized");
        }

        return instance;
    }

    @NotNull
    public static Plugin getPluginInstance() {
        return getInstance().plugin;
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

    @NotNull
    public static ServerThreadTicker getThreadTicker() {
        return getInstance().threadTicker;
    }

    @NotNull
    public static JsonHelper createNewJsonHelper(List<JsonAdapter<?>> adapters) {
        return new JsonHelper(adapters);
    }

    /**
     * Call {@link #enableBStats(int)} prior
     *
     * @return BstatsManager
     */
    @Nullable
    public static BStatsManager getBStatsManager() {
        return getInstance().bStatsManager;
    }
}
