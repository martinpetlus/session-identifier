package sk.stuba.fiit.ms.database;

import java.util.List;

import sk.stuba.fiit.ms.document.DocumentContent;
import sk.stuba.fiit.ms.session.Session;

public interface Database {

	public boolean containsContent(final String url);

	public DocumentContent getContent(final String url);

	public void save(final DocumentContent doc);

	public void close();

	public int size();

	public List<DocumentContent> getAllContents();

	public void setResultsContent(final List<Session> sessions);

}
