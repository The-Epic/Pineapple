package sh.miles.pineapple.chat.node;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.StringUtils;
import sh.miles.pineapple.chat.token.Token;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class TagNode extends BaseNode {

    private final String namespace;
    private final Queue<String> arguments;
    private int fullChildTextLength;

    /**
     * Creates a new tag node
     *
     * @param parent the parent BaseNode
     * @param token  the token of this node
     * @param source the source string that this node is from
     */
    public TagNode(@NotNull final BaseNode parent, @NotNull final Token token, final @NotNull String source) {
        super(parent, token, source);
        this.arguments = new ArrayDeque<>(StringUtils.split(token.detail(source), ':', true));
        this.namespace = arguments.peek();
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Queue<String> getArguments() {
        return new ArrayDeque<>(this.arguments);
    }

    public int getFullChildTextLength() {
        return fullChildTextLength;
    }

    /**
     * Appends the given length of a child text to this TagNode
     *
     * @param length the length of the string to append
     */
    public void appendChildTextLength(int length) {
        this.fullChildTextLength += length;
        if (getParent() instanceof TagNode tagNode) {
            tagNode.appendChildTextLength(length);
        }
    }

    @Override
    protected StringBuilder asString(final @NotNull StringBuilder builder, final int depth) {
        String pad = pad(depth);
        builder.append("\n").append(pad).append("TagNode(%d, %s) {".formatted(this.fullChildTextLength, this.arguments));
        printChildren(builder, depth + 1);
        builder.append("\n").append(pad).append("}");
        return builder;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final TagNode tagNode)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.arguments.equals(tagNode.arguments);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(this.arguments);
        return result;
    }
}
