package sk.stuba.fiit.ms.features;

public final class FeatureNormalizer {

    private final int numberOfFeatures;

    private final int rows;

    private double[] featuresMeans;

    private double[] featuresStds;

    private boolean isNormalized;

    private double[][] features;

    public FeatureNormalizer(final double[][] features) {
        if (!isValidFeatures(features)) {
            throw new IllegalArgumentException("Features are illegal");
        }

        this.numberOfFeatures = features[0].length;
        this.rows = features.length;
        this.isNormalized = false;
        this.features = features;
    }

    public void normalizeInPlace() {
        if (isNormalized) {
            return;
        }

        computeMeans();
        computeStds();
        normalizeFeatures();

        isNormalized = true;
    }

    public void normalizeInPlace(final double[] featureVector) {
        if (featureVector == null || featureVector.length != numberOfFeatures) {
            throw new IllegalArgumentException("Feature vector is null or not same length");
        }

        for (int j = 0; j < featureVector.length; j++) {
            featureVector[j] = (featureVector[j] - featuresMeans[j]) / featuresStds[j];
        }
    }

    private void computeMeans() {
        featuresMeans = new double[numberOfFeatures];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numberOfFeatures; j++) {
                featuresMeans[j] += features[i][j];
            }
        }

        for (int j = 0; j < numberOfFeatures; j++) {
            featuresMeans[j] /= rows;
        }
    }

    private void computeStds() {
        featuresStds = new double[numberOfFeatures];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numberOfFeatures; j++) {
                featuresStds[j] += Math.pow(features[i][j] - featuresMeans[j], 2.0);
            }
        }

        for (int j = 0; j < numberOfFeatures; j++) {
            featuresStds[j] = Math.sqrt(featuresStds[j] / rows);

            if (Double.compare(featuresStds[j], 0.0) == 0) {
                featuresStds[j] = 1.0;
            }
        }
    }

    private void normalizeFeatures() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numberOfFeatures; j++) {
                features[i][j] = (features[i][j] - featuresMeans[j]) / featuresStds[j];
            }
        }
    }

    private boolean isValidFeatures(final double[][] features) {
        if (features == null || features.length == 0) {
            return false;
        } else {
            for (int i = 0; i < features.length; i++) {
                if (features[i] == null || features[i].length == 0) {
                    return false;
                }

                if (i + 1 < features.length) {
                    if (features[i + 1] == null || features[i + 1].length == 0) {
                        return false;
                    }

                    if (features[i].length != features[i + 1].length) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

}
