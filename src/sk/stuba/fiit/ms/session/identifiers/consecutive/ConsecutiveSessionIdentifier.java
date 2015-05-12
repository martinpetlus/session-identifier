package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Identifies sessions in consecutive fashion with given consecutive approach.
 */
public final class ConsecutiveSessionIdentifier extends SessionIdentifier {

    private final ConsecutiveApproach consecutiveApproach;

    private final List<Session> sessions;

    /**
     * Creates instance of this consecutive identifier with given consecutive approach.
     * @param consecutiveApproach consecutive approach to be used in identification
     */
    public ConsecutiveSessionIdentifier(final ConsecutiveApproach consecutiveApproach) {
        this.consecutiveApproach = consecutiveApproach;
        this.sessions = new ArrayList<Session>();
    }

    /**
     * Identifies search into session using this identifier and consecutive approach.
     * @param search search to be identified into session
     */
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

    /**
     * Clear identfied sessions.
     */
    @Override
    protected void clear() {
        sessions.clear();
    }

    /**
     * Returns identified sessions.
     * @return identified sessions
     */
    @Override
    public List<Session> getIdentifiedSessions() {
        return new ArrayList<Session>(sessions);
    }

}
