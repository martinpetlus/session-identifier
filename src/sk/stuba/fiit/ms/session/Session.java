package sk.stuba.fiit.ms.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Session {

    private static int numberOfInstances = 0;

    private int userId;

    private final int id;

    private final List<Search> searches;

    private Intent intent;

    public static final Comparator<Session> OLDEST = new Comparator<Session>() {

        @Override
        public final int compare(final Session session1, final Session session2) {
            return Search.OLDEST.compare(session1.getOldestSearch(), session2.getOldestSearch());
        }

    };

    public Session() {
        this(null);
    }

    public Session(final List<Search> searches) {
        this.id = ++numberOfInstances;

        this.searches = new ArrayList<Search>();

        if (searches != null) {
            for (Search search : searches) {
                this.add(search);
            }
        }

        this.userId = -1;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(final Intent intent) {
        this.intent = intent;
    }

    public boolean add(final Search search) {
        if (search.getResults().isEmpty()) {
            return false;
        }

        int i = 0;

        for (; i < searches.size(); i++) {
            if (searches.get(i).getTimeStamp() > search.getTimeStamp()) {
                break;
            }
        }

        searches.add(i, search);

        return true;
    }

    public Search getNewestSearch() {
        if (searches.isEmpty()) {
            return null;
        } else {
            return searches.get(searches.size() - 1);
        }
    }

    public Search getOldestSearch() {
        if (searches.isEmpty()) {
            return null;
        } else {
            return searches.get(0);
        }
    }

    public Search getSearch(int index) {
        return searches.get(index);
    }

    public List<Search> getAllSearches() {
        return new ArrayList<Search>(searches);
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<Result>();

        for (Search search : searches) {
            results.addAll(search.getResults());
        }

        return results;
    }

    public int getNumberOfSearches() {
        return searches.size();
    }

    public boolean isEmpty() {
        return searches.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName()).append('[');

        sb.append("id=").append(id);

        sb.append(" intent=").append(intent);

        sb.append(" user_id=").append(userId);

        sb.append(" searches=").append(searches.size());

        return sb.append(']').toString();
    }

    public static Session newInstance(final Search... searches) {
        Session session = new Session();

        for (Search search : searches) {
            session.add(search);
        }

        return session;
    }

    public static Session newInstance(final List<Search> searches) {
        return new Session(searches);
    }

    public static List<Search> collectSearches(final List<Session> sessions, final boolean sort) {
        List<Search> searches = new ArrayList<Search>();

        for (Session session : sessions) {
            searches.addAll(session.getAllSearches());
        }

        if (sort) {
            Collections.sort(searches, Search.OLDEST);
        }

        return searches;
    }

}
