package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;

public final class ConsecutiveSessionIdentifier extends SessionIdentifier {

    private final List<Session> sessions;

    private final ConsecutiveApproach consecutiveApproach;

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
    public void identify(Search search) {
        if (sessions.isEmpty()) {
            addNewSession(search);
            return;
        }

        Session lastSession = sessions.get(sessions.size() - 1);

        Search lastSearch = lastSession.getLastSearch();

        if (consecutiveApproach.isSameSession(lastSearch, search)) {
            lastSession.add(search);
        } else {
            addNewSession(search);
        }
    }

    @Override
    public List<Session> getIdentifiedSessions() {
        return new ArrayList<Session>(sessions);
    }

}
