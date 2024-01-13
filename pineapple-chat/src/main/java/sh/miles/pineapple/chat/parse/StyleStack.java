package sh.miles.pineapple.chat.parse;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.tag.AbstractTag;
import sh.miles.pineapple.chat.tag.ClickEventTag;
import sh.miles.pineapple.chat.tag.ColorTag;
import sh.miles.pineapple.chat.tag.DecorationTag;
import sh.miles.pineapple.chat.tag.HoverEventTag;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A stack that helps style a ComponentBuilder
 */
public class StyleStack {

    private final Deque<ColorTag> colors = new ArrayDeque<>();
    private final Deque<DecorationTag> decorations = new ArrayDeque<>();
    private final Deque<ClickEventTag> clickEvents = new ArrayDeque<>();
    private final Deque<HoverEventTag> hoverEvents = new ArrayDeque<>();

    /**
     * Pushes the tag to the top of the style stack
     *
     * @param tag the tag to push
     * @since 1.0.0-SNAPSHOT
     */
    public void push(@NotNull final AbstractTag tag) {
        if (tag instanceof ColorTag colorTag) {
            colors.push(colorTag);
        } else if (tag instanceof DecorationTag decorationTag) {
            decorations.push(decorationTag);
        } else if (tag instanceof ClickEventTag clickEventTag) {
            clickEvents.push(clickEventTag);
        } else if (tag instanceof HoverEventTag hoverEventTag) {
            hoverEvents.push(hoverEventTag);
        }
    }

    /**
     * Pops the given tag from the StyleStack
     *
     * @param tag the tag to pop
     * @since 1.0.0-SNAPSHOT
     */
    public void pop(@NotNull final AbstractTag tag) {
        if (tag instanceof ColorTag) {
            colors.pop();
        } else if (tag instanceof DecorationTag) {
            decorations.pop();
        } else if (tag instanceof ClickEventTag) {
            clickEvents.pop();
        } else if (tag instanceof HoverEventTag) {
            hoverEvents.pop();
        }
    }

    public ColorTag peekColors() {
        return colors.peek();
    }

    public DecorationTag peekDecorations() {
        return decorations.peek();
    }

    /**
     * Applies colors to the given builder
     *
     * @param builder the builder to use
     * @param context the parse context
     * @since 1.0.0-SNAPSHOT
     */
    public void applyTopColor(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        ColorTag tag = colors.peek();
        if (tag != null) {
            tag.apply(builder, context);
        }
    }

    /**
     * Applies click events to the given builder
     *
     * @param builder the builder to use
     * @param context the parse content
     * @since 1.0.0-SNAPSHOT
     */
    public void applyTopClick(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        ClickEventTag tag = clickEvents.peek();
        if (tag != null) {
            tag.apply(builder, context);
        }
    }

    /**
     * Applies hover events to the given builder
     *
     * @param builder the builder to use
     * @param context the parse content
     * @since 1.0.0-SNAPSHOT
     */
    public void applyTopHover(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        HoverEventTag tag = hoverEvents.peek();
        if (tag != null) {
            tag.apply(builder, context);
        }
    }

    public Deque<DecorationTag> getDecorations() {
        return new ArrayDeque<>(decorations);
    }
}
