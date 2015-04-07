package sk.stuba.fiit.ms.learning.train;

import sk.stuba.fiit.ms.features.Statistic;

public final class Features {

	private static final int EXPANSION = 4;

	public static double[] transform(final double[][] features) {
		double[] reduced = new double[features[0].length * EXPANSION];
		
		double[] feature = new double[features.length];
		
		for (int j = 0; j < features[0].length; j++) {
			for (int i = 0; i < features.length; i++) {
				feature[i] = features[i][j];
			}
			
			reduced[j * EXPANSION] = Statistic.min(feature);
			reduced[j * EXPANSION + 1] = Statistic.max(feature);
			reduced[j * EXPANSION + 2] = Statistic.mean(feature);
			reduced[j * EXPANSION + 3] = Statistic.std(feature);
		}
			
		return reduced;
	}

	public static int transformExpansion() {
		return EXPANSION;
	}
	
}
