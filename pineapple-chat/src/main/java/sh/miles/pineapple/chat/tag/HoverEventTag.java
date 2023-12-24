package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.ParserContext;

import java.util.Queue;

public class HoverEventTag extends AbstractTag {

    private final HoverEvent event;

    protected HoverEventTag(@NotNull final String namespace, final @NotNull Queue<String> arguments, final int childTextLength) {
        super(namespace, arguments, childTextLength);
        arguments.poll();
        HoverEvent.Action action = HoverEvent.Action.valueOf(arguments.poll().toUpperCase());
        switch (action) {
            case SHOW_TEXT ->
                    event = new HoverEvent(action, new Text(new BaseComponent[]{ParserContext.INSTANCE.parse(dequoteArgument(arguments.poll()))}));
            case SHOW_ITEM ->
                    event = new HoverEvent(action, new Item(arguments.poll(), Integer.parseInt(arguments.poll()), ItemTag.ofNbt(arguments.poll())));
            case SHOW_ENTITY ->
                    event = new HoverEvent(action, new Entity(arguments.poll(), arguments.poll(), ParserContext.INSTANCE.parse(arguments.poll())));
            default -> throw new IllegalStateException("Unable to parse actoin %s".formatted(action));
        }
    }

    @Override
    public void apply(final @NotNull PineappleComponentBuilder builder, final ParserContext context) {
        builder.event(this.event);
    }

    private static String dequoteArgument(@NotNull final String argument) {
        return argument.substring(1, argument.length() - 1);
    }
}
