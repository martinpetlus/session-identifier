package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.measures.lexical.LexicalSimilarity;

public final class QuerySimilarity implements PairFeature, SessionFeature {

    private final LexicalSimilarity lexicalSimilarity;

    public QuerySimilarity(final LexicalSimilarity lexicalSimilarity) {
        this.lexicalSimilarity = lexicalSimilarity;
    }

    @Override
    public double extract(final Search search1, final Search search2) {
        return lexicalSimilarity.calculate(search1.getQuery(), search2.getQuery());
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
