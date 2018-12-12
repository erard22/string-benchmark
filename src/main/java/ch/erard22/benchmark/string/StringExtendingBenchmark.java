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

@BenchmarkMode(Mode.AverageTime)
@Measurement(batchSize = 1000, iterations = 10)
@Warmup(batchSize = 1000, iterations = 5)
@Fork(5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StringExtendingBenchmark {

    @State(Scope.Thread)
    public static class IterationState {
        public StringBuffer stringBuffer;
        public StringBuilder stringBuilder;
        public String concatString;
        public String plusString;

        @Setup(Level.Iteration)
        public void init() {
            stringBuffer = new StringBuffer();
            stringBuilder = new StringBuilder();
            concatString = "";
            plusString = "";
        }
    }

    @State(Scope.Thread)
    public static class RunState {
        public String newString;

        @Setup(Level.Invocation)
        public void init() {
            newString = RandomStringUtils.random(10, true, true);
        }
    }

    @Benchmark
    public StringBuilder stringBuilder(RunState runState, IterationState iterationState) {
        return iterationState.stringBuilder.append(runState.newString);
    }

    @Benchmark
    public StringBuffer stringBuffer(RunState runState, IterationState iterationState) {
        return iterationState.stringBuffer.append(runState.newString);
    }

    @Benchmark
    public String stringPlus(RunState runState, IterationState iterationState) {
        return iterationState.plusString += runState.newString;
    }

    @Benchmark
    public String stringConcat(RunState runState, IterationState iterationState) {
        return iterationState.concatString.concat(runState.newString);
    }

    public static void main(String[] args) throws RunnerException {

        Options options = new OptionsBuilder()
                .include(StringExtendingBenchmark.class.getSimpleName())
                .threads(1).forks(1).shouldFailOnError(true).shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
