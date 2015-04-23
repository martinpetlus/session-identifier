package sk.stuba.fiit.ms.session.identifiers;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public abstract class SessionIdentifier {

    public abstract void identify(final Search search);

    public abstract List<Session> getIdentifiedSessions();

    public final void identifyAll(final List<Search> searches) {
        for (Search search : searches) {
            identify(search);
        }
    }

}
