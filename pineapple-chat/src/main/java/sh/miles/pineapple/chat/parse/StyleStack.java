package sh.miles.pineapple.chat.parse;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.PineappleComponentBuilder;
import sh.miles.pineapple.chat.tag.AbstractTag;
import sh.miles.pineapple.chat.tag.ClickEventTag;
import sh.miles.pineapple.chat.tag.ColorTag;
import sh.miles.pineapple.chat.tag.DecorationTag;
import sh.miles.pineapple.chat.tag.HoverEventTag;

import java.util.ArrayDeque;
import java.util.Deque;

public class StyleStack {

    private final Deque<ColorTag> colors = new ArrayDeque<>();
    private final Deque<DecorationTag> decorations = new ArrayDeque<>();
    private final Deque<ClickEventTag> clickEvents = new ArrayDeque<>();
    private final Deque<HoverEventTag> hoverEvents = new ArrayDeque<>();


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

    public void applyTopColor(@NotNull final PineappleComponentBuilder builder, @NotNull final PineappleParserContext context) {
        ColorTag tag = colors.peek();
        if (tag != null) {
            tag.apply(builder, context);
        }
    }

    public void applyTopClick(@NotNull final PineappleComponentBuilder builder, @NotNull final PineappleParserContext context) {
        ClickEventTag tag = clickEvents.peek();
        if (tag != null) {
            tag.apply(builder, context);
        }
    }

    public void applyTopHover(@NotNull final PineappleComponentBuilder builder, @NotNull final PineappleParserContext context) {
        HoverEventTag tag = hoverEvents.peek();
        if (tag != null) {
            tag.apply(builder, context);
        }
    }

    public Deque<DecorationTag> getDecorations() {
        return new ArrayDeque<>(decorations);
    }
}
