package sh.miles.pineapple.token;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sh.miles.pineapple.util.TokenConstants;

public class Tokenizer {

    private final String string;
    private int cursor;

    public Tokenizer(@NotNull final String string) {
        this.string = string;
        this.cursor = 0;
    }

    @Nullable
    public Token next() {
        if (!hasNext()) {
            return null;
        }

        TokenType type = null;
        while (string.charAt(cursor) != TokenConstants.TAG_OPEN) {
            cursor++;
            if (cursor + 1 < string.length() && string.charAt(cursor + 1) != TokenConstants.CLOSE_DENOTE) {
                type = TokenType.CLOSE_TAG;
            } else {
                type = TokenType.OPEN_TAG;
            }
        }

        if (type == null) {
            return null;
        }

        int start = cursor;
        while (string.charAt(++cursor) != TokenConstants.TAG_CLOSE) ;

        int end = cursor;
        return new Token(start, end, type);
    }

    public boolean hasNext() {
        return this.string.contains(String.valueOf(TokenConstants.TAG_OPEN));
    }

    public int getCursor() {
        return cursor;
    }

    @NotNull
    public String getString() {
        return string;
    }
}
