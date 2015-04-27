package sk.stuba.fiit.ms.input;

import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public final class Sessions {

    private final List<Session> sessions;

    private final int minNumberOfSearches;

    public Sessions() {
        this(0);
    }

    public Sessions(final int minNumberOfSearches) {
        this.minNumberOfSearches = minNumberOfSearches;
        this.sessions = new ArrayList<Session>();
    }

    public boolean add(final Session session) {
        boolean valid = validate(session);

        if (valid) {
            sessions.add(session);
        } else {
            Logger.warn("Invalid session: " + session);
        }

        return valid;
    }

    public boolean validate(final Session session) {
        if (session.getNumberOfSearches() <= minNumberOfSearches) {
            return false;
        }

        return true;
    }

    public int size() {
        return sessions.size();
    }

    public List<Session> getSessions() {
        return new ArrayList<Session>(sessions);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + sessions.size() + "]";
    }

}
