package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.style.ChatColorUtils;

import java.util.Queue;

public class GradientTag extends ColorTag implements IteratingTag {

    protected ChatColor endColor;
    protected ChatColor[] colors;
    int index;

    GradientTag(final @NotNull Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength, initFirstColor(arguments));
        endColor = ChatColorUtils.from(arguments.poll());
        this.colors = ChatColorUtils.createLinearGradient(super.color.getColor(), this.endColor.getColor(), childTextLength);
    }

    private static ChatColor initFirstColor(@NotNull final Queue<String> arguments) {
        arguments.poll();
        return ChatColorUtils.from(arguments.poll());
    }

    @Override
    public void next() {
        index++;
    }

    @Override
    public void apply(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        if (index > colors.length) {
            throw new IllegalStateException("Issue while applying gradient! Gradient application extends beyond expected range of %d".formatted(super.childTextLength));
        }
        builder.color(colors[index]);
    }
}
