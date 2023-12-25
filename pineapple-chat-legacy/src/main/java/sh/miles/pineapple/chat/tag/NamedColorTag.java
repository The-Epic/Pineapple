package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;

public class NamedColorTag extends ColorTag {

    NamedColorTag(@NotNull String namespace, @NotNull final Queue<String> arguments, int childTextLength) {
        super(namespace, arguments, childTextLength, ChatColor.valueOf(namespace.toUpperCase()));
    }
}
