package sk.stuba.fiit.ms.learning.training.training_set.creation;

import java.util.List;

import sk.stuba.fiit.ms.features.extraction.SessionSearchExtractor;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

/**
 * Creates positive training examples.
 */
public final class PositiveExamplesCreator implements ExamplesCreator {

    private final SessionSearchExtractor extractor;

    public PositiveExamplesCreator(final SessionSearchExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public double[][] create(final List<Session> sessions, final int indexOfSession) {
        Session session = sessions.get(indexOfSession);

        // No way to create positive examples for sessions with only one query
        if (session.getNumberOfSearches() == 1) {
            return null;
        }

        List<Search> searches = session.getAllSearches();

        double[][] features = new double[searches.size() - 1][];

        // Extract features from 1 to (searches.size - 1) first queries of session and consecutive query
        for (int i = 1; i < searches.size(); i++) {
            features[i - 1] = extractor.extractFeatures(new Session(searches.subList(0, i)), searches.get(i));
        }

        return features;
    }

}
