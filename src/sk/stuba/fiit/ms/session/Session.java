package sk.stuba.fiit.ms.session;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.input.Topic;

public final class Session {
	
	private static int numberOfSessions = 0;
	
	private final int id;
	
	private final List<SearchResult> searchResults;
	
	private Topic topic;
	
	public Session() {
		this(null);
	}
	
	public Session(final List<SearchResult> results) {
		this.id = ++numberOfSessions;
		
		if (results == null) {
			this.searchResults = new ArrayList<SearchResult>();
		} else {
			this.searchResults = new ArrayList<SearchResult>(results);
		}	
	}
	
	public int getId() {
		return id;
	}
	
	public Topic getTopic() {
		return topic;
	}
	
	public void setTopic(final Topic t) {
		this.topic = t;
	}
	
	public boolean add(final SearchResult searchResult) {
		if (searchResult.getResults().size() > 0) {
			searchResults.add(searchResult);
			return true;
		} else {
			return false;
		}
	}
	
	public SearchResult getSearchResult(int index) {
		return searchResults.get(index);
	}
	
	public List<SearchResult> getAllSearchResults() {
		return new ArrayList<SearchResult>(searchResults);
	}
	
	public List<Result> getAllResults() {
		List<Result> results = new ArrayList<Result>();
		
		for (SearchResult searchResult : searchResults) {
			results.addAll(searchResult.getResults());
		}
		
		return results;
	}

	public int getNumberOfSearchResults() {
		return searchResults.size();
	}
	
	public int results() {
		return searchResults.size();
	}
	
	public boolean isEmpty() {
		return searchResults.size() == 0;
	}
	
	@Override
	public String toString() {
		return "Session[id=" + id + " search_results=" + results() + "]";
	}
	
}
