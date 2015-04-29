package sk.stuba.fiit.ms.learning.traning.traning_set.creation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk.stuba.fiit.ms.features.extraction.SessionExtractor;
import sk.stuba.fiit.ms.learning.traning.traning_set.TrainingSet;
import sk.stuba.fiit.ms.session.Session;

public final class TrainingSetCreator {

    private final ExamplesCreator positiveCreator;

    private final ExamplesCreator negativeCreator;

    public TrainingSetCreator(final SessionExtractor extractor) {
        positiveCreator = new PositiveExamplesCreator(extractor);

        negativeCreator = new NegativeExamplesCreator(extractor);
    }

    public TrainingSet generateTrainingSet(final List<Session> sessions) {
        // Create copy of the sessions for sorting purpose
        List<Session> sortedSessions = new ArrayList<Session>(sessions);

        // Sort sessions from oldest to newest by their oldest query
        Collections.sort(sortedSessions, Session.OLDEST);

        // New instance of our training set for the classifier
        TrainingSet trainingSet = new TrainingSet();

        for (int i = 0; i < sortedSessions.size(); i++) {
            // Generate positive examples for this session
            double[][] positiveExamples = positiveCreator.create(sortedSessions, i);

            if (positiveExamples != null) {
                trainingSet.addPositiveExamples(positiveExamples);
            }

            // Generate negative examples for this session
            double[][] negativeExamples = negativeCreator.create(sortedSessions, i);

            if (negativeExamples != null) {
                trainingSet.addNegativeExamples(negativeExamples);
            }
        }

        return trainingSet;
    }

}
