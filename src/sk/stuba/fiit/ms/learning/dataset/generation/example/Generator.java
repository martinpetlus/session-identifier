package sk.stuba.fiit.ms.learning.dataset.generation.example;

import java.util.Random;

import sk.stuba.fiit.ms.session.Session;

public abstract class Generator {

    protected final Random random;

    protected final Session session;

    public Generator(final Session session) {
        this.session = session;
        this.random = new Random();
    }

    private static void swap(final int arr[], final int a, final int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static int[] randomIndices(final int length, final int maxExclusive) {
        return randomIndices(length, maxExclusive, -1);
    }

    public static int[] randomIndices(final int length, final int maxExclusive, final int exclude) {
        if (length > (maxExclusive - (exclude >= 0  && exclude < maxExclusive ? 1 : 0))) {
            throw new IllegalArgumentException("Low indices range for given length");
        }

        int[] indices = new int[maxExclusive];

        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        Random random = new Random();

        for (int i = 0; i < indices.length; i++) {
            swap(indices, i, random.nextInt(indices.length));
        }

        int[] randomIndices = new int[length];

        for (int i = 0, j = 0; i < length; i++, j++) {
            if (indices[j] == exclude) {
                j++;
            }

            randomIndices[i] = indices[j];
        }

        return randomIndices;
    }

    public abstract boolean generatable(int queries);

    public abstract double[] generate(int queries);

}
