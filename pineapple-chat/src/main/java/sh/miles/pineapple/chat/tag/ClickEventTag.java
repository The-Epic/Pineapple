package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.chat.ClickEvent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.PineappleComponentBuilder;

import java.util.Queue;

public class ClickEventTag extends AbstractTag {

    private final ClickEvent clickEvent;

    protected ClickEventTag(final @NotNull Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength);
        ClickEvent.Action action = ClickEvent.Action.valueOf(arguments.poll().toUpperCase());
        String text = arguments.poll();
        this.clickEvent = new ClickEvent(action, text);
    }

    @Override
    public void apply(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        builder.event(this.clickEvent);
    }
}
