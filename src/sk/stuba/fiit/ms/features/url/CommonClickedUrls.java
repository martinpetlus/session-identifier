package sk.stuba.fiit.ms.features.url;

import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

public final class CommonClickedUrls implements PairFeature, SessionFeature {

	@Override
	public double extract(final SearchResult searchResult, final SearchResult compareTo) {
		List<String> clicked = compareTo.getClickedUrls();
		
		int count = 0;
		
		for (String url : searchResult.getClickedUrls()) {
			if (clicked.contains(url)) {
				count++;
			}
		}
		
		return count;
	}

	@Override
	public double extract(final Session session, final SearchResult searchResult) {
		int count = 0;

		for (SearchResult sr : session.getAllSearchResults()) {
			count += (int) this.extract(sr, searchResult);
		}

		return count;
	}

}
