package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.measures.lexical.LexicalMeasure;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.List;

abstract class QueryClickedMeasure implements PairFeature, SessionFeature {

    private final LexicalMeasure lexicalMeasure;

    public abstract String getResultText(final Result clickedResult);

    public QueryClickedMeasure(final LexicalMeasure lexicalMeasure) {
        this.lexicalMeasure = lexicalMeasure;
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
            sum += lexicalMeasure.calculate(query, getResultText(clickedResult));
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
