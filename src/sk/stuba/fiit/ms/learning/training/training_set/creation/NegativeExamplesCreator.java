package sk.stuba.fiit.ms.learning.training.training_set.creation;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.features.extraction.SessionSearchExtractor;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.session.identifiers.stack.StackSessionIdentifier;

public final class NegativeExamplesCreator implements ExamplesCreator {

    private final SessionSearchExtractor extractor;

    public NegativeExamplesCreator(final SessionSearchExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public double[][] create(final List<Session> sessions, final int indexOfSession) {
        // No way to generate negative examples for the oldest session
        if (indexOfSession == 0) {
            return null;
        }

        // Current session
        Session session = sessions.get(indexOfSession);

        List<Search> searches = session.getAllSearches();

        // Sessions older than the current session
        List<Session> previousSessions = sessions.subList(0, indexOfSession);

        List<double[]> features = new ArrayList<double[]>();

        // Create negative examples for every query in current session
        outer:for (Search search : searches) {
            // Loop through previous sessions from the youngest to the oldest
            for (int i = previousSessions.size() - 1; i >= 0; i--) {
                Session previousSession = previousSessions.get(i);

                // Check if the previous sessions are not too old
                if ((session.getOldestSearch().getTimeStamp() - previousSession.getOldestSearch().getTimeStamp()) >
                        StackSessionIdentifier.MAX_OLD) {
                    continue outer;
                }

                // Filter out queries from session, that were issued after the query,
                // for which we are extracting negative examples
                Session filteredPreviousSession = filterOutSearches(previousSession, search);

                features.add(extractor.extractFeatures(filteredPreviousSession, search));
            }

        }

        return features.toArray(new double[features.size()][]);
    }

    private Session filterOutSearches(final Session session, final Search search) {
        Session filteredSession = new Session();

        for (Search s : session.getAllSearches()) {
            // Weird (negated) condition because of Session Track data set, where queries
            // doesn't contain time stamp, thus time stamp is by default zero and we want
            // to work with this data set too (i.e. do not filter out any query)
            if (!(s.getTimeStamp() > search.getTimeStamp())) {
                filteredSession.add(s);
            }
        }

        return filteredSession;
    }

}
