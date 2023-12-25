package sh.miles.pineapple.chat.tag;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.PineappleParserContext;

import java.util.Queue;

public abstract class AbstractTag {

    protected final String namespace;
    protected final Queue<String> arguments;
    protected final int childTextLength;

    protected AbstractTag(@NotNull String namespace, @NotNull final Queue<String> arguments, int childTextLength) {
        this.namespace = namespace;
        this.arguments = arguments;
        this.childTextLength = childTextLength;
    }

    public abstract void apply(final @NotNull PineappleComponentBuilder builder, PineappleParserContext context);

    public int getChildTextLength() {
        return this.childTextLength;
    }
}
