package sk.stuba.fiit.ms.measures.lexical;

// Implementation from http://en.wikipedia.org/wiki/Levenshtein_distance
public final class LevenshteinDistance extends LexicalDistance {

    private LevenshteinDistance() {}

    @Override
    public double calculate(final String s, final String t) {
        if (s.equals(t)) {
            return 0;
        }

        if (s.isEmpty()) {
            return t.length();
        }

        if (s.isEmpty()) {
            return t.length();
        }

        int[] v0 = new int[t.length() + 1];
        int[] v1 = new int[t.length() + 1];

        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < s.length(); i++) {
            v1[0] = i + 1;

            for (int j = 0; j < t.length(); j++) {
                int cost = (s.charAt(i) == t.charAt(j)) ? 0 : 1;

                v1[j + 1] = Math.min(Math.min(v1[j] + 1, v0[j + 1] + 1), v0[j] + cost);
            }

            for (int j = 0; j < v0.length; j++) {
                v0[j] = v1[j];
            }
        }

        return v1[t.length()];
    }

    public static LevenshteinDistance newInstance() {
        return new LevenshteinDistance();
    }

}
