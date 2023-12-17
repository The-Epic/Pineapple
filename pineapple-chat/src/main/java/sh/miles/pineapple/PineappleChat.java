package sh.miles.pineapple;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Exposed Chat Class for pineapple
 */
public final class PineappleChat {

    private PineappleChat() {
        throw new UnsupportedOperationException("you can't do that");
    }

    /**
     * Parses a string and its replacements into a component
     *
     * @param message the message
     * @return the component output
     */
    @NotNull
    public static BaseComponent parse(String message) {
        return MiniMessageParser.parseFormat(message);
    }

    /**
     * Parses a string and its replacements into a component
     *
     * @param message      the message
     * @param placeholders the placeholder
     * @return the component output
     */
    @NotNull
    public static BaseComponent parse(String message, Map<String, String> placeholders) {
        return MiniMessageParser.parseFormat(message, placeholders);
    }

    /**
     * Parses a string and its replacements into a component
     *
     * @param message      the message
     * @param placeholders the placeholder
     * @return the component output
     */
    @NotNull
    public static BaseComponent parse(String message, String... placeholders) {
        return MiniMessageParser.parseFormat(message, placeholders);
    }

    /**
     * Serializes a component into mini message format
     *
     * @param component the component
     * @return the string in mini message format
     */
    @NotNull
    public static String serialize(@NotNull final BaseComponent component) {
        return MiniMessageSerializer.serialize(component);
    }

}
