package sk.stuba.fiit.ms.session;

import java.util.ArrayList;
import java.util.List;

public final class Result {

	private static int numberOfResults = 0;

	private final int id;
	
	private final int rank;
	
	private final String url;
	
	private final String title;
	
	private final String snippet;

	private List<Click> clicks;
	
	private String content;

	public Result(int rank, final String url, final String title, final String snippet) {
		this.id = ++numberOfResults;
		this.rank = rank;
		this.url = url;
		this.title = title;
		this.snippet = snippet;
		this.clicks = null;
		this.content = "";
	}

	public int getRank() {
		return rank;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getSnippet() {
		return snippet;
	}
	
	public int getId() {
		return id;
	}

	public List<Click> getClicks() {
		if (clicks == null || clicks.isEmpty()) {
			return new ArrayList<Click>();
		} else {
			return new ArrayList<Click>(clicks);
		}
	}

	void addClick(final Click click) {
		if (clicks == null) {
			clicks = new ArrayList<Click>();
		}

		clicks.add(click);
	}

	public double getSpentTime() {
		if (isClicked()) {
			double sum = 0.0;

			for (Click click : clicks) {
				sum += click.getSpentTime();
			}

			return  sum;
		} else {
			return 0.0;
		}
	}

	public boolean isClicked() {
		return clicks != null && !clicks.isEmpty();
	}

	public int getNumberOfClicks() {
		return clicks == null ? 0 : clicks.size();
	}
	
	public void setContent(final String content) {
		if (content != null) {
			this.content = content.trim();
		}
	}
	
	public String getContent() {
		return this.content;
	}
	
	@Override
	public String toString() {
		return "Result[id=" + id +
				" rank=" + rank + " url=" + url +
				" title=" + title + " snippet=" +
				snippet +  " clicks=" + getNumberOfClicks() +
				"]\n";
	}
	
}
