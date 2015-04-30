package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.measures.lexical.LexicalMeasure;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class QueryMeasure implements PairFeature, SessionFeature {

    private final LexicalMeasure lexicalMeasure;

    public QueryMeasure(final LexicalMeasure lexicalMeasure) {
        this.lexicalMeasure = lexicalMeasure;
    }

    @Override
    public double extract(final Search search1, final Search search2) {
        return lexicalMeasure.calculate(search1.getQuery(), search2.getQuery());
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
