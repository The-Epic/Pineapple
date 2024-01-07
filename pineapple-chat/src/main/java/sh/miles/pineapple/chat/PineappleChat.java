package sh.miles.pineapple.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.PineappleParser;

import java.util.Map;

/**
 * The main PineappleChat access point
 *
 * @since 1.0.0-SNAPSHOT
 */
public final class PineappleChat {

    private PineappleChat() {
        throw new UnsupportedOperationException("you can't do that");
    }

    /**
     * Parses the given string into a BaseComponent
     *
     * @param string the string to parse
     * @return the BaseComponent
     * @since 1.0.0-SNAPSHOT
     */
    public static BaseComponent parse(@NotNull final String string) {
        return PineappleParser.parse(string);
    }

    /**
     * Parses the given string into a BaseComponent
     *
     * @param string       the string to parse
     * @param replacements the replacements to put into the string
     * @return the BaseComponent
     * @since 1.0.0-SNAPSHOT
     */
    public static BaseComponent parse(@NotNull final String string, @NotNull final Map<String, Object> replacements) {
        return PineappleParser.parse(string, replacements);
    }

    /**
     * Creates a PineappleComponent from a source string
     * <p>
     * For more information regarding pineapple components see {@link PineappleComponent}
     *
     * @param source the source string to create a PineappleComponent from.
     * @return the PineappleComponent
     * @since 1.0.0-SNAPSHOT
     */
    public static PineappleComponent component(@NotNull final String source) {
        return new PineappleComponent(source);
    }
}
