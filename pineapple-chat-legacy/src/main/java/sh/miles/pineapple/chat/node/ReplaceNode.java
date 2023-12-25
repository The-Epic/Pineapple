package sh.miles.pineapple.chat.node;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.token.Token;

public class ReplaceNode extends TextNode {

    public ReplaceNode(@NotNull final BaseNode parent, @NotNull final Token token, final @NotNull String source, @NotNull final String text) {
        super(parent, token, source, text);
    }

    @Override
    protected StringBuilder asString(final @NotNull StringBuilder builder, final int depth) {
        String pad = pad(depth);
        builder.append("\n").append(pad).append("ReplaceNode(\"%s\")".formatted(this.getText()));
        printChildren(builder, depth + 1);
        return builder;
    }

}
