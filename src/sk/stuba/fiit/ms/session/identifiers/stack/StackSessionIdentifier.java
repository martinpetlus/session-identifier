package sk.stuba.fiit.ms.session.identifiers.stack;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.features.FeatureNormalizer;
import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.learning.SVM;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class StackSessionIdentifier extends SessionIdentifier {

    private final SVM model;

    private final SessionExtractor extractor;

    private final List<Session> stack;

    private final FeatureNormalizer normalizer;

    public StackSessionIdentifier(final SessionExtractor extractor, final SVM model) {
        this(extractor, model, null);
    }

    public StackSessionIdentifier(final SessionExtractor extractor, final SVM model, final FeatureNormalizer normalizer) {
        this.model = model;

        this.extractor = extractor;

        this.stack = new ArrayList<Session>();

        this.normalizer = normalizer;
    }

    private void addSession(final Search search) {
        Session session = new Session();

        session.add(search);

        stack.add(session);
    }

    @Override
    public void identify(final Search search) {
        if (stack.isEmpty()) {
            addSession(search);
        } else {
            int i;

            for (i = stack.size() - 1; i >= 0; i--) {
                Session session = stack.get(i);

                double[] features = extractor.extractFeatures(session, search);

                if (normalizer != null) {
                    normalizer.normalizeInPlace(features);
                }

                if (model.predict(features)) {
                    session.add(search);

                    stack.remove(i);
                    stack.add(session);

                    break;
                }
            }

            if (i < 0) {
                addSession(search);
            }
        }
    }

    @Override
    public List<Session> getIdentifiedSessions() {
        return new ArrayList<Session>(stack);
    }

}
