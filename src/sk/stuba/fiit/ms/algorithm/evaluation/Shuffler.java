package sk.stuba.fiit.ms.algorithm.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.stuba.fiit.ms.session.Search;
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

    private static List<List<Search>> copySessionSearchResults(final List<Session> sessions) {
        List<List<Search>> sessionSearchResults = new ArrayList<List<Search>>();

        for (Session session : sessions) {
            sessionSearchResults.add(new ArrayList<Search>(session.getAllSearches()));
        }

        return sessionSearchResults;
    }

    public List<Search> shuffle(final List<Session> sessions) {
        final List<Search> shuffled = new ArrayList<Search>();

        final int numberOfSessions = sessions.size();

        final int numberOfSearchResults = countSearchResults(sessions);

        final List<List<Search>> copy = copySessionSearchResults(sessions);

        for (int i = 0; i < numberOfSearchResults; i++) {
            List<Search> searches = null;

            while (searches == null) {
                int randomIndex = random.nextInt(numberOfSessions);

                if (copy.get(randomIndex).size() > 0) {
                    searches = copy.get(randomIndex);
                }
            }

            shuffled.add(searches.get(0));

            searches.remove(0);
        }

        return shuffled;
    }

}
