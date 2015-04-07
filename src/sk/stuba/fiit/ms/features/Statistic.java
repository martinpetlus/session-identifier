package sk.stuba.fiit.ms.features;

public final class Statistic {

    private Statistic() {}

    public static double sum(final double[] m) {
        checkForVector(m);

        double sum = 0.0;

        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }

        return sum;
    }

    public static double mean(final double[] m) {
        checkForVector(m);

        double sum = 0.0;

        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }

        return sum / m.length;
    }

    public static double min(final double[] m) {
        checkForVector(m);

        double min = m[0];

        for (int i = 1; i < m.length; i++) {
            if (m[i] < min) {
                min = m[i];
            }
        }

        return min;
    }

    public static double max(final double[] m) {
        checkForVector(m);

        double max = m[0];

        for (int i = 1; i < m.length; i++) {
            if (m[i] > max) {
                max = m[i];
            }
        }

        return max;
    }

    public static double variance(final double[] m) {
        double mean = mean(m);
        double sum  = 0.0;

        for (int i = 0; i < m.length; i++) {
            sum += (mean - m[i]) * (mean - m[i]);
        }

        return sum / m.length;
    }

    public static double std(final double[] m) {
        return Math.sqrt(variance(m));
    }

    public static double magnitude(final double[] vec) {
        checkForVector(vec);

        double sum = 0.0;

        for (int i = 0; i < vec.length; i++) {
            sum += vec[i] * vec[i];
        }

        return Math.sqrt(sum);
    }

    public static double dotProduct(final double[] vec1, final double[] vec2) {
        checkForSameVectors(vec1, vec2);

        final int length = vec1.length;

        double[] dotProduct = new double[length];

        for (int i = 0; i < length; i++) {
            dotProduct[i] = vec1[i] * vec2[i];
        }

        return sum(dotProduct);
    }

    public static double cosine(final double[] vec1, final double[] vec2) {
        checkForSameVectors(vec1, vec2);

        double dotProd = dotProduct(vec1, vec2);

        double mag1 = magnitude(vec1);
        double mag2 = magnitude(vec2);

        return dotProd / (mag1 * mag2);
    }

    private static void checkForVector(final double[] m) {
        if (m == null || m.length == 0) {
            throw new IllegalArgumentException("Vector is null or empty");
        }
    }

    private static void checkForSameVectors(final double[] vec1, final double[] vec2) {
        if (vec1 == null || vec2 == null ||
                vec1.length == 0 || vec2.length == 0 ||
                vec1.length != vec2.length) {
            throw new IllegalArgumentException("Vectors are null, empty or not same length");
        }
    }

}
