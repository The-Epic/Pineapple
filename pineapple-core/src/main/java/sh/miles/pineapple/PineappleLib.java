package sh.miles.pineapple;

import org.bukkit.plugin.Plugin;
import sh.miles.pineapple.config.ConfigurationManager;
import sh.miles.pineapple.menu.MenuManager;

import java.util.logging.Logger;

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

    public static void initialize(Plugin plugin) {
        instance = new PineappleLib(plugin);
        // Add inv events
    }

    public static void cleanup() {
        instance = null;
    }

    public static PineappleLib getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Not initialized");
        }

        return instance;
    }

    public static Plugin getPluginInstance() {
        return getInstance().plugin;
    }

    public static Logger getLogger() {
        return getInstance().plugin.getLogger();
    }

    public static ConfigurationManager getConfigurationManager() {
        return getInstance().configManager;
    }

    public static MenuManager getMenuManager() {
        return getInstance().menuManager;
    }
}
