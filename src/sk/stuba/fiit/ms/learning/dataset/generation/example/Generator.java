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

    protected int[] randomIndices(final int length, final int maxExclusive) {
        return randomIndices(length, maxExclusive, -1);
    }

    protected int[] randomIndices(final int length, final int maxExclusive, final int exclude) {
        if (length > (maxExclusive - (exclude >= 0  && exclude < maxExclusive ? 1 : 0))) {
            throw new IllegalArgumentException("Low indices range for given length");
        }

        int[] randomIndices = new int[length];

        int count = 0;

        Random random = new Random();

        while (count < length) {
            int randomIndex = random.nextInt(maxExclusive);

            if (randomIndex == exclude) {
                continue;
            }

            int i = 0;

            for (; i < count; i++) {
                if (randomIndices[i] == randomIndex) {
                    break;
                }
            }

            if (i == count) {
                randomIndices[count++] = randomIndex;
            }
        }

        return randomIndices;
    }


    public abstract boolean generatable(int queries);

    public abstract double[] generate(int queries);

}
