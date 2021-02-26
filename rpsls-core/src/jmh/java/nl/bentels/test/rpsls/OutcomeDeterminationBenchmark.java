package nl.bentels.test.rpsls;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class OutcomeDeterminationBenchmark {

    @Test
    public void launchBenchmark() throws Exception {

        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                // You can be more specific if you'd like to run only one benchmark per test.
                .include(this.getClass().getName() + ".*")
                // Set the following options as needed
                .mode (Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(2)
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(2)
                .threads(2)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
                //.addProfiler(WinPerfAsmProfiler.class)
                .build();

        new Runner(opt).run();
    }



    @State(Scope.Thread)
    public static class GameAndEntities {
        public App game = new App();
        public App.GameEntity left, right;

        {
            List<App.GameEntity> temp = new ArrayList<>(App.entities);
            left = temp.get(ThreadLocalRandom.current().nextInt(temp.size()));
            right = temp.get(ThreadLocalRandom.current().nextInt(temp.size()));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchmarkTest(Blackhole blackhole, GameAndEntities gameAndEntities) {
        gameAndEntities.game.determineTheOutcome(gameAndEntities.left, gameAndEntities.right);
    }
}
