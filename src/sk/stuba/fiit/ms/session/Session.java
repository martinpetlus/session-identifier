package sk.stuba.fiit.ms.session;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Session {

    private int userId;

    private final List<Search> searches;

    private Intent intent;

    /**
     * Comparator used for sorting sessions from the oldest to the newest by their oldest search.
     */
    public static final Comparator<Session> OLDEST = new Comparator<Session>() {

        @Override
        public final int compare(final Session session1, final Session session2) {
            return Search.OLDEST.compare(session1.getOldestSearch(), session2.getOldestSearch());
        }

    };

    /**
     * Creates new empty session.
     */
    public Session() {
        this(null);
    }

    /**
     * Creates new session with specified searches in it.
     * @param searches searches to be present in the session
     */
    public Session(final List<Search> searches) {
        this.searches = new ArrayList<Search>();

        if (searches != null) {
            for (Search search : searches) {
                this.add(search);
            }
        }

        this.userId = -1;
    }

    /**
     * Sets the user id of this session.
     * @param userId user id to set
     */
    public void setUserId(final int userId) {
        this.userId = userId;
    }

    /**
     * Returns the user id of this session.
     * @return user id of this session
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns intent of the session.
     * @return intent of the session
     */
    public Intent getIntent() {
        return intent;
    }

    /**
     * Sets intent of the session.
     * @param intent intent to set
     */
    public void setIntent(final Intent intent) {
        this.intent = intent;
    }

    /**
     * Add search to the session. Searches are ordered by the time stamp their were issued.
     * Search have to contain results to be added.
     * @param search search to add to this session
     * @return true if search has been added
     */
    public void add(final Search search) {
        int i = 0;

        for (; i < searches.size(); i++) {
            if (searches.get(i).getTimeStamp() > search.getTimeStamp()) {
                break;
            }
        }

        searches.add(i, search);
    }

    /**
     * Returns the newest search from session or null if empty.
     * @return newest search
     */
    public Search getNewestSearch() {
        if (searches.isEmpty()) {
            return null;
        } else {
            return searches.get(searches.size() - 1);
        }
    }

    /**
     * Returns the oldest search from session or null if empty.
     * @return oldest search
     */
    public Search getOldestSearch() {
        if (searches.isEmpty()) {
            return null;
        } else {
            return searches.get(0);
        }
    }

    /**
     * Returns copy of a list of all searches in this session.
     * @return copy of session searches
     */
    public List<Search> getAllSearches() {
        return new ArrayList<Search>(searches);
    }

    /**
     * Return list of all results of searches in this session.
     * @return results of all searches in this session
     */
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<Result>();

        for (Search search : searches) {
            results.addAll(search.getResults());
        }

        return results;
    }

    /**
     * Return number of searches in this session.
     * @return number of searches
     */
    public int getNumberOfSearches() {
        return searches.size();
    }

    /**
     * Return true if the session is empty, otherwise false.
     * @return true if session is empty
     */
    public boolean isEmpty() {
        return searches.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName()).append('[');

        sb.append("intent=").append(intent);

        sb.append(" user_id=").append(userId);

        sb.append(" searches=").append(searches.size());

        return sb.append(']').toString();
    }

    /**
     * Creates new instance of session with specified searches in it.
     * @param searches list of searches to be present in new session
     * @return new session with specified searches in it
     */
    public static Session newInstance(final Search... searches) {
        Session session = new Session();

        for (Search search : searches) {
            session.add(search);
        }

        return session;
    }

    /**
     * Returns collected searches present int given session.
     * @param sessions sessions to collect searches from
     * @return list of searches
     */
    public static List<Search> collectSearches(final List<Session> sessions) {
        List<Search> searches = new ArrayList<Search>();

        for (Session session : sessions) {
            searches.addAll(session.getAllSearches());
        }

        return searches;
    }

}
