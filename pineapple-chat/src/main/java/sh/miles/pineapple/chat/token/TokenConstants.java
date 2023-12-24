package sh.miles.pineapple.chat.token;

final class TokenConstants {

    static final char OPEN = '<';
    static final char CLOSE = '>';
    static final char CLOSE_DENOTE = '/';
    static final char ESCAPE = '\\';
    static final char QUOTE_ESCAPE = '\'';

    private TokenConstants() {
        throw new UnsupportedOperationException("no");
    }

}
