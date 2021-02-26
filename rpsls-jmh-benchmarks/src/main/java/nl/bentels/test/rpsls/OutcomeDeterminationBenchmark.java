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
