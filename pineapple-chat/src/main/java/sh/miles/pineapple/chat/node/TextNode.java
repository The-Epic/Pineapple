
package sh.miles.pineapple.chat.node;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.token.Token;

import java.util.Objects;

public class TextNode extends BaseNode {

    private final String text;

    /**
     * Creates a new TextNode
     *
     * @param parent the parent node
     * @param token  the token of this node
     * @param source the original source text this node is from
     * @param text   the text this node contains
     */
    public TextNode(@NotNull final BaseNode parent, @NotNull final Token token, @NotNull final String source, @NotNull final String text) {
        super(parent, token, source);
        this.text = text;
        if (parent instanceof TagNode tagNode) {
            tagNode.appendChildTextLength(this.text.length());
        }
    }

    public TextNode(@NotNull final BaseNode parent, @NotNull final Token token, final @NotNull String source) {
        this(parent, token, source, token.detail(source));
    }

    public String getText() {
        return text;
    }

    @Override
    protected StringBuilder asString(final @NotNull StringBuilder builder, final int depth) {
        String pad = pad(depth);
        builder.append("\n").append(pad).append("TextNode(\"%s\")".formatted(this.text));
        printChildren(builder, depth + 1);
        return builder;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final TextNode textNode)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(text, textNode.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }
}
