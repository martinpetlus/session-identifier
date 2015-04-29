package sk.stuba.fiit.ms.learning.training.training_set.creation;

import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public interface ExamplesCreator {

    double[][] create(final List<Session> sessions, final int indexOfSession);

}
