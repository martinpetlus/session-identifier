package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.measures.lexical.LexicalSimilarity;

import java.util.List;

public final class QueryClickedTitlesSimilarity implements PairFeature, SessionFeature {

    private final LexicalSimilarity lexicalSimilarity;

    public QueryClickedTitlesSimilarity(final LexicalSimilarity lexicalSimilarity) {
        this.lexicalSimilarity = lexicalSimilarity;
    }

    @Override
    public double extract(final Search search, final Search compareTo) {
        if (!compareTo.hasClickedResults()) {
            return 0.0;
        }

        String query = search.getQuery();

        double sum = 0.0;

        List<Result> clickedResults = compareTo.getClickedResults();

        for (Result clickedResult : clickedResults) {
            sum += lexicalSimilarity.calculate(query, clickedResult.getTitle());
        }

        return sum / clickedResults.size();
    }

    @Override
    public double extract(final Session session, final Search search) {
        double sum = 0.0;

        for (Search s : session.getAllSearches()) {
            sum += this.extract(search, s);
        }

        return sum / session.getNumberOfSearches();
    }

}
