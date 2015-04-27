package sk.stuba.fiit.ms.input;

import sk.stuba.fiit.ms.session.Session;

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
        if (validate(session)) {
            sessions.add(session);
            return true;
        }

        return false;
    }

    public boolean validate(final Session session) {
        if (session.getNumberOfSearches() >= minNumberOfSearches) {
            return false;
        }

        return true;
    }

}
