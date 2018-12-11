package ch.erard22.benchmark.string;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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

@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringBenchmark {

    private static class RunState {
        public volatile String s1;
        public volatile String s2;
        public volatile String s3;
    }

    @Setup(Level.Invocation)
    public void init() {
        RunState.s1 = RandomStringUtils.random(10, true, true);
        s2 = RandomStringUtils.random(10, true, true);
        s3 = RandomStringUtils.random(10, true, true);
    }

    @Benchmark
    @Warmup(iterations = 3, batchSize = 1)
    @Measurement(iterations = 5, batchSize = 1000)
    @BenchmarkMode(Mode.Throughput)
    public StringBuilder stringBuilder(StringBenchmark state) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(state.s1);
        stringBuilder.append(state.s2);
        stringBuilder.append(state.s3);

        return stringBuilder;
    }

    @Benchmark
    @Warmup(iterations = 3, batchSize = 1)
    @Measurement(iterations = 5, batchSize = 1000)
    @BenchmarkMode(Mode.Throughput)
    public StringBuffer stringBuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(state.s1);
        stringBuffer.append(state.s2);
        stringBuffer.append(state.s3);
        return stringBuffer;
    }

    @Benchmark
    @Warmup(iterations = 3, batchSize = 1)
    @Measurement(iterations = 5, batchSize = 1000)
    @BenchmarkMode(Mode.Throughput)
    public String stringPlus() {
        String string = state.s1 + state.s2 + state.s3;
        return string;
    }

    @Benchmark
    @Warmup(iterations = 3, batchSize = 1)
    @Measurement(iterations = 5, batchSize = 1000)
    @BenchmarkMode(Mode.Throughput)
    public String stringConcat() {
        String string = state.s1.concat(state.s2).concat(state.s3);
        return string;
    }

    @Benchmark
    @Warmup(iterations = 3, batchSize = 1)
    @Measurement(iterations = 5, batchSize = 1000)
    @BenchmarkMode(Mode.Throughput)
    public String stringFormat() {
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
