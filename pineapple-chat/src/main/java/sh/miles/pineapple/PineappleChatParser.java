package sh.miles.pineapple;

import net.md_5.bungee.api.chat.BaseComponent;
import sh.miles.pineapple.util.PineappleComponentBuilder;

public final class PineappleChatParser {

    private PineappleChatParser() {
    }

    public static BaseComponent parse(String message) {
        PineappleComponentBuilder builder = PineappleComponentBuilder.empty();

        for (int i = 0; i < message.length(); i++) {
            final char at = message.charAt(i);

        }

        return builder.build();
    }

}
