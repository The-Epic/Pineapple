package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.ColorUtil;
import sh.miles.pineapple.chat.parse.ParserContext;

import java.util.Queue;

public class GradientTag extends ColorTag implements IteratingTag {

    protected ChatColor endColor;
    protected ChatColor[] colors;
    int index;

    GradientTag(final @NotNull String namespace, final @NotNull Queue<String> arguments, final int childTextLength) {
        super(namespace, arguments, childTextLength);
        endColor = nextColor(arguments);
        this.colors = ColorUtil.createGradient(super.color.getColor(), this.endColor.getColor(), childTextLength);
    }

    @Override
    public void next() {
        index++;
    }

    @Override
    public void apply(final @NotNull PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        if (index > colors.length) {
            throw new IllegalStateException("Issue while applying gradient! Gradient application extends beyond expected range of %d".formatted(super.childTextLength));
        }
        builder.color(colors[index]);
    }
}
