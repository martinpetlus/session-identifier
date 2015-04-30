package sk.stuba.fiit.ms.evaluation;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftEvaluator implements Evaluator {

    private final Map<Search, Session> originalSessionBySearch;

    private final Map<Search, Session> detectedSessionBySearch;

    public ShiftEvaluator() {
        originalSessionBySearch = new HashMap<Search, Session>();
        detectedSessionBySearch = new HashMap<Search, Session>();
    }

    private void clearAndFillMaps(final List<Session> originalSessions, final List<Session> detectedSessions) {
        originalSessionBySearch.clear();
        detectedSessionBySearch.clear();

        for (Session session : originalSessions) {
            for (Search search : session.getAllSearches()) {
                originalSessionBySearch.put(search, session);
            }
        }

        for (Session session : detectedSessions) {
            for (Search search : session.getAllSearches()) {
                detectedSessionBySearch.put(search, session);
            }
        }
    }

    @Override
    public EvaluatorResult evaluate(final List<Session> originalSessions, final List<Session> detectedSessions) {
        clearAndFillMaps(originalSessions, detectedSessions);

        // Collect queries
        List<Search> searches = Session.collectSearches(originalSessions);

        // Sort queries from the oldest to the newest issued
        Collections.sort(searches, Search.OLDEST);

        // Number of shifts detected by our method
        int shifts = 0;

        // Number of correct shifts detected by out method
        int correctShifts = 0;

        // Number of true shifts in original sessions
        int trueShifts = 0;

        // Loop through queries from the oldest to the newest
        for (int i = 1; i < searches.size(); i++) {
            Search currentSearch = searches.get(i);
            Search previousSearch = searches.get(i - 1);

            // If there is a shift in our detected sessions
            if (!detectedSessionBySearch.get(previousSearch).equals(detectedSessionBySearch.get(currentSearch))) {
                shifts++;

                // Is this a correct shift?
                if (!originalSessionBySearch.get(previousSearch).equals(originalSessionBySearch.get(currentSearch))) {
                    correctShifts++;
                }
            }

            // If there is a true shift in original sessions
            if (!originalSessionBySearch.get(previousSearch).equals(originalSessionBySearch.get(currentSearch))) {
                trueShifts++;
            }
        }

        return new ShiftEvaluatorResult(shifts, correctShifts, trueShifts);
    }

    private static final class PrecisionMeasure implements Measure {

        private final double computed;

        public PrecisionMeasure(final int correctShifts, final int shifts) {
            computed = ((double) correctShifts) / shifts;
        }

        @Override
        public double compute() {
            return computed;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[precision=" + computed + "]";
        }

    }

    private static final class RecallMeasure implements Measure {

        private final double computed;

        public RecallMeasure(final int correctShifts, final int trueShifts) {
            computed = ((double) correctShifts) / trueShifts;
        }

        @Override
        public double compute() {
            return computed;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[recall=" + computed + "]";
        }

    }

    private static final class FBetaMeasure implements Measure {

        // Beta is a weight to control of the emphasis on precision or recall
        private final double beta;

        private final double computed;

        // Emphasized recall over precision by setting beta to 1.5
        public FBetaMeasure(final Measure precision, final Measure recall) {
            this(1.5, precision, recall);
        }

        public FBetaMeasure(final double beta, final Measure precision, final Measure recall) {
            this.beta = beta;

            double numerator = (1 + this.beta * this.beta) * precision.compute() * recall.compute();

            double denominator = this.beta * this.beta * precision.compute() + recall.compute();

            this.computed = numerator / denominator;
        }

        @Override
        public double compute() {
            return computed;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() +
                "[beta=" + beta + " f=" + computed + "]";
        }

    }

    private static final class ShiftEvaluatorResult implements EvaluatorResult {

        private final Measure precision;

        private final Measure recall;

        private final Measure fBeta;

        public ShiftEvaluatorResult(final int shifts, final int correctShifts, final int trueShifts) {
            precision = new PrecisionMeasure(correctShifts, shifts);

            recall = new RecallMeasure(correctShifts, trueShifts);

            fBeta = new FBetaMeasure(precision, recall);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() +
                "[precision=" + precision.compute() +
                " recall=" + recall.compute() +
                " fBeta=" + fBeta.compute() + "]";
        }

    }

}
