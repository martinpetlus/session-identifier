package sk.stuba.fiit.ms.learning.training.training_set.creation;

import sk.stuba.fiit.ms.session.Session;

import java.util.List;

/**
 * Interface to be implemented with training examples creator.
 */
public interface ExamplesCreator {

    /**
     * Creates training examples from the session of the list.
     * @param sessions list of session
     * @param indexOfSession index of session to create training examples from
     * @return created training examples
     */
    double[][] create(final List<Session> sessions, final int indexOfSession);

}
