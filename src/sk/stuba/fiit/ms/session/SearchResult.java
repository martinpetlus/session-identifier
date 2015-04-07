package sk.stuba.fiit.ms.session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SearchResult {

    private static int numberOfSearchResults = 0;

    private final int id;

    private final String query;

    private final List<Result> results;

    private final List<Click> clicks;

    private SearchResult(final Builder builder) {
        this.id = ++numberOfSearchResults;

        this.query = builder.query;

        this.clicks = new ArrayList<Click>(builder.clicks);
        this.results = new ArrayList<Result>(builder.results);

        addClicksToResults(this.clicks);
    }

    private void addClicksToResults(final List<Click> clicks) {
        for (Click click : clicks) {
            getResultByRank(click.getRank()).addClick(click);
        }
    }

    public List<String> getResultsUrls() {
        List<String> urls = new ArrayList<String>();

        for (Result result : results) {
            urls.add(result.getUrl());
        }

        return urls;
    }

    public List<String> getClickedUrls() {
        Set<String> clickedUrls = new HashSet<String>();

        for (Click click : clicks) {
            clickedUrls.add(getResultByRank(click.getRank()).getUrl());
        }

        return new ArrayList<String>(clickedUrls);
    }

    public List<Result> getClickedResults() {
        Set<Result> clickedResults = new HashSet<Result>();

        for (Click click : clicks) {
            clickedResults.add(getResultByRank(click.getRank()));
        }

        return new ArrayList<Result>(clickedResults);
    }

    public List<Result> getClickedResults(double minimumTimeSpent) {
        Set<Result> clickedResults = new HashSet<Result>();

        for (Click click : clicks) {
            Result result = getResultByRank(click.getRank());

            if (result.getSpentTime() >= minimumTimeSpent) {
                clickedResults.add(getResultByRank(click.getRank()));
            }
        }

        return new ArrayList<Result>(clickedResults);
    }

    public int getNumberOfClicks() { return clicks.size(); }

    public int getNumberOfResults() { return results.size(); }

    public double getSpentTimeOnClicks() {
        double sum = 0.0;

        for (Click click : clicks) {
            sum += click.getSpentTime();
        }

        return sum;
    }

    public boolean hasClickedResults() {
        return !clicks.isEmpty();
    }

    public int getId() {
        return this.id;
    }

    public List<Result> getResults() {
        return new ArrayList<Result>(results);
    }

    public List<Click> getClicks() { return new ArrayList<Click>(clicks); }

    public Result getResult(final Click click) {
        if (clicks.contains(click)) {
            return getResultByRank(click.getRank());
        } else {
            return null;
        }
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "SearchResult[id= " + id +
                "query=" + query +
                " results=" + results.size() +
                " clicks=" + clicks.size() + "]";
    }

    private Result getResultByRank(final int rank) {
        return results.get(rank - 1);
    }

    public static final class Builder {
        private String query;

        private final List<Result> results = new ArrayList<Result>();

        private final List<Click> clicks = new ArrayList<Click>();

        public void setQuery(final String query) {
            this.query = query;
        }

        public void addResult(final Result result) {
            results.add(result);
        }

        public void addClick(final Click click) {
            clicks.add(click);
        }

        public SearchResult build() {
            return new SearchResult(this);
        }
    }

}
