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
        if (sessions.isEmpty()) {
            sessions.add(Session.newInstance(search));
            return;
        }

        Session lastSession = sessions.get(sessions.size() - 1);

        Search newestSearch = lastSession.getNewestSearch();

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
