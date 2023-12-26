package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.PineappleComponentBuilder;
import sh.miles.pineapple.chat.style.Decoration;

import java.util.Queue;

public class DecorationTag extends AbstractTag {

    protected final Decoration decoration;
    protected final boolean flag;

    DecorationTag(final @NotNull Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength);
        arguments.poll();
        this.decoration = Decoration.valueOf(arguments.poll().toUpperCase());
        this.flag = hasFlag(arguments);
    }

    DecorationTag(final @NotNull Queue<String> arguments, final int childTextLength, @NotNull final Decoration decoration, final boolean flag) {
        super(arguments, childTextLength);
        this.decoration = decoration;
        this.flag = flag;
    }

    @Override
    public void apply(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        this.decoration.apply(builder.getCurrentComponent(), this.flag);
    }

    protected static boolean hasFlag(Queue<String> argument) {
        String string;
        return !((string = argument.poll()) != null && string.startsWith("!"));
    }
}
