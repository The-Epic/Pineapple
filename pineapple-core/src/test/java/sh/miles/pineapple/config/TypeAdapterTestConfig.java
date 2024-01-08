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

public class TypeAdapterTestConfig {

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
    public static EnumTest ENUM_1 = EnumTest.VALUE1;

    @ConfigEntry("test.enum.2")
    public static EnumTest ENUM_2 = EnumTest.VALUE2;

    @ConfigEntry("test.enum.3")
    public static EnumTest ENUM_3 = EnumTest.VALUE3;

    @ConfigEntry("test.item")
    public static ItemStack ITEM = ItemBuilder.of(Material.BARRIER).nameLegacy("TestName")
            .loreLegacy("line1", "line2", "line3").build();

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
        weightedRandom.add(0.5, "a");
        weightedRandom.add(0.5, "b");

        return weightedRandom;
    }
}