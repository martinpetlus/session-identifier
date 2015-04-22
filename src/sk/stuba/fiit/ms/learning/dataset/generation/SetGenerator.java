package sk.stuba.fiit.ms.learning.dataset.generation;

import java.util.List;

import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.learning.DataSet;
import sk.stuba.fiit.ms.learning.dataset.generation.example.Generator;
import sk.stuba.fiit.ms.learning.dataset.generation.example.NegativeGenerator;
import sk.stuba.fiit.ms.learning.dataset.generation.example.PositiveGenerator;
import sk.stuba.fiit.ms.session.Session;

public final class SetGenerator {

    private final SessionExtractor extractor;

    private final int minQueries;

    private final int maxQueries;

    public SetGenerator(final SessionExtractor extractor, int minQueries, int maxQueries) {
        this.minQueries = minQueries;
        this.maxQueries = maxQueries;
        this.extractor = extractor;
    }

    public DataSet generateSet(final List<Session> sessions) {
        final DataSet set = new DataSet();

        for (Session session : sessions) {
            for (int queries = minQueries; queries <= maxQueries; queries++) {
                Generator generator = new PositiveGenerator(extractor, session);

                if (generator.canGenerate(queries)) {
                    set.addPositiveExamples(generator.generate(queries));
                }

                generator = new NegativeGenerator(extractor, session, sessions);

                if (generator.canGenerate(queries)) {
                    set.addNegativeExamples(generator.generate(queries));
                }
            }
        }

        return set;
    }

}
