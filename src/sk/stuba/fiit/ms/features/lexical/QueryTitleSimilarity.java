package sk.stuba.fiit.ms.features.lexical;

import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.features.Util;
import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.session.Result;

public final class QueryTitleSimilarity implements PairFeature, SessionFeature {

    private final Similarity similarity;

    public QueryTitleSimilarity(final Similarity similarity) {
        this.similarity = similarity;
    }

    private double titleSimilarity(final String[] query, final Result result) {
        String[] title = TextNormalizer.split(result.getTitle());

        return similarity.calculate(query, title);
    }

    @Override
    public double extract(final SearchResult searchResult, final SearchResult compareTo) {
        String[] query = TextNormalizer.split(searchResult.getQuery());

        List<Result> results = compareTo.getResults();

        int i = 0;
        int size = results.size();

        double[] values = new double[size];

        for (Result result : results) {
            values[i++] = titleSimilarity(query, result);
        }

        return Statistic.mean(values);
    }

    @Override
    public double extract(final Session session, final SearchResult searchResult) {
        List<SearchResult> searchResults = session.getAllSearchResults();

        String[][] titles = new String[searchResults.size()][];

        for (int i = 0; i < searchResults.size(); i++) {
            titles[i] = getTitles(searchResults.get(i));
        }

        String[] union = Util.union(titles);

        return similarity.calculate(union, TextNormalizer.split(searchResult.getQuery()));
    }

    private String[] getTitles(final SearchResult searchResult) {
        String[][] titles = new String[searchResult.getNumberOfResults()][];

        int i = 0;

        for (Result result : searchResult.getResults()) {
            titles[i++] = TextNormalizer.split(result.getTitle());
        }

        return Util.union(titles);
    }

}
