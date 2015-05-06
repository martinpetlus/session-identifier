package sk.stuba.fiit.ms.features.extraction;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.features.lexical.QueryClickedSnippetsMeasure;
import sk.stuba.fiit.ms.features.lexical.QueryClickedTitlesMeasure;
import sk.stuba.fiit.ms.features.lexical.QueryMeasure;
import sk.stuba.fiit.ms.features.semantic.SemanticCosineOfSearches;
import sk.stuba.fiit.ms.features.temporal.TemporalDistanceNewest;
import sk.stuba.fiit.ms.features.temporal.TemporalDistanceOldest;
import sk.stuba.fiit.ms.features.url.CommonClickedHosts;
import sk.stuba.fiit.ms.features.url.CommonClickedUrls;
import sk.stuba.fiit.ms.features.url.CommonHosts;
import sk.stuba.fiit.ms.features.url.CommonUrls;
import sk.stuba.fiit.ms.learning.Features;
import sk.stuba.fiit.ms.measures.lexical.CosineLexicalSimilarity;
import sk.stuba.fiit.ms.measures.lexical.LevenshteinDistance;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class SessionSearchExtractor {

    private final List<PairFeature> pairFeatures;

    private final List<SessionFeature> sessionFeatures;

    private SessionSearchExtractor(final Builder builder) {
        pairFeatures = new ArrayList<PairFeature>(builder.pairFeatures);
        sessionFeatures = new ArrayList<SessionFeature>(builder.sessionFeatures);
    }

    public double[] extractFeatures(final Session session, final Search search) {
        int numOfSessionFeatures = sessionFeatures.size();
        int numOfPairFeatures = pairFeatures.size() * Features.transformExpansion();

        double[] featureVector = new double[numOfSessionFeatures + numOfPairFeatures];

        double[] features1 = extractSessionFeatures(session, search);
        double[] features2 = extractPairFeatures(session, search);

        System.arraycopy(features1, 0, featureVector, 0, numOfSessionFeatures);
        System.arraycopy(features2, 0, featureVector, numOfSessionFeatures, numOfPairFeatures);

        return featureVector;
    }

    private double[] extractSessionFeatures(final Session session, final Search search) {
        double[] featureVector = new double[sessionFeatures.size()];

        int i = 0;

        for (SessionFeature sessionFeature : sessionFeatures) {
            featureVector[i++] = sessionFeature.extract(session, search);
        }

        return featureVector;
    }

    private double[] extractPairFeatures(final Session session, final Search search) {
        List<Search> searches = session.getAllSearches();

        int i = 0;

        double[][] features = new double[searches.size()][];

        for (Search s : searches) {
            features[i++] = this.extractPairFeatures(search, s);
        }

        return Features.transform(features);
    }

    private double[] extractPairFeatures(final Search s1, final Search s2) {
        int size = pairFeatures.size();

        double[] values = new double[size];

        for (int i = 0; i < size; i++) {
            values[i] = pairFeatures.get(i).extract(s1, s2);
        }

        return values;
    }

    public static final class Builder {

        private List<PairFeature> pairFeatures = new ArrayList<PairFeature>();

        private List<SessionFeature> sessionFeatures = new ArrayList<SessionFeature>();

        public void addPairFeature(final PairFeature feature) {
            pairFeatures.add(feature);
        }

        public void addSessionFeature(final SessionFeature feature) { sessionFeatures.add(feature); }

        public SessionSearchExtractor build() {
            return new SessionSearchExtractor(this);
        }

    }

    public static SessionSearchExtractor buildDefault(final LDAModel ldaModel) {
        SessionSearchExtractor.Builder builder = new SessionSearchExtractor.Builder();

//        builder.addPairFeature(new QuerySimilarity(JaccardLexicalSimilarity.newInstance()));
//        builder.addPairFeature(new QueryTitleSimilarity(CosineLexicalSimilarity.newInstance()));
//        builder.addPairFeature(new QueryTitleSimilarity(JaccardLexicalSimilarity.newInstance()));
//
//        builder.addPairFeature(new CommonUrls());
//        builder.addPairFeature(new CommonClickedUrls());
//
//        builder.addPairFeature(new SemanticCosineOfSearches(model));

        // Session features
        if (ldaModel != null) {
            builder.addSessionFeature(new SemanticCosineOfSearches(ldaModel));
        }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append('[');

        if (!sessionFeatures.isEmpty()) {
            sb.append("SessionFeatures:(");

            for (int i = 0; i < sessionFeatures.size(); i++) {
                sb.append(sessionFeatures.get(i).getClass().getSimpleName());

                if (i + 1 < sessionFeatures.size()) {
                    sb.append(',');
                }
            }

            sb.append(')');
        }

        if (!pairFeatures.isEmpty()) {
            sb.append(";PairFeatures:(");

            for (int i = 0; i < pairFeatures.size(); i++) {
                sb.append(pairFeatures.get(i).getClass().getSimpleName());

                if (i + 1 < pairFeatures.size()) {
                    sb.append(',');
                }
            }

            sb.append(')');
        }

        sb.append(']');

        return sb.toString();
    }

}
