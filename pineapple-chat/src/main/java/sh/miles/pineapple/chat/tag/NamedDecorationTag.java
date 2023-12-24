package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.style.Decoration;

import java.util.Queue;

public class NamedDecorationTag extends DecorationTag {

    NamedDecorationTag(@NotNull final String namespace, final @NotNull Queue<String> arguments, final int childTextLength) {
        super(namespace, arguments, childTextLength, Decoration.valueOf(namespace.toUpperCase()), !arguments.poll().startsWith("!"));
    }
}
