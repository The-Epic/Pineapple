package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.style.ChatColorUtils;

import java.util.Queue;

public class NamedColorTag extends ColorTag {

    NamedColorTag(@NotNull final Queue<String> arguments, int childTextLength) {
        super(arguments, childTextLength, null);
        color = ChatColorUtils.fromNamedColor(super.namespace);
    }
}
