package sk.stuba.fiit.ms.session.identifiers.stack;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public interface StackApproach {

    boolean isMatch(final Session session, final Search search);

}
