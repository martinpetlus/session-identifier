package sk.stuba.fiit.ms.session;

import java.util.*;

public final class Search {

    private static int numberOfSearches = 0;

    private final int id;

    private final String query;

    private final Map<Integer, Result> results;

    private final List<Integer> resultsViews;

    private final List<Click> clicks;

    private Search(final Builder builder) {
        this.id = ++numberOfSearches;

        this.query = builder.query;

        this.clicks = new ArrayList<Click>(builder.clicks);

        this.results = new HashMap<Integer, Result>(builder.results.size());

        this.resultsViews = new ArrayList<Integer>(builder.results.size());

        for (Result result : builder.results) {
            this.results.put(result.getRank(), result);
            this.resultsViews.add(result.getRank());
        }

        addClicksToResults(this.clicks);
    }

    private void addClicksToResults(final List<Click> clicks) {
        for (Click click : clicks) {
            getResultByRank(click.getRank()).addClick(click);
        }
    }

    public List<String> getResultsUrls() {
        List<String> urls = new ArrayList<String>();

        for (Result result : results.values()) {
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

    public int getNumerOfResultsViews() {
        return resultsViews.size();
    }

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
        List<Result> results = new ArrayList<Result>();

        for (Result result : this.results.values()) {
            results.add(result);
        }

        return results;
    }

    public List<Click> getClicks() { return new ArrayList<Click>(clicks); }

    public Result getResult(final Click click) {
        if (clicks.contains(click)) {
            return getResultByRank(click.getRank());
        } else {
            return null;
        }
    }

    public void mergeIn(final Search search) {
        this.clicks.addAll(search.clicks);

        for (Click click : search.clicks) {
            Result result = getResultByRank(click.getRank());

            if (result != null) {
                result.addClick(click);
            }
        }

        for (Result result : search.results.values()) {
            int rank = result.getRank();

            if (!this.results.containsKey(rank)) {
                this.results.put(rank, result);
            }
        }

        this.resultsViews.addAll(search.resultsViews);
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "Search[id= " + id +
                "query=" + query +
                " results=" + results.size() +
                " clicks=" + clicks.size() + "]";
    }

    private Result getResultByRank(final int rank) {
        return results.get(rank);
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

        public Search build() {
            return new Search(this);
        }

    }

}
