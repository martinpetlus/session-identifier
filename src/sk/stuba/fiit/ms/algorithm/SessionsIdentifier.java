package sk.stuba.fiit.ms.algorithm;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.features.FeatureNormalizer;
import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.learning.SVM;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class SessionsIdentifier {

    private final SVM model;

    private final SessionExtractor extractor;

    private final List<Session> sessions;

    private final FeatureNormalizer normalizer;

    public SessionsIdentifier(final SessionExtractor extractor, final SVM model) {
        this(extractor, model, null);
    }

    public SessionsIdentifier(final SessionExtractor extractor, final SVM model, final FeatureNormalizer normalizer) {
        this.model = model;

        this.extractor = extractor;

        this.sessions = new ArrayList<Session>();

        this.normalizer = normalizer;
    }

    private void addSession(final Search search) {
        Session session = new Session();

        session.add(search);

        sessions.add(session);
    }

    public void add(final Search search) {
        if (sessions.isEmpty()) {
            addSession(search);
        } else {
            int i;

            for (i = sessions.size() - 1; i >= 0; i--) {
                Session session = sessions.get(i);

                double[] features = extractor.extractFeatures(session, search);

                if (normalizer != null) {
                    normalizer.normalizeInPlace(features);
                }

                if (model.predict(features)) {
                    session.add(search);

                    sessions.remove(i);
                    sessions.add(session);

                    break;
                }
            }

            if (i < 0) {
                addSession(search);
            }
        }
    }

    public void addAll(final List<Search> searches) {
        for (Search search : searches) {
            add(search);
        }
    }

    public List<Session> getSessions() {
        return new ArrayList<Session>(sessions);
    }

}
