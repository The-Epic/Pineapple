package sh.miles.pineapple.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PineappleComponent {

    private final String source;
    private BaseComponent parsed;

    PineappleComponent(@NotNull final String source) {
        this.source = source;
        this.parsed = null;
    }

    public BaseComponent component(@NotNull final Map<String, Object> replacements) {
        return PineappleChat.parse(this.source, replacements);
    }

    public BaseComponent component() {
        if (this.parsed != null) {
            return parsed;
        }

        this.parsed = PineappleChat.parse(this.source);
        return this.parsed;
    }

    public String getSource() {
        return source;
    }
}
