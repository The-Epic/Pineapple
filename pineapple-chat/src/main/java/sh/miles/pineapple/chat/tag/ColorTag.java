package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.ParserContext;

import java.awt.Color;
import java.util.Queue;

public class ColorTag extends AbstractTag {

    protected ChatColor color;

    ColorTag(@NotNull final String namespace, final @NotNull Queue<String> arguments, final int childTextLength) {
        super(namespace, arguments, childTextLength);
        arguments.poll(); // remove namespace
        this.color = nextColor(arguments);
    }

    ColorTag(@NotNull final String namespace, final @NotNull Queue<String> arguments, final int childTextLength, @NotNull final ChatColor color) {
        super(namespace, arguments, childTextLength);
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

    protected ChatColor nextColor(Queue<String> arguments) {
        final String colorString = arguments.poll();
        return colorString.startsWith("#") ? ChatColor.of(Color.decode(colorString)) : ChatColor.valueOf(colorString.toUpperCase());
    }
}
