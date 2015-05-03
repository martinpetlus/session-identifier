package sk.stuba.fiit.ms.session.identifiers.stack;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public interface StackApproach {

    /**
     * Returns true if the search belongs to the session from the stack
     * @param session session to check if search belongs to
     * @param search search to check
     * @return true if search belongs to the session
     */
    boolean isMatch(final Session session, final Search search);

}
