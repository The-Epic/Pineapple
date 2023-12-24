package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.chat.ComponentBuilder;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.style.Decoration;

import java.util.Queue;

public class DecorationTag extends AbstractTag {

    private final Decoration decoration;
    private final boolean flag;

    DecorationTag(@NotNull final String namespace, final @NotNull Queue<String> arguments, final int childTextLength) {
        this(namespace, arguments, childTextLength, Decoration.valueOf(arguments.poll().toUpperCase()), !arguments.poll().startsWith("!"));
    }

    DecorationTag(@NotNull final String namespace, final @NotNull Queue<String> arguments, final int childTextLength, @NotNull final Decoration decoration, final boolean flag) {
        super(namespace, arguments, childTextLength);
        this.decoration = decoration;
        this.flag = flag;
    }

    @Override
    public void apply(final @NotNull PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        this.decoration.apply(builder.getCurrentComponent(), this.flag);
    }
}
