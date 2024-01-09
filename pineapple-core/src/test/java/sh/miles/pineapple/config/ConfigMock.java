package sh.miles.pineapple.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sh.miles.pineapple.chat.PineappleChat;
import sh.miles.pineapple.chat.PineappleComponent;
import sh.miles.pineapple.collection.WeightedRandom;
import sh.miles.pineapple.config.annotation.ConfigEntry;
import sh.miles.pineapple.item.ItemBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@SuppressWarnings("deprecation")
public class ConfigMock {

    @ConfigEntry("test.collection.list")
    public static List<String> COLLECTION_LIST = new ArrayList<>(List.of("a", "b", "c"));

    @ConfigEntry("test.collection.set")
    public static Set<String> COLLECTION_SET = new LinkedHashSet<>(List.of("a", "b", "c"));

    @ConfigEntry("test.collection.queue")
    public static Queue<String> COLLECTION_QUEUE = new ArrayDeque<>(List.of("a", "b", "c"));

    @ConfigEntry("test.collection.map")
    public static Map<String, String> COLLECTION_MAP = new HashMap<>(Map.of("a", "1", "b", "2", "c", "3"));

    @ConfigEntry("test.color.red")
    public static ChatColor COLOR_RED = ChatColor.RED;

    @ConfigEntry("test.color.green")
    public static ChatColor COLOR_GREEN = ChatColor.GREEN;

    @ConfigEntry("test.color.blue")
    public static ChatColor COLOR_BLUE = ChatColor.BLUE;

    @ConfigEntry("test.enum.1")
    public static EnumMock ENUM_1 = EnumMock.VALUE1;

    @ConfigEntry("test.enum.2")
    public static EnumMock ENUM_2 = EnumMock.VALUE2;

    @ConfigEntry("test.enum.3")
    public static EnumMock ENUM_3 = EnumMock.VALUE3;

    @ConfigEntry("test.material")
    public static Material MATERIAL = Material.BARRIER;

    @ConfigEntry("test.component")
    public static PineappleComponent CHAT = PineappleChat.component("<green></bold>Test");

    @ConfigEntry("test.primitive.boolean")
    public static boolean PRIMITIVE_BOOLEAN = true;

    @ConfigEntry("test.primitive.int")
    public static int PRIMITIVE_INT = 1;

    @ConfigEntry("test.primitive.long")
    public static long PRIMITIVE_LONG = 1;

    @ConfigEntry("test.weightedrandom")
    public static WeightedRandom<String> WEIGHTED_RANDOM = getWeightedRandom();

    public static WeightedRandom<String> getWeightedRandom() {
        WeightedRandom<String> weightedRandom = new WeightedRandom<>();
        weightedRandom.add(5, "a");
        weightedRandom.add(2.5, "b");
        weightedRandom.add(2.5, "c");
        return weightedRandom;
    }
}
