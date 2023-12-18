package sh.miles.pineapple.bstats;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.Callable;

public class BStatsManager {
    private final int pluginId;
    private final Metrics bStats;

    public BStatsManager(JavaPlugin plugin, int pluginId) {
        this.bStats = new Metrics(plugin, pluginId);
        this.pluginId = pluginId;
    }

    public void addServerType(String pieName) {
        this.bStats.addCustomChart(new SimplePie(pieName, BstatsUtils::getServerType));
    }

    public void addSimplePie(String pieName, Callable<String> callable) {
        this.bStats.addCustomChart(new SimplePie(pieName, callable));
    }

    public void addAdvancedPie(String pieName, Callable<Map<String, Integer>> callable) {
        this.bStats.addCustomChart(new AdvancedPie(pieName, callable));
    }

}
