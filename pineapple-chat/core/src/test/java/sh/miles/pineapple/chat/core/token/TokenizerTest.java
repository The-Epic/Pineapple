package sh.miles.pineapple.chat.core.token;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {


    @Test
    void should_Tokenize_Correctly() {
        final String test = "<token0>Hello</token0> <token1>World</token1> and <$name>";
        final Tokenizer tokenizer = new Tokenizer(test);
        final List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(0, 8, TokenType.OPEN));
        tokens.add(new Token(8, 13, TokenType.CONTENT));
        tokens.add(new Token(13, 22, TokenType.CLOSE));
        tokens.add(new Token(22, 23, TokenType.CONTENT));
        tokens.add(new Token(23, 31, TokenType.OPEN));
        tokens.add(new Token(31, 36, TokenType.CONTENT));
        tokens.add(new Token(36, 45, TokenType.CLOSE));
        tokens.add(new Token(45, 50, TokenType.CONTENT));
        tokens.add(new Token(50, 57, TokenType.REPLACE));
        int index = 0;
        while (tokenizer.hasNext()) {
            assertEquals(tokens.get(index), tokenizer.next());
            index++;
        }
    }
}
