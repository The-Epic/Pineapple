package sh.miles.pineapple.chat.tag.base;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.bungee.PineappleComponentBuilder;
import sh.miles.pineapple.chat.parse.ParserContext;

import java.util.Queue;

public abstract class TextSupplierTag extends AbstractTag {

    protected TextSupplierTag(@NotNull final Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength);
    }

    @Override
    public void apply(@NotNull final PineappleComponentBuilder builder, @NotNull final ParserContext context) {
        context.applyStyle(builder, apply(), context);
    }

    /**
     * Gets the BaseComponent created from applying this tag
     *
     * @return the BaseComponent
     */
    protected abstract BaseComponent apply();
}
