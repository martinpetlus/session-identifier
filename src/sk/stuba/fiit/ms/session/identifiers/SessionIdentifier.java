package sk.stuba.fiit.ms.session.identifiers;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class to be extended by various session identifiers.
 */
public abstract class SessionIdentifier {

    /**
     * Method to be implemented by subclass to identify search into session.
     * @param search search to be identified into session
     */
    protected abstract void identify(final Search search);

    /**
     * Method to be implemented by subclass to clear previous identified
     * sessions before new identification of sessions.
     */
    protected abstract void clear();

    /**
     * Returns identified sessions.
     * @return identified sessions
     */
    public abstract List<Session> getIdentifiedSessions();

    /**
     * Identifies sessions from given list of searches.
     * @param searches searches to identify sessions from
     */
    public final void identify(final List<Search> searches) {
        // Create copy of the list for sorting purpose
        List<Search> sortedSearches = new ArrayList<Search>(searches);

        // Sort queries from oldest to newest issued
        Collections.sort(sortedSearches, Search.OLDEST);

        // Clear previous identified sessions
        clear();

        // Identify sessions
        for (Search search : sortedSearches) {
            identify(search);
        }
    }

}
