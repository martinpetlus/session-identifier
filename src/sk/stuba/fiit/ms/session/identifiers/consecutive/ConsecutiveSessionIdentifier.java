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

    private Session addNewSession(final Search search) {
        Session session = new Session();

        session.add(search);

        sessions.add(session);

        return session;
    }

    @Override
    protected void identify(final Search search) {
        if (sessions.isEmpty()) {
            addNewSession(search);
            return;
        }

        Session lastSession = sessions.get(sessions.size() - 1);

        Search newestSearch = lastSession.getNewestSearch();

        if (consecutiveApproach.isSameSession(newestSearch, search)) {
            lastSession.add(search);
        } else {
            addNewSession(search);
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
