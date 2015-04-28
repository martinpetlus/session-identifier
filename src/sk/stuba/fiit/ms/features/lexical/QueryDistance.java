package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.measures.lexical.LexicalDistance;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class QueryDistance implements PairFeature, SessionFeature {

    private final LexicalDistance lexicalDistance;

    public QueryDistance(final LexicalDistance lexicalDistance) {
        this.lexicalDistance = lexicalDistance;
    }

    @Override
    public double extract(final Search search1, final Search search2) {
        return lexicalDistance.calculate(search1.getQuery(), search2.getQuery());
    }

    @Override
    public double extract(Session session, Search search) {
        double sum = 0.0;

        for (Search search1 : session.getAllSearches()) {
            sum += this.extract(search, search1);
        }

        return sum / session.getNumberOfSearches();
    }

}
