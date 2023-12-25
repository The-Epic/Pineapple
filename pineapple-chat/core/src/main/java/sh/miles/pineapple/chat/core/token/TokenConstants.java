package sh.miles.pineapple.chat.core.token;

final class TokenConstants {

    static final char OPEN = '<';
    static final char CLOSE = '>';
    static final char CLOSE_DENOTE = '/';
    static final char ESCAPE = '\\';
    static final char QUOTE_ESCAPE = '\'';
    static final char REPLACE_DENOTE = '$';

    private TokenConstants() {
        throw new UnsupportedOperationException("no");
    }

}
