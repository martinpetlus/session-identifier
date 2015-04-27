package sk.stuba.fiit.ms.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class Evaluator {

    private Evaluator() {}

    private static int findSessionId(final Search sr, final List<Session> sessions) {
        for (Session session : sessions) {
            List<Search> searches = session.getAllSearches();

            if (searches.indexOf(sr) > -1) {
                return session.getId();
            }
        }

        return -1;
    }

    private static Session findSessionById(final int id, final List<Session> sessions) {
        for (Session session : sessions) {
            if (id == session.getId()) {
                return session;
            }
        }

        return null;
    }

    private static List<Cardinality> computeCardinalities(final int[] mapping) {
        Map<Integer, Cardinality> cardinalities = new HashMap<Integer, Cardinality>();

        for (Integer id : mapping) {
            if (cardinalities.containsKey(id)) {
                cardinalities.get(id).incrementValue();
            } else {
                cardinalities.put(id, new Cardinality(id, 1));
            }
        }

        return new ArrayList<Cardinality>(cardinalities.values());
    }

    private static Cardinality findMaximumCardinality(final int[] mapping) {
        List<Cardinality> cardinalities = computeCardinalities(mapping);

        return Collections.max(cardinalities, Cardinality.COMPARATOR);
    }

    private static double computePrecision(final Cardinality c, final int length) {
        return ((double) c.getValue()) / length;
    }

    private static double computeRecall(final Cardinality c, final List<Session> sessions) {
        Session session = findSessionById(c.getSessionId(), sessions);

        return ((double) c.getValue()) / session.getNumberOfSearches();
    }

    public static Results evaluate(final List<Session> correct, final List<Session> detected) {
        int[][] mapping = new int[detected.size()][];

        int i = 0;
        int size = detected.size();

        double precisions = 0.0;
        double recalls = 0.0;

        for (Session session : detected) {
            List<Search> searches = session.getAllSearches();

            mapping[i] = new int[searches.size()];

            int j = 0;

            for (Search search : searches) {
                mapping[i][j++] = findSessionId(search, correct);
            }

            Cardinality max = findMaximumCardinality(mapping[i]);

            precisions += computePrecision(max, mapping[i].length);
            recalls += computeRecall(max, correct);

            i++;
        }

        return new Results(precisions / size, recalls / size);
    }

    private static final class Cardinality {

        private final int sessionId;

        private int value;

        public static final Comparator<Cardinality> COMPARATOR = new Comparator<Cardinality>() {

            @Override
            public int compare(final Cardinality c1, final Cardinality c2) {
                return c1.getValue() - c2.getValue();
            }

        };

        public Cardinality(final int sessionId, final int value) {
            this.sessionId = sessionId;
            this.value = value;
        }

        public void incrementValue() {
            value++;
        }

        public int getValue() {
            return value;
        }

        public int getSessionId() {
            return sessionId;
        }

        @Override
        public String toString() {
            return getClass().getName() +
                    "[session_id=" +
                    sessionId + " value=" +
                    value + "]";
        }

    }

    public static final class Results {

        private final double precision;

        private final double recall;

        private Results(final double precision, final double recall) {
            this.precision = precision;
            this.recall = recall;
        }

        public double getF1Score() {
            return 2 * (precision * recall) / (precision + recall);
        }

        public double getPrecision() {
            return precision;
        }

        public double getRecall() {
            return recall;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() +
                    "[precision=" + precision +
                    " recall=" + recall +
                    " f1_score=" + getF1Score() + "]";
        }

    }

}
