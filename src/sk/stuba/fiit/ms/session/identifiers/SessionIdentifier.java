package sk.stuba.fiit.ms.session.identifiers;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.Collections;
import java.util.List;

public abstract class SessionIdentifier {

    protected abstract void identify(final Search search);

    protected abstract void clear();

    public abstract List<Session> getIdentifiedSessions();

    public final void identify(final List<Search> searches) {
        // Sort queries from oldest to newest issued
        Collections.sort(searches, Search.OLDEST);

        // Clear previous identified sessions
        clear();

        // Identify sessions
        for (Search search : searches) {
            identify(search);
        }
    }

}
