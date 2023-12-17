package sh.miles.pineapple;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
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
}
