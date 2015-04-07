package sk.stuba.fiit.ms.features.extract;

import sk.stuba.fiit.ms.features.number.NumberOfClicks;
import sk.stuba.fiit.ms.features.lexical.Cosine;
import sk.stuba.fiit.ms.features.lexical.Jaccard;
import sk.stuba.fiit.ms.features.lexical.QueryCommonWords;
import sk.stuba.fiit.ms.features.lexical.QuerySimilarity;
import sk.stuba.fiit.ms.features.lexical.QueryTitleSimilarity;
import sk.stuba.fiit.ms.features.number.NumberOfResults;
import sk.stuba.fiit.ms.features.semantic.ClickedResults;
import sk.stuba.fiit.ms.features.semantic.CosineOfClickedResults;
import sk.stuba.fiit.ms.features.semantic.CosineOfResults;
import sk.stuba.fiit.ms.features.time.SpentTimeOnClicks;
import sk.stuba.fiit.ms.features.url.CommonClickedUrls;
import sk.stuba.fiit.ms.features.url.CommonUrls;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

public final class SessionExtractor {

    private final FeaturesExtractor featuresExtractor;

    public SessionExtractor(final LDAModel model) {
        this.featuresExtractor = addFeatures(model);
    }

    private FeaturesExtractor addFeatures(final LDAModel model) {
        FeaturesExtractor.Builder builder = new FeaturesExtractor.Builder();

        // Lexical features
        builder.addPairFeature(new QuerySimilarity(Cosine.getInstance()));
        builder.addPairFeature(new QuerySimilarity(Jaccard.getInstance()));
        builder.addPairFeature(new QueryTitleSimilarity(Cosine.getInstance()));
        builder.addPairFeature(new QueryTitleSimilarity(Jaccard.getInstance()));
        builder.addPairFeature(new QueryCommonWords(QueryCommonWords.Direction.NO));

        builder.addPairFeature(new CommonUrls());
        builder.addPairFeature(new CommonClickedUrls());
        builder.addPairFeature(new NumberOfClicks());
        builder.addPairFeature(new NumberOfResults());
        builder.addPairFeature(new SpentTimeOnClicks());

        // Semantic features
        builder.addPairFeature(new ClickedResults(model, 0.6));
        builder.addPairFeature(new CosineOfClickedResults(model));
        builder.addPairFeature(new CosineOfResults(model));

        // Session features
        builder.addSessionFeature(new QuerySimilarity(Cosine.getInstance()));
        builder.addSessionFeature(new QueryTitleSimilarity(Cosine.getInstance()));

        builder.addSessionFeature(new CommonClickedUrls());
        builder.addSessionFeature(new CommonUrls());

        return builder.build();
    }

    public double[] extractFeatures(final Session session, final SearchResult searchResult) {
        return featuresExtractor.extractFeatures(session, searchResult);
    }

}
