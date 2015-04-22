package sk.stuba.fiit.ms.learning.dataset.generation.example;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class NegativeGenerator extends Generator {

    private final SessionExtractor extractor;

    private final List<Session> allSessions;

    public NegativeGenerator(final SessionExtractor extractor, final Session session, final List<Session> allSessions) {
        super(session);

        this.extractor = extractor;
        this.allSessions = allSessions;
    }

    @Override
    public double[] generate(final int queries) {
        if (!generatable(queries)) {
            throw new IllegalArgumentException("Illegal number of queries: " + queries);
        }

        Search randomResult = null;

        while (randomResult == null) {
            Session randomSession = allSessions.get(random.nextInt(allSessions.size()));

            if (!session.equals(randomSession)) {
                randomResult = randomSession.getSearch(
                    random.nextInt(randomSession.getNumberOfSearches()));
            }
        }

        int[] indices = randomIndices(queries, session.getNumberOfSearches());

        List<Search> randomResults =
                new ArrayList<Search>(queries);

        for (int i = 0; i < queries; i++) {
            randomResults.add(session.getSearch(indices[i]));
        }

        return extractor.extractFeatures(new Session(randomResults), randomResult);
    }

    @Override
    public boolean generatable(final int queries) {
        if (queries > session.getNumberOfSearches()) {
            return false;
        }

        for (Session session : allSessions) {
            if (!this.session.equals(session)) {
                return true;
            }
        }

        return false;
    }

}
