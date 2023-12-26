package sh.miles.pineapple.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PineappleChatBenchmark {

    @Benchmark
    public BaseComponent testNiceMix() {
        Map<String, Object> replacements = Map.of("name", "JeryTheCarry");
        final String input = "<yellow>random <gradient:red:blue><bold><$name></gradient></bold> <click:run_command:/weather clear><underline><red>click here</click><blue> to <bold>FEEL</underline> it";
        return PineappleChat.parse(input, replacements);
    }

}
