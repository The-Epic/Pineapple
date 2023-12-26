package sh.miles.pineapple.chat.token;

import net.md_5.bungee.api.chat.BaseComponent;
import org.junit.jupiter.api.Test;
import sh.miles.pineapple.chat.PineappleChat;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PineappleChatTest {

    @Test
    public void test_Should_Not_Throw() {
        assertDoesNotThrow(this::testNiceMix);
    }

    public BaseComponent testNiceMix() {
        Map<String, Object> replacements = Map.of("name", "JeryTheCarry");
        final String input = "<yellow>random <gradient:red:blue><bold><$name></gradient></bold> <click:run_command:/weather clear><underlined><red>click here</click><blue> to <bold>FEEL</underlined> it";
        return PineappleChat.parse(input, replacements);
    }

}
