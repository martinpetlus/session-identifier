package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;

public final class ConsecutiveSessionIdentifier extends SessionIdentifier {

    private final ConsecutiveApproach consecutiveApproach;

    private final List<Session> sessions;

    public ConsecutiveSessionIdentifier(final ConsecutiveApproach consecutiveApproach) {
        this.consecutiveApproach = consecutiveApproach;
        this.sessions = new ArrayList<Session>();
    }

    @Override
    protected void identify(final Search search) {
        // If there are no identified sessions yet
        if (sessions.isEmpty()) {
            sessions.add(Session.newInstance(search));
            return;
        }

        // Get the most recent session
        Session lastSession = sessions.get(sessions.size() - 1);

        // Get the newest query from the most recent session
        Search newestSearch = lastSession.getNewestSearch();

        // Is our query part of the most recent session?
        if (consecutiveApproach.isSameSession(newestSearch, search)) {
            lastSession.add(search);
        } else {
            sessions.add(Session.newInstance(search));
        }
    }

    @Override
    protected void clear() {
        sessions.clear();
    }

    @Override
    public List<Session> getIdentifiedSessions() {
        return new ArrayList<Session>(sessions);
    }

}
