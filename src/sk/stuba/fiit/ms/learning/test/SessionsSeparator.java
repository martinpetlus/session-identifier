package sk.stuba.fiit.ms.learning.test;

import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;

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

    public List<Session> getTrainingSessions() {
        return trainingSessions;
    }

    public List<Session> getTestingSessions() {
        return testingSessions;
    }

}
