package sk.stuba.fiit.ms.features.lexical;

import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.utils.SetUtils;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.measure.lexical.LexicalSimilarity;
import sk.stuba.fiit.ms.utils.TextNormalizer;

public final class QueryTitleSimilarity implements PairFeature, SessionFeature {

    private final LexicalSimilarity lexicalSimilarity;

    public QueryTitleSimilarity(final LexicalSimilarity lexicalSimilarity) {
        this.lexicalSimilarity = lexicalSimilarity;
    }

    private double titleSimilarity(final String[] query, final Result result) {
        String[] title = TextNormalizer.split(result.getTitle());

        return lexicalSimilarity.calculate(query, title);
    }

    @Override
    public double extract(final Search search, final Search compareTo) {
        String[] query = TextNormalizer.split(search.getQuery());

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
    public double extract(final Session session, final Search search) {
        List<Search> searches = session.getAllSearches();

        String[][] titles = new String[searches.size()][];

        for (int i = 0; i < searches.size(); i++) {
            titles[i] = getTitles(searches.get(i));
        }

        String[] union = SetUtils.union(titles);

        return lexicalSimilarity.calculate(union, TextNormalizer.split(search.getQuery()));
    }

    private String[] getTitles(final Search search) {
        String[][] titles = new String[search.getNumberOfResults()][];

        int i = 0;

        for (Result result : search.getResults()) {
            titles[i++] = TextNormalizer.split(result.getTitle());
        }

        return SetUtils.union(titles);
    }

}
