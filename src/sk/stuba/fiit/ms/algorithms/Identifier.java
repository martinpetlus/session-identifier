package sk.stuba.fiit.ms.algorithms;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public abstract class Identifier {

    public abstract void identify(final Search search);

    public abstract List<Session> getIdentifiedSessions();

    public void identifyAll(final List<Search> searches) {
        for (Search search : searches) {
            identify(search);
        }
    }

}
