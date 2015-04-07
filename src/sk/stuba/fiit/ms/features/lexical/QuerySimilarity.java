package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.features.Util;
import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public final class QuerySimilarity implements PairFeature, SessionFeature {

    private final Similarity similarity;

    public QuerySimilarity(final Similarity similarity) {
        this.similarity = similarity;
    }

    @Override
    public double extract(final SearchResult sr1, final SearchResult sr2) {
        String query1 = sr1.getQuery();
        String query2 = sr2.getQuery();

        return similarity.calculate(TextNormalizer.split(query1), TextNormalizer.split(query2));
    }

    @Override
    public double extract(final Session session, final SearchResult searchResult) {
        List<SearchResult> searchResults = session.getAllSearchResults();

        String[][] sentences = new String[searchResults.size()][];

        for (int i = 0; i < searchResults.size(); i++) {
            sentences[i] = TextNormalizer.split(searchResults.get(i).getQuery());
        }

        String[] union = Util.union(sentences);

        return similarity.calculate(union, TextNormalizer.split(searchResult.getQuery()));
    }

}
