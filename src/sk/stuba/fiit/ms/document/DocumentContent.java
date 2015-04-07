package sk.stuba.fiit.ms.document;

import java.io.Serializable;

public final class DocumentContent implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String url;
	
	private final String content;
	
	public DocumentContent(final String url, final String content) {
		this.url = url;
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getContent() {
		return content;
	}
	
}
