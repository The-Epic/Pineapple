package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.PineappleComponentBuilder;

import java.util.Queue;

public abstract class AbstractTag {

    protected final String namespace;
    protected final Queue<String> arguments;
    protected final int childTextLength;

    protected AbstractTag(@NotNull final Queue<String> arguments, final int childTextLength) {
        this.namespace = arguments.poll();
        this.arguments = arguments;
        this.childTextLength = childTextLength;
    }

    public abstract void apply(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context);

    public int getChildTextLength() {
        return this.childTextLength;
    }
}
