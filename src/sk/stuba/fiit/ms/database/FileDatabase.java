package sk.stuba.fiit.ms.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.ms.document.DocumentContent;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Session;

public final class FileDatabase implements Database {

	private static FileDatabase instance;

	private static final String FILE = "docs.ser";

	private final Map<String, DocumentContent> docs;

	@SuppressWarnings("unchecked")
	private FileDatabase() {
		Map<String, DocumentContent> docs = null;

		try {
			InputStream file = new FileInputStream(FILE);
			ObjectInput input = new ObjectInputStream(file);

			docs = (Map<String, DocumentContent>) input.readObject();

			System.out.println("Number of loaded downloaded docs: " + docs.size());

			input.close();
		} catch (Exception ex) {
			System.err.println("Cannot load: " + FILE);
		}

		if (docs != null && !docs.isEmpty()) {
			this.docs = docs;
		} else {
			this.docs = new HashMap<String, DocumentContent>();
		}
	}

	@Override
	public boolean containsContent(final String url) {
		return docs.containsKey(url);
	}

	@Override
	public DocumentContent getContent(final String url) {
		return docs.get(url);
	}

	@Override
	public void save(final DocumentContent doc) {
		docs.put(doc.getUrl(), doc);
	}

	@Override
	public int size() {
		return docs.size();
	}

	@Override
	public void close() {
		try {
			FileOutputStream file = new FileOutputStream(FILE);
			ObjectOutputStream oos = new ObjectOutputStream(file);

			oos.writeObject(docs);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<DocumentContent> getAllContents() {
		return new ArrayList<DocumentContent>(docs.values());
	}

	@Override
	public void setResultsContent(final List<Session> sessions) {
		for (Session session : sessions) {
			for (Result result : session.getAllResults()) {
				String url = result.getUrl();

				if (this.containsContent(url)) {
					DocumentContent doc = this.getContent(url);
					result.setContent(doc.getContent());
				}
			}
		}
	}

	public synchronized static FileDatabase getInstance() {
		if (instance == null) {
			instance = new FileDatabase();
		}

		return instance;
	}

}
