package sh.miles.pineapple.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.PineappleParser;

import java.util.Map;

public final class PineappleChat {

    private PineappleChat() {
        throw new UnsupportedOperationException("you can't do that");
    }

    public static BaseComponent parse(@NotNull final String string) {
        return PineappleParser.parse(string);
    }

    public static BaseComponent parse(@NotNull final String string, @NotNull final Map<String, Object> replacements) {
        return PineappleParser.parse(string, replacements);
    }

}
