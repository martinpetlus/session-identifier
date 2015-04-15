package sk.stuba.fiit.ms.features.extract;

import sk.stuba.fiit.ms.features.lexical.*;
import sk.stuba.fiit.ms.features.number.NumberOfClicks;
import sk.stuba.fiit.ms.features.number.NumberOfResults;
import sk.stuba.fiit.ms.features.number.NumberOfResultsViews;
import sk.stuba.fiit.ms.features.semantic.SemanticCosineOfSearches;
import sk.stuba.fiit.ms.features.time.SpentTimeOnClicks;
import sk.stuba.fiit.ms.features.url.CommonClickedUrls;
import sk.stuba.fiit.ms.features.url.CommonUrls;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class SessionExtractor {

    private final FeaturesExtractor featuresExtractor;

    public SessionExtractor(final LDAModel model) {
        this.featuresExtractor = addFeatures(model);
    }

    private FeaturesExtractor addFeatures(final LDAModel model) {
        FeaturesExtractor.Builder builder = new FeaturesExtractor.Builder();

        // Lexical features
//        builder.addPairFeature(new QuerySimilarity(Cosine.getInstance()));
//        builder.addPairFeature(new QuerySimilarity(Jaccard.getInstance()));
//        builder.addPairFeature(new QueryTitleSimilarity(Cosine.getInstance()));
//        builder.addPairFeature(new QueryTitleSimilarity(Jaccard.getInstance()));
//        builder.addPairFeature(new QueryCommonWords(QueryCommonWords.Direction.NO));
//
//        builder.addPairFeature(new CommonUrls());
//        builder.addPairFeature(new CommonClickedUrls());
//        builder.addPairFeature(new NumberOfClicks());
//        builder.addPairFeature(new NumberOfResults());
//        builder.addPairFeature(new SpentTimeOnClicks());
//        builder.addPairFeature(new NumberOfResultsViews());
//
//        // Semantic features
//        builder.addPairFeature(new SemanticCosineOfSearches(model));

        // Session features
        builder.addSessionFeature(new SemanticCosineOfSearches(model));
        builder.addSessionFeature(new QuerySimilarity(Cosine.getInstance()));
        builder.addSessionFeature(new QueryTitleSimilarity(Cosine.getInstance()));

        builder.addSessionFeature(new CommonClickedUrls());
        builder.addSessionFeature(new CommonUrls());

        return builder.build();
    }

    public FeaturesExtractor getFeaturesExtractor() {
        return featuresExtractor;
    }

    public double[] extractFeatures(final Session session, final Search search) {
        return featuresExtractor.extractFeatures(session, search);
    }

}
