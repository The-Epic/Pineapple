package sh.miles.pineapple.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.Map;

public class PineappleChatTest {

    public static void main(String[] args) {
        final Map<String, Object> replacements = Map.of("name", "JeryTheCarry", "age", 21);
        BaseComponent component = PineappleChat.parse("<yellow>random <gradient:red:blue><bold><$name></gradient></bold> <click:run_command:/weather clear><underline><red>click here</click><blue> to <bold>FEEL</underline> it", replacements);
        System.out.println(ComponentSerializer.toString(component));
    }
}
