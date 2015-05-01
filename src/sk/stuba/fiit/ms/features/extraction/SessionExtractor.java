package sk.stuba.fiit.ms.features.extraction;

import sk.stuba.fiit.ms.features.lexical.*;
import sk.stuba.fiit.ms.features.semantic.SemanticCosineOfSearches;
import sk.stuba.fiit.ms.features.temporal.TemporalDistanceNewest;
import sk.stuba.fiit.ms.features.temporal.TemporalDistanceOldest;
import sk.stuba.fiit.ms.features.url.CommonClickedHosts;
import sk.stuba.fiit.ms.features.url.CommonClickedUrls;
import sk.stuba.fiit.ms.features.url.CommonHosts;
import sk.stuba.fiit.ms.features.url.CommonUrls;
import sk.stuba.fiit.ms.measures.lexical.LevenshteinDistance;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.measures.lexical.CosineLexicalSimilarity;

public final class SessionExtractor {

    private final FeatureExtractor featureExtractor;

    public SessionExtractor(final LDAModel model) {
        this.featureExtractor = addFeatures(model);
    }

    private FeatureExtractor addFeatures(final LDAModel model) {
        FeatureExtractor.Builder builder = new FeatureExtractor.Builder();

//        builder.addPairFeature(new QuerySimilarity(JaccardLexicalSimilarity.newInstance()));
//        builder.addPairFeature(new QueryTitleSimilarity(CosineLexicalSimilarity.newInstance()));
//        builder.addPairFeature(new QueryTitleSimilarity(JaccardLexicalSimilarity.newInstance()));
//
//        builder.addPairFeature(new CommonUrls());
//        builder.addPairFeature(new CommonClickedUrls());
//
//        // Semantic features
//        builder.addPairFeature(new SemanticCosineOfSearches(model));

        // Session features
        builder.addSessionFeature(new SemanticCosineOfSearches(model));

        builder.addSessionFeature(new QueryMeasure(CosineLexicalSimilarity.newInstance()));
        builder.addSessionFeature(new QueryMeasure(LevenshteinDistance.newInstance()));

        builder.addSessionFeature(new QueryClickedTitlesMeasure(CosineLexicalSimilarity.newInstance()));
        builder.addSessionFeature(new QueryClickedSnippetsMeasure(CosineLexicalSimilarity.newInstance()));

        builder.addSessionFeature(new CommonHosts());
        builder.addSessionFeature(new CommonClickedHosts());

        builder.addSessionFeature(new CommonClickedUrls());
        builder.addSessionFeature(new CommonUrls());

        builder.addSessionFeature(new TemporalDistanceNewest());
        builder.addSessionFeature(new TemporalDistanceOldest());

        // Pair features
        builder.addPairFeature(new QueryMeasure(CosineLexicalSimilarity.newInstance()));

        return builder.build();
    }

    public FeatureExtractor getFeatureExtractor() {
        return featureExtractor;
    }

    public double[] extractFeatures(final Session session, final Search search) {
        return featureExtractor.extractFeatures(session, search);
    }

}
