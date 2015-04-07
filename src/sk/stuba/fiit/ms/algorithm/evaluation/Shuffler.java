package sk.stuba.fiit.ms.algorithm.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

public final class Shuffler {

    private final Random random = new Random();

    private static int countSearchResults(final List<Session> sessions) {
        int count = 0;

        for (Session session : sessions) {
            count += session.results();
        }

        return count;
    }

    private static List<List<SearchResult>> copySessionSearchResults(final List<Session> sessions) {
        List<List<SearchResult>> sessionSearchResults = new ArrayList<List<SearchResult>>();

        for (Session session : sessions) {
            sessionSearchResults.add(new ArrayList<SearchResult>(session.getAllSearchResults()));
        }

        return sessionSearchResults;
    }

    public List<SearchResult> shuffle(final List<Session> sessions) {
        final List<SearchResult> shuffled = new ArrayList<SearchResult>();

        final int numberOfSessions = sessions.size();

        final int numberOfSearchResults = countSearchResults(sessions);

        final List<List<SearchResult>> copy = copySessionSearchResults(sessions);

        for (int i = 0; i < numberOfSearchResults; i++) {
            List<SearchResult> searchResults = null;

            while (searchResults == null) {
                int randomIndex = random.nextInt(numberOfSessions);

                if (copy.get(randomIndex).size() > 0) {
                    searchResults = copy.get(randomIndex);
                }
            }

            shuffled.add(searchResults.get(0));

            searchResults.remove(0);
        }

        return shuffled;
    }

}
