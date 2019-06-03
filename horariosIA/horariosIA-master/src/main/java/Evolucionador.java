import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;

import java.io.IOException;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;

public class Evolucionador {
    static  EvolutionStatistics<Integer, ?>  statistics;
    public static Phenotype<EnumGene<Grupo>, Integer> evolucionar(int numMaterias) throws IOException {
        final Organizador tsm =
                new Organizador(Organizador.grupos());
        tsm.setNumMaterias(numMaterias);
        final Engine<EnumGene<Grupo>, Integer> engine = Engine.builder(tsm)
                .optimize(Optimize.MINIMUM)
                .alterers(
                        new SwapMutator<>(0.15),
                        new PartiallyMatchedCrossover<>(0.15))
                .build();

        // Create evolution statistics consumer.
        statistics = EvolutionStatistics.ofNumber();

        final Phenotype<EnumGene<Grupo>, Integer> best = engine.stream()
                .limit(1_000)
                .peek(statistics)
                .collect(toBestPhenotype());
        System.out.println(statistics);
        return best;
    }
}
