package sk.stuba.fiit.ms.session.identifiers.stack;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class StackSessionIdentifier extends SessionIdentifier {

    // Milliseconds per day: 24 hours * 60 minutes * 60 seconds * 1000 milliseconds
    public static final long MAX_OLD = 86_400_000L;

    private final StackApproach stackApproach;

    private final List<Session> stack;

    public StackSessionIdentifier(final StackApproach stackApproach) {
        this.stackApproach = stackApproach;
        this.stack = new ArrayList<Session>();
    }

    @Override
    protected  void identify(final Search search) {
        // If the stack has no sessions yet
        if (stack.isEmpty()) {
            stack.add(Session.newInstance(search));
            return;
        }

        // Traverse stack from the top to its bottom (from the most recent session)
        for (int i = stack.size() - 1; i >= 0; i--) {
            Session session = stack.get(i);

            // Check if the session isn't too old for our query
            if ((search.getTimeStamp() - session.getOldestSearch().getTimeStamp()) > MAX_OLD) {
                break;
            }

            // Is there significant match between session and search?
            if (stackApproach.isMatch(session, search)) {
                session.add(search);

                // Add session to the top of the stack, as the most recent session
                stack.remove(i);
                stack.add(session);

                return;
            }
        }

        // Add new session to the top of the stack containing our query
        stack.add(Session.newInstance(search));
    }

    @Override
    protected void clear() {
        stack.clear();
    }

    @Override
    public List<Session> getIdentifiedSessions() {
        return new ArrayList<Session>(stack);
    }

}
