package sh.miles.pineapple.chat.tag;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.tag.base.TextSupplierTag;

import java.util.Queue;

public class TranslatableTag extends TextSupplierTag {

    private final String translation;

    protected TranslatableTag(@NotNull final Queue<String> arguments, final int childTextLength) {
        super(arguments, childTextLength);
        arguments.poll();
        this.translation = arguments.poll();
    }

    @Override
    protected BaseComponent apply() {
        return new TranslatableComponent(translation);
    }
}
