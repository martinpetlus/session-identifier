package sk.stuba.fiit.ms.features.extract;

import sk.stuba.fiit.ms.features.number.NumberOfClicks;
import sk.stuba.fiit.ms.features.lexical.Jaccard;
import sk.stuba.fiit.ms.features.lexical.QueryCommonWords;
import sk.stuba.fiit.ms.features.lexical.QuerySimilarity;
import sk.stuba.fiit.ms.features.lexical.QueryTitleSimilarity;
import sk.stuba.fiit.ms.features.number.NumberOfResults;
import sk.stuba.fiit.ms.features.semantic.Cosine;
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
        builder.addPairFeature(new QuerySimilarity(sk.stuba.fiit.ms.features.lexical.Cosine.getInstance()));
        builder.addPairFeature(new QuerySimilarity(Jaccard.getInstance()));
        builder.addPairFeature(new QueryTitleSimilarity(sk.stuba.fiit.ms.features.lexical.Cosine.getInstance()));
        builder.addPairFeature(new QueryTitleSimilarity(Jaccard.getInstance()));
        builder.addPairFeature(new QueryCommonWords(QueryCommonWords.Direction.NO));

        builder.addPairFeature(new CommonUrls());
        builder.addPairFeature(new CommonClickedUrls());
        builder.addPairFeature(new NumberOfClicks());
        builder.addPairFeature(new NumberOfResults());
        builder.addPairFeature(new SpentTimeOnClicks());

        // Semantic features
        builder.addPairFeature(new Cosine(model));

        // Session features
        builder.addSessionFeature(new QuerySimilarity(sk.stuba.fiit.ms.features.lexical.Cosine.getInstance()));
        builder.addSessionFeature(new QueryTitleSimilarity(sk.stuba.fiit.ms.features.lexical.Cosine.getInstance()));

        builder.addSessionFeature(new CommonClickedUrls());
        builder.addSessionFeature(new CommonUrls());

        return builder.build();
    }

    public double[] extractFeatures(final Session session, final Search search) {
        return featuresExtractor.extractFeatures(session, search);
    }

}
