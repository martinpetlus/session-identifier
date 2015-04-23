package sk.stuba.fiit.ms.similarities.lexical;

import sk.stuba.fiit.ms.features.Util;

public final class CosineLexicalSimilarity extends LexicalSimilarity {

    private CosineLexicalSimilarity() {}

    private static int[] occurenceWithFrequency(final String[] a, final String[] union) {
        int[] vec = new int[union.length];

        int i = 0;

        for (String s : union) {
            vec[i++] = countOccurence(s, a);
        }

        return vec;
    }

    private static int countOccurence(String s, final String[] a) {
        int count = 0;

        for (String el : a) {
            if (el.equals(s)) {
                count++;
            }
        }

        return count;
    }

    private static double dotProduct(final int[] vec1, final int[] vec2, int length) {
        double dotProd = 0.0;

        for (int i = 0; i < length; i++) {
            dotProd += vec1[i] * vec2[i];
        }

        return dotProd;
    }

    private static double magnitude(final int[] vector) {
        double mag = 0.0;

        for (int i = 0; i < vector.length; i++) {
            mag += Math.pow(vector[i], 2);
        }

        return Math.sqrt(mag);
    }

    @Override
    public double calculate(final String[] one, final String[] two) {
        if (one.length == 0 || two.length == 0) {
            return 0.0;
        }

        String[] union = Util.union(one, two);

        int[] vecOneOcc = occurenceWithFrequency(one, union);
        int[] vecTwoOcc = occurenceWithFrequency(two, union);

        double vecOneMag = magnitude(vecOneOcc);
        double vecTwoMag = magnitude(vecTwoOcc);

        double dotProd = dotProduct(vecOneOcc, vecTwoOcc, union.length);

        return dotProd / (vecOneMag * vecTwoMag);
    }

    public static CosineLexicalSimilarity newInstance() {
        return new CosineLexicalSimilarity();
    }

}
