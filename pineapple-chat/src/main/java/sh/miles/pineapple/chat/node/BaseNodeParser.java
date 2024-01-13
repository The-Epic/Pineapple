package sh.miles.pineapple.chat.node;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.ParserContext;
import sh.miles.pineapple.chat.token.Token;
import sh.miles.pineapple.chat.token.TokenType;
import sh.miles.pineapple.chat.token.Tokenizer;

public final class BaseNodeParser {

    private BaseNodeParser() {
        throw new UnsupportedOperationException("can not instantiate utility class");
    }

    /**
     * Parses a string into a BaseNodeTree with the given parser context
     *
     * @param string  the string
     * @param context the parser context
     * @return the new BaseNode tree
     */
    public static BaseNode parseTree(@NotNull final String string, ParserContext context) {
        final BaseNode root = new BaseNode(null, null, string);
        final Tokenizer tokenizer = new Tokenizer(string);
        BaseNode parent = root;
        BaseNode current = null;
        Token next;
        while ((next = tokenizer.next()) != null) {
            switch (next.tokenType()) {
                case OPEN, CLOSE -> current = new TagNode(parent, next, string);
                case REPLACE ->
                        current = new TextNode(parent, next, string, context.getReplacement(next.detail(string)).toString());
                case CONTENT -> current = new TextNode(parent, next, string);
                default -> throw new IllegalStateException("the next TokenType can not be determined! This is a bug!");
            }

            if (current instanceof TextNode) {
                parent.addChild(current);
            } else if (next.tokenType() == TokenType.OPEN) {
                parent.addChild(current);
                parent = current;
            } else {
                parent = findParentOfClosed((TagNode) parent, (TagNode) current);
            }
        }
        return root;
    }

    private static BaseNode findParentOfClosed(@NotNull TagNode parent, @NotNull final TagNode closing) {
        BaseNode current = parent;
        while (current != null) {
            if (current instanceof TagNode currentTag && currentTag.getNamespace().equals(closing.getNamespace())) {
                return current.getParent();
            }
            current = current.getParent();
        }

        return parent;
    }

}
