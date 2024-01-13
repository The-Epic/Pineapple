package sh.miles.pineapple.chat.node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.chat.token.Token;
import sh.miles.pineapple.chat.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseNode {

    private final BaseNode parent;
    private final Token token;
    private final String source;
    private final List<BaseNode> children;

    /**
     * Creates a new BaseNode
     *
     * @param parent the parent node
     * @param token  the token this node derives from
     * @param source the source string
     */
    public BaseNode(@Nullable BaseNode parent, @Nullable Token token, @NotNull final String source) {
        this.parent = parent;
        this.token = token;
        this.source = source;
        this.children = new ArrayList<>();
    }

    public BaseNode getParent() {
        return parent;
    }

    public Token getToken() {
        return token;
    }

    public TokenType getTokenType() {
        return token.tokenType();
    }

    public String getSource() {
        return source;
    }

    public List<BaseNode> getChildren() {
        return children;
    }

    public void addChild(@NotNull final BaseNode child) {
        children.add(child);
    }

    protected StringBuilder asString(@NotNull final StringBuilder builder, int depth) {
        String pad = pad(depth);
        builder.append("\n").append(pad).append("BaseNode {");
        printChildren(builder, depth + 1);
        builder.append("\n").append(pad).append("}");
        return builder;
    }

    protected void printChildren(@NotNull final StringBuilder builder, final int depth) {
        if (!this.children.isEmpty()) {
            for (final BaseNode child : this.children) {
                child.asString(builder, depth + 1);
            }
        }
    }

    protected static String pad(int depth) {
        return " ".repeat(2 * depth);
    }

    @Override
    public String toString() {
        return asString(new StringBuilder(), 0).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final BaseNode baseNode)) {
            return false;
        }
        return Objects.equals(parent, baseNode.parent) && Objects.equals(token, baseNode.token) && Objects.equals(source, baseNode.source) && Objects.equals(children, baseNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, token, source, children);
    }
}
