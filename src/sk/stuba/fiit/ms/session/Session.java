package sk.stuba.fiit.ms.session;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.input.Topic;

public final class Session {

    private static int numberOfSessions = 0;

    private final int id;

    private final List<Search> searches;

    private Topic topic;

    public Session() {
        this(null);
    }

    public Session(final List<Search> results) {
        this.id = ++numberOfSessions;

        if (results == null) {
            this.searches = new ArrayList<Search>();
        } else {
            this.searches = new ArrayList<Search>(results);
        }
    }

    public int getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic t) {
        this.topic = t;
    }

    public boolean add(final Search search) {
        if (search.getResults().size() > 0) {
            searches.add(search);
            return true;
        } else {
            return false;
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
        return searches.size() == 0;
    }

    @Override
    public String toString() {
        return "Session[id=" + id + " searches=" + getNumberOfSearches() + "]";
    }

}
