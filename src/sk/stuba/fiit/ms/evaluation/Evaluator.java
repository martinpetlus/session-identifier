package sk.stuba.fiit.ms.evaluation;

import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public interface Evaluator {

    EvaluatorResult evaluate(final List<Session> originalSessions, final List<Session> detectedSessions);

}
