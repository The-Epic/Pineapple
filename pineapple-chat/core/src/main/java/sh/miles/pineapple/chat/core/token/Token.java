package sh.miles.pineapple.chat.core.token;

import org.jetbrains.annotations.NotNull;

public record Token(int start, int end, @NotNull TokenType tokenType) {

    @NotNull
    public String detail(String source) {
        switch (tokenType) {
            case CONTENT -> {
                return source.substring(start, end);
            }
            case OPEN -> {
                return source.substring(start + 1, end - 1);
            }
            case REPLACE, CLOSE -> {
                return source.substring(start + 2, end - 1);
            }
            default -> throw new IllegalStateException("severe error occurred");
        }
    }

    public boolean isClosing() {
        return tokenType == TokenType.CLOSE;
    }
}
