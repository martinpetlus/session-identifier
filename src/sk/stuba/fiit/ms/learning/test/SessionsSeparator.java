package sk.stuba.fiit.ms.learning.test;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SessionsSeparator {

    private final List<Session> sessions;

    private List<Session> trainingSessions;

    private List<Session> testingSessions;

    private final double ratio;

    public SessionsSeparator(final List<Session> sessions, final double ratio) {
        this.sessions = sessions;
        this.ratio = ratio;
    }

    public void splitSessions() {
        int numberOfTrainingSessions = (int) (sessions.size() * ratio);

        trainingSessions = new ArrayList<Session>(sessions.subList(0, numberOfTrainingSessions));
        testingSessions = new ArrayList<Session>(sessions.subList(numberOfTrainingSessions, sessions.size()));
    }

    public void splitQueriesInTestingSessions() {
        List<Session> newTestingSessions = new ArrayList<Session>();

        Random random = new Random();

        for (Session session : testingSessions) {
            Session trainSession = copySession(session);
            Session testSession = copySession(session);

            List<Search> searches = session.getAllSearches();

            for (int i = 0; i < searches.size(); i++) {
                int randomIndex = random.nextInt(searches.size());

                trainSession.add(searches.get(randomIndex));
                searches.remove(randomIndex);

                if (searches.size() > 0) {
                    randomIndex = random.nextInt(searches.size());

                    testSession.add(searches.get(randomIndex));
                    searches.remove(randomIndex);
                }
            }

            trainingSessions.add(trainSession);
            newTestingSessions.add(testSession);
        }

        testingSessions = newTestingSessions;
    }

    private Session copySession(final Session session) {
        Session copy = new Session();

        copy.setTopic(session.getTopic());
        copy.setUserId(session.getUserId());

        return copy;
    }

    public List<Session> getTrainingSessions() {
        return trainingSessions;
    }

    public List<Session> getTestingSessions() {
        return testingSessions;
    }

}
