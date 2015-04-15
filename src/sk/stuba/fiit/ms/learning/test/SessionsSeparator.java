package sk.stuba.fiit.ms.learning.test;

import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public final class SessionsSeparator {

    private final List<Session> sessions;

    private final double ratio;

    public SessionsSeparator(final List<Session> sessions, final double ratio) {
        this.sessions = sessions;
        this.ratio = ratio;
    }

    public List<Session> getTrainingSessions() {
        return sessions.subList(0, getNumberOfTrainingSessions());
    }

    public List<Session> getTestingSessions() {
        return sessions.subList(getNumberOfTrainingSessions(), sessions.size());
    }

    private int getNumberOfTrainingSessions() {
        return (int) (sessions.size() * ratio);
    }

}
