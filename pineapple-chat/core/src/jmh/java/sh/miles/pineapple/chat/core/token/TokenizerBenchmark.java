package sh.miles.pineapple.chat.core.token;

import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(value = 1, warmups = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class TokenizerBenchmark {

    @Benchmark
    public List<Token> manyTokens() {
        String manyTokens = "<yellow>random <gradient:red:blue:green><bold><obfuscated>traveler</gradient> <click:run_command:test command><underlined><red>click here</click><blue> to <rainbow><b>FEEL</rainbow> <obfuscated>it</obfuscated></blue> now things are going to get <rainbow>str<bold>a<italic>n<obfuscated>g</obfuscated></italic></bold>e</rainbow> <black>for now <gradient:#F0F00F:#0F0FF0>fairwell <bold><obfuscated>traveler</obfuscated></bold>";
        return getTokens(manyTokens);
    }

    @Benchmark
    public List<Token> fewTokens() {
        String fewTokens = "<yellow>Simplicity is the <italic>keynote</italic> of all <bold>true</bold> elegance.</yellow>";
        return getTokens(fewTokens);

    }

    @Benchmark
    public List<Token> noTokens() {
        String noTokens = "emptiness is a burden";
        return getTokens(noTokens);
    }

    @NotNull
    private List<Token> getTokens(final String manyTokens) {
        final List<Token> tokens = new ArrayList<>(20);
        final Tokenizer tokenizer = new Tokenizer(manyTokens);
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }
        return tokens;
    }
}
