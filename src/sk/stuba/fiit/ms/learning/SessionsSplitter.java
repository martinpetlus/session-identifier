package sk.stuba.fiit.ms.learning;

import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that splits session between training and testing.
 */
public final class SessionsSplitter {

    private List<Session> trainingSessions;

    private List<Session> testingSessions;

    private final double ratio;

    public SessionsSplitter(final double ratio) {;
        this.ratio = ratio;
    }

    public void splitSessions(final List<Session> sessions) {
        int numberOfTrainingSessions = (int) (sessions.size() * ratio);

        trainingSessions = new ArrayList<Session>(sessions.subList(0, numberOfTrainingSessions));
        testingSessions = new ArrayList<Session>(sessions.subList(numberOfTrainingSessions, sessions.size()));
    }

    public List<Session> getTrainingSessions() {
        return trainingSessions;
    }

    public List<Session> getTestingSessions() {
        return testingSessions;
    }

}
