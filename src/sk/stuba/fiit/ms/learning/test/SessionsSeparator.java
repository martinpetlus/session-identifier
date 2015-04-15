package sk.stuba.fiit.ms.learning.test;

import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public final class SessionsSeparator {

    private final List<Session> sessions;

    private double ratio;

    public SessionsSeparator(final List<Session> sessions) {
        this.sessions = sessions;
        this.ratio = 0.8;
    }

    public void setRatio(final double ratio) {
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
