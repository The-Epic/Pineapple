package sh.miles.pineapple.bstats;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class BstatsUtils {

    private static final Server SERVER = Bukkit.getServer();

    public static String getServerType() {
        if (SERVER.getOnlineMode()) {
            return "Online";
        }

        if (SERVER.spigot().getConfig().getBoolean("settings.bungeecord", false)) {
            return "Bungee";
        }

        return "Offline";
    }
}
