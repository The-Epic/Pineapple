package sh.miles.pineapple.chat.style;

import net.md_5.bungee.api.ChatColor;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(value = 1, warmups = 1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ChatColorBenchmark {

    @Benchmark
    public ChatColor bungeeChatOfMethod() {
        return ChatColor.of("#111000");
    }

    @Benchmark
    public ChatColor pineappleChatFromMethod() {
        return ChatColorUtils.from("#111000");
    }

}
