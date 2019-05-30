/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */

import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.internal.collection.Array;
import io.jenetics.internal.collection.ArrayISeq;
import io.jenetics.util.ISeq;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.function.Function;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.Objects.requireNonNull;

/**
 * Implementation of the Traveling Salesman Problem. This example tries to find
 * the shortest path, which visits all Austrian district capitals.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version 4.1
 * @since 3.6
 */
public final class Organizador
	implements Problem<ISeq<Materia>, EnumGene<Materia>, Integer>
{

	private final ISeq<Materia> _points;

	/**
	 * Create a new TSP instance with the way-points we want to visit.
	 *
	 * @param points the way-points we want to visit
	 * @throws NullPointerException if the given {@code points} seq is {@code null}
	 */
	public Organizador(final ISeq<Materia> points) {
		_points = requireNonNull(points);
	}

	@Override
	public Codec<ISeq<Materia>, EnumGene<Materia>> codec() {
		return Codecs.ofSubSet(_points,3);
	}

	@Override
	public Function<ISeq<Materia>, Integer> fitness() {
		return horario ->{
			int a = 1;
			for (Materia mat: horario
				 ) {
				a += mat.choques(horario);
			}
			return 0-a;
		};
	}

	public static void main(String[] args) throws IOException {
		final Organizador tsm =
			new Organizador(grupos());

		final Engine<EnumGene<Materia>, Integer> engine = Engine.builder(tsm)
			.optimize(Optimize.MINIMUM)
			.alterers(
				new SwapMutator<>(0.15),
				new PartiallyMatchedCrossover<>(0.15))
			.build();

		// Create evolution statistics consumer.
		final EvolutionStatistics<Integer, ?>
			statistics = EvolutionStatistics.ofNumber();

		final Phenotype<EnumGene<Materia>, Integer> best = engine.stream()
			.limit(1_000)
			.peek(statistics)
			.collect(toBestPhenotype());



		System.out.println(statistics);
		System.out.println(best.getFitness());
		best.getGenotype().getChromosome().stream().forEach(a->System.out.println(a));
	}

	// Return the district capitals, we want to visit.
	private static ISeq<Materia> grupos() throws IOException {
		ISeq<Materia> materias = new ArrayISeq<>( Array.ofLength(0));
		ArrayList<Periodo> periodos = new ArrayList<>();
		periodos.add(new Periodo("lunes",new Time(1110000), new Time(1200000)));
		periodos.add(new Periodo("martes",new Time(1110000), new Time(120000)));
		periodos.add(new Periodo("viernes",new Time(1110000), new Time(1200000)));
		materias = materias.append(new Materia(periodos));
		periodos = new ArrayList<>();
		periodos.add(new Periodo("lunes",new Time(1050000), new Time(1100000)));
		periodos.add(new Periodo("martes",new Time(1050000), new Time(1100000)));
		periodos.add(new Periodo("viernes",new Time(1050000), new Time(1100000)));
		materias = materias.append(new Materia(periodos));
		periodos = new ArrayList<>();
		periodos.add(new Periodo("lunes",new Time(1110000), new Time(1200000)));
		periodos.add(new Periodo("martes",new Time(1110000), new Time(1200000)));
		periodos.add(new Periodo("viernes",new Time(1110000), new Time(1200000)));
		materias = materias.append(new Materia(periodos));
		periodos = new ArrayList<>();
		periodos.add(new Periodo("lunes",new Time(1210000), new Time(1300000)));
		periodos.add(new Periodo("martes",new Time(1210000), new Time(1300000)));
		periodos.add(new Periodo("viernes",new Time(1210000), new Time(1300000)));
		materias = materias.append(new Materia(periodos));
		periodos = new ArrayList<>();

		periodos.add(new Periodo("lunes",new Time(1210000), new Time(1300000)));
		periodos.add(new Periodo("martes",new Time(1210000), new Time(1300000)));
		periodos.add(new Periodo("viernes",new Time(1210000), new Time(1300000)));
		materias = materias.append(new Materia(periodos));
		return materias;
	}

}
