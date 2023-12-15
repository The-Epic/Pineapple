package sh.miles.pineapple.token;

import org.jetbrains.annotations.NotNull;

public record Token(int start, int end, @NotNull TokenType type) {

    public String get(String string) {
        return string.substring(start, end);
    }
}
