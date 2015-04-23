package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.features.Util;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.similarities.lexical.LexicalSimilarity;

import java.util.List;

public final class QuerySimilarity implements PairFeature, SessionFeature {

    private final LexicalSimilarity lexicalSimilarity;

    public QuerySimilarity(final LexicalSimilarity lexicalSimilarity) {
        this.lexicalSimilarity = lexicalSimilarity;
    }

    @Override
    public double extract(final Search sr1, final Search sr2) {
        String query1 = sr1.getQuery();
        String query2 = sr2.getQuery();

        return lexicalSimilarity.calculate(TextNormalizer.split(query1), TextNormalizer.split(query2));
    }

    @Override
    public double extract(final Session session, final Search search) {
        List<Search> searches = session.getAllSearches();

        String[][] sentences = new String[searches.size()][];

        for (int i = 0; i < searches.size(); i++) {
            sentences[i] = TextNormalizer.split(searches.get(i).getQuery());
        }

        String[] union = Util.union(sentences);

        return lexicalSimilarity.calculate(union, TextNormalizer.split(search.getQuery()));
    }

}
