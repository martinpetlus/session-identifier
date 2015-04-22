package sk.stuba.fiit.ms.learning.dataset.generation.example;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class PositiveGenerator extends Generator {

    private final SessionExtractor extractor;

    private List<Search> randomResults;

    private int randomIndex;

    public PositiveGenerator(final SessionExtractor extractor, final Session session) {
        super(session);

        this.extractor = extractor;
    }

    private void prepare(final int queries) {
        randomResults = new ArrayList<Search>(queries);

        randomIndex = random.nextInt(session.getNumberOfSearches());

        int[] randomIndices = randomIndices(queries, session.getNumberOfSearches(), randomIndex);

        for (int i = 0; i < queries; i++) {
            randomResults.add(session.getSearch(randomIndices[i]));
        }
    }

    @Override
    public double[] generate(final int queries) {
        if (!generatable(queries)) {
            throw new IllegalArgumentException("Illegal number of queries: " + queries);
        }

        prepare(queries);

        Session session = new Session(this.randomResults);
        Search result = this.session.getSearch(this.randomIndex);

        return extractor.extractFeatures(session, result);
    }

    @Override
    public boolean generatable(final int queries) {
        return session.getNumberOfSearches() > queries;
    }

}
