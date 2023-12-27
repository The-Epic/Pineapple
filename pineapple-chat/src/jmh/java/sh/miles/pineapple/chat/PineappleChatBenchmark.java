package sh.miles.pineapple.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import sh.miles.pineapple.chat.token.Token;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PineappleChatBenchmark {

    @Benchmark
    public BaseComponent testNiceMix() {
        Map<String, Object> replacements = Map.of("name", "JeryTheCarry");
        final String input = "<yellow>random <gradient:red:blue><bold><$name></gradient></bold> <click:run_command:/weather clear><underlined><red>click here</click><blue> to <bold>FEEL</underlined> it";
        return PineappleChat.parse(input, replacements);
    }

    @Benchmark
    public BaseComponent testFew() {
        String fewTokens = "<yellow>Simplicity is the <italic>keynote</italic> of all <bold>true</bold> elegance.</yellow>";
        return PineappleChat.parse(fewTokens);
    }


    @Benchmark
    public BaseComponent testEmpty() {
        String noTokens = "emptiness is a burden";
        return PineappleChat.parse(noTokens);
    }

}
