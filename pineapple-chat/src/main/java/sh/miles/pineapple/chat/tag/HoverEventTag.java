package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.tag.base.AbstractTag;

import java.util.Queue;

public class HoverEventTag extends AbstractTag {

    private HoverEvent event;

    protected HoverEventTag(final @NotNull Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength);
        arguments.poll();
    }

    @Override
    public void apply(final @NotNull PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        if (event == null) {
            HoverEvent.Action action = HoverEvent.Action.valueOf(arguments.poll().toUpperCase());
            switch (action) {
                case SHOW_TEXT ->
                        event = new HoverEvent(action, new Text(new BaseComponent[]{context.parse(dequoteArgument(arguments.poll()), context)}));
                case SHOW_ITEM ->
                        event = new HoverEvent(action, new Item(dequoteArgument(arguments.poll()), Integer.parseInt(arguments.poll()), ItemTag.ofNbt(arguments.poll())));
                case SHOW_ENTITY ->
                        event = new HoverEvent(action, new Entity(arguments.poll(), arguments.poll(), context.parse(arguments.poll(), context)));
                default -> throw new IllegalStateException("Unable to parse action %s".formatted(action));
            }
        }
        builder.event(this.event);
    }

    private static String dequoteArgument(@NotNull final String argument) {
        return argument.substring(1, argument.length() - 1);
    }
}
