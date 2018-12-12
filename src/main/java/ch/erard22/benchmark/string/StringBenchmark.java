package ch.erard22.benchmark.string;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput)
@Measurement(batchSize = 1000, iterations = 10, time = 10)
@Warmup(batchSize = 1000, iterations = 5)
@Fork(5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringBenchmark {

    @State(Scope.Thread)
    public static class RunState {
        public String s1;
        public String s2;
        public String s3;

        @Setup(Level.Invocation)
        public void init() {
            s1 = RandomStringUtils.random(10, true, true);
            s2 = RandomStringUtils.random(10, true, true);
            s3 = RandomStringUtils.random(10, true, true);
        }
    }

    @Benchmark
    public StringBuilder stringBuilder(RunState state) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(state.s1);
        stringBuilder.append(state.s2);
        stringBuilder.append(state.s3);

        return stringBuilder;
    }

    @Benchmark
    public StringBuffer stringBuffer(RunState state) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(state.s1);
        stringBuffer.append(state.s2);
        stringBuffer.append(state.s3);
        return stringBuffer;
    }

    @Benchmark
    public String stringPlus(RunState state) {
        String string = state.s1 + state.s2 + state.s3;
        return string;
    }

    @Benchmark
    public String stringConcat(RunState state) {
        String string = state.s1.concat(state.s2).concat(state.s3);
        return string;
    }

    @Benchmark
    public String stringFormat(RunState state) {
        String string = String.format("%s%s%s", state.s1, state.s2, state.s3);
        return string;
    }

    public static void main(String[] args) throws RunnerException {

        Options options = new OptionsBuilder()
                .include(StringBenchmark.class.getSimpleName())
                .threads(1).forks(1).shouldFailOnError(true).shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
