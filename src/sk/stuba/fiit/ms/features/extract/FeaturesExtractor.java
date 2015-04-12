package sk.stuba.fiit.ms.features.extract;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.learning.train.Features;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class FeaturesExtractor {

    private final List<PairFeature> pairFeatures;

    private final List<SessionFeature> sessionFeatures;

    private FeaturesExtractor(final Builder builder) {
        pairFeatures = new ArrayList<PairFeature>(builder.pairFeatures);
        sessionFeatures = new ArrayList<SessionFeature>(builder.sessionFeatures);
    }

    public double[] extractFeatures(final Session session, final Search search) {
        final int numOfSessionFeatures = sessionFeatures.size();
        final int numOfPairFeatures = pairFeatures.size() * Features.transformExpansion();

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

        for (Search sr : searches) {
            features[i++] = this.extractPairFeatures(search, sr);
        }

        return Features.transform(features);
    }

    private double[] extractPairFeatures(final Search sr1, final Search sr2) {
        int size = pairFeatures.size();

        double[] values = new double[size];

        for (int i = 0; i < size; i++) {
            values[i] = pairFeatures.get(i).extract(sr1, sr2);
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

        public FeaturesExtractor build() {
            return new FeaturesExtractor(this);
        }

    }

}
