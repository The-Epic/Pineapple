package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.style.Decoration;

import java.util.Queue;

public class NamedDecorationTag extends DecorationTag {

    NamedDecorationTag(@NotNull final Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength, null, false);
        super.decoration = Decoration.valueOf(namespace);
        super.flag = hasFlag(arguments);
    }
}
