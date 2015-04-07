package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.SearchResult;

import java.util.List;

public final class CosineOfClickedResults extends Inferencer implements PairFeature {

	private final double minimumTimeSpent;

	public CosineOfClickedResults(final LDAModel model, final double minimumTimeSpent) {
		super(model);

		this.minimumTimeSpent = minimumTimeSpent;
	}

	public CosineOfClickedResults(final LDAModel model) {
		this(model, 0.0);
	}

	@Override
	public double extract(final SearchResult searchResult, final SearchResult compareTo) {
		List<Result> clicked1 = searchResult.getClickedResults(minimumTimeSpent);
		List<Result> clicked2 = compareTo.getClickedResults(minimumTimeSpent);

		if (clicked1.isEmpty() || clicked2.isEmpty()) {
			return 0.0;
		}

		double inferences = 0.0;

		double[][] inferences1 = super.getInferences(clicked1);
		double[][] inferences2 = super.getInferences(clicked2);

		for (int i = 0; i < inferences1.length; i++) {
			for (int j = 0; j < inferences2.length; j++) {
				inferences += Statistic.cosine(inferences1[i], inferences2[j]);
			}
		}

		return inferences / (inferences1.length * inferences2.length);
	}

}