package sh.miles.pineapple.chat.node;

import org.jetbrains.annotations.NotNull;
import sh.miles.pineapple.chat.parse.PineappleParserContext;
import sh.miles.pineapple.chat.token.Token;
import sh.miles.pineapple.chat.token.TokenType;
import sh.miles.pineapple.chat.token.Tokenizer;

public final class BaseNodeParser {

    private BaseNodeParser() {
        throw new UnsupportedOperationException("no");
    }

    public static BaseNode parseTree(@NotNull final String source, PineappleParserContext context) {
        final Tokenizer tokenizer = new Tokenizer(source);
        final BaseNode root = new BaseNode(null, null, source);
        BaseNode parent = root;
        Token token;
        while ((token = tokenizer.next()) != null) {
            BaseNode child;
            if (token.tokenType() == TokenType.OPEN || token.tokenType() == TokenType.CLOSE) {
                child = new TagNode(parent, token, source);
            } else if (token.tokenType() == TokenType.REPLACE) {
                child = new ReplaceNode(parent, token, source, context.getReplacement(token.detail(source)).toString());
            } else {
                child = new TextNode(parent, token, source);
            }

            if (child instanceof TextNode || child instanceof ReplaceNode) {
                parent.addChild(child);
            } else if (token.tokenType() == TokenType.OPEN) {
                parent.addChild(child);
                parent = child;
            } else {
                parent = findParentOfClosed((TagNode) parent, (TagNode) child);
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
