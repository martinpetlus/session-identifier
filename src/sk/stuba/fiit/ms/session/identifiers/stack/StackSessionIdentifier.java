package sk.stuba.fiit.ms.session.identifiers.stack;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class StackSessionIdentifier extends SessionIdentifier {

    /**
     * Used ina algorithm to prevent joining queries into too old previous sessions.
     * This value is set to 1 day.
     * milliseconds per day: 24 hours * 60 minutes * 60 seconds * 1000 milliseconds
     */
    public static final long MAX_OLD = 86_400_000L;

    private final StackApproach stackApproach;

    private final List<Session> stack;

    /**
     * Constructs instance of stack session identifier with given stack approach.
     * @param stackApproach stack approach to be used in this identifier
     */
    public StackSessionIdentifier(final StackApproach stackApproach) {
        this.stackApproach = stackApproach;
        this.stack = new ArrayList<Session>();
    }

    /**
     * Identifies search into session using stack session algorithm with given approach.
     * @param search search to be identified into session
     */
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

    /**
     * Clears already identified sessions.
     */
    @Override
    protected void clear() {
        stack.clear();
    }

    /**
     * Returns identified sessions.
     * @return identified sessions
     */
    @Override
    public List<Session> getIdentifiedSessions() {
        return new ArrayList<Session>(stack);
    }

}
