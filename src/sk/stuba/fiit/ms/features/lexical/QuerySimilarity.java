package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.utils.SetUtils;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.measures.lexical.LexicalSimilarity;
import sk.stuba.fiit.ms.utils.TextNormalizer;

import java.util.List;

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
        List<Search> searches = session.getAllSearches();

        String[][] sentences = new String[searches.size()][];

        for (int i = 0; i < searches.size(); i++) {
            sentences[i] = TextNormalizer.split(searches.get(i).getQuery());
        }

        String[] union = SetUtils.union(sentences);

        return lexicalSimilarity.calculate(union, TextNormalizer.split(search.getQuery()));
    }

}
