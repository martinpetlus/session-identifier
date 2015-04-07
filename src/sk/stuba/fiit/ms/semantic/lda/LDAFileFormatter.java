package sk.stuba.fiit.ms.semantic.lda;

import java.io.PrintWriter;
import java.util.List;

import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Session;

public final class LDAFileFormatter implements ResultTextFormatter {
	
	public static final String FILE = "lda.dat";

	private final boolean writeTitle;
	
	private final boolean writeSnippet;
	
	private final boolean writeContent;
	
	public LDAFileFormatter() {
		this(true, true, true);
	}
	
	public LDAFileFormatter(final boolean writeTitle,
			final boolean writeSnippet, final boolean writeContent) {
		this.writeTitle = writeTitle;
		this.writeSnippet = writeSnippet;
		this.writeContent = writeContent;
	}

	public void write(final List<Session> sessions) {
		try {
			PrintWriter writer = new PrintWriter(FILE, "UTF-8");
			
			for (Session session : sessions) {
				for (Result result : session.getAllResults()) {
					writer.println(result.getId() + " X " + formatText(result));
				}
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFileName() {
		return FILE;
	}
	
	private void appendText(final boolean append, final String text,
			final StringBuilder sb, final boolean space) {
		if (append) {
			if (!text.isEmpty()) {
				sb.append(text);
				
				if (space) {
					sb.append(" ");
				}
			}
		}
	}

	@Override
	public String formatText(final Result result) {
		StringBuilder sb = new StringBuilder();
		
		appendText(writeTitle, result.getTitle(), sb, true);
		
		appendText(writeSnippet, result.getSnippet(), sb, true);
		
		appendText(writeContent, result.getContent(), sb, false);
		
		return sb.toString().trim();
	}
	
}
