package sk.stuba.fiit.ms.session.identifiers.stack;

import sk.stuba.fiit.ms.features.FeatureNormalizer;
import sk.stuba.fiit.ms.features.extraction.SessionExtractor;
import sk.stuba.fiit.ms.learning.SVM;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class MethodStackApproach implements StackApproach {

    private final SVM model;

    private final SessionExtractor extractor;

    private final FeatureNormalizer normalizer;

    public MethodStackApproach(final SessionExtractor extractor, final SVM model, final FeatureNormalizer normalizer) {
        this.model = model;

        this.extractor = extractor;

        this.normalizer = normalizer;
    }

    @Override
    public boolean isMatch(final Session session, final Search search) {
        double[] features = extractor.extractFeatures(session, search);

        normalizer.normalizeInPlace(features);

        return model.predict(features);
    }

}
