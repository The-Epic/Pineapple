package sh.miles.pineapple.chat.token;

public class Tokenizer {

    private final String string;
    private int cursor = 0;


    public Tokenizer(String string) {
        this.string = string;
    }

    public boolean hasNext() {
        return this.cursor < string.length();
    }

    public Token next() {
        boolean inQuotes = false;
        if (!hasNext()) {
            return null;
        }

        char at;
        int lastTokenEnd = cursor;
        int start = -1;
        for (; cursor < string.length(); cursor++) {
            at = string.charAt(cursor);
            if (at == TokenConstants.OPEN) {
                start = cursor;
                break;
            }
        }

        if (cursor == string.length()) {
            return new Token(lastTokenEnd, string.length(), TokenType.CONTENT);
        } else if (start > lastTokenEnd) {
            return new Token(lastTokenEnd, start, TokenType.CONTENT);
        }

        int end = -1;
        for (; cursor < string.length(); cursor++) {
            at = string.charAt(cursor);
            if (at == TokenConstants.QUOTE_ESCAPE) {
                inQuotes = !inQuotes;
            }else if (at == TokenConstants.CLOSE && !inQuotes) {
                end = cursor += 1;
                break;
            }
        }

        if (end == -1) {
            throw new IllegalStateException("the token that started a capture at index %d never ended. Capturing: %s".formatted(start, string.substring(start)));
        }

        TokenType tokenType;
        if (string.charAt(start + 1) == TokenConstants.CLOSE_DENOTE) {
            tokenType = TokenType.CLOSE;
        } else {
            tokenType = TokenType.OPEN;
        }

        return new Token(start, end, tokenType);
    }


}
