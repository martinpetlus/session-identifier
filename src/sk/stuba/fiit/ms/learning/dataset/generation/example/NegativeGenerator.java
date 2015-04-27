package sk.stuba.fiit.ms.learning.dataset.generation.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.stuba.fiit.ms.features.extraction.SessionExtractor;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class NegativeGenerator extends Generator {

    private final List<Session> sessions;

    public NegativeGenerator(final SessionExtractor extractor, final Session session, final List<Session> allSessions) {
        super(extractor, session);

        this.sessions = allSessions;
    }

    @Override
    public double[][] generate(final int queries) {
        if (!canGenerate(queries)) {
            throw new IllegalArgumentException("Illegal number of queries: " + queries);
        }

        Session session = this.getSession();

        Search randomSearch = null;

        Random random = new Random();

        while (randomSearch == null) {
            Session randomSession = sessions.get(random.nextInt(sessions.size()));

            if (!session.equals(randomSession)) {
                randomSearch = randomSession.getSearch(random.nextInt(randomSession.getNumberOfSearches()));
            }
        }

        int[] indices = Generator.randomIndices(queries, session.getNumberOfSearches());

        List<Search> randomSearches = new ArrayList<Search>(queries);

        for (int i = 0; i < queries; i++) {
            randomSearches.add(session.getSearch(indices[i]));
        }

        double features[][] = new double[1][];

        features[0] = this.getSessionExtractor().extractFeatures(new Session(randomSearches), randomSearch);

        return features;
    }

    @Override
    public boolean canGenerate(final int queries) {
        Session session = this.getSession();

        if (queries > session.getNumberOfSearches()) {
            return false;
        }

        for (Session s : sessions) {
            if (!session.equals(s)) {
                return true;
            }
        }

        return false;
    }

}
