package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.style.ChatColorUtils;

import java.util.Queue;

public class ColorTag extends AbstractTag {

    protected final ChatColor color;

    ColorTag(final @NotNull Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength);
        arguments.poll();
        this.color = ChatColorUtils.from(arguments.poll());
    }

    ColorTag(final @NotNull Queue<String> arguments, final int childTextLength, @NotNull final ChatColor color) {
        super(arguments, childTextLength);
        this.color = color;
    }

    @Override
    public void apply(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        builder.color(color);
    }

    @Override
    public String toString() {
        return "ColorTag(\"%s\")".formatted(this.color.getColor());
    }
}
