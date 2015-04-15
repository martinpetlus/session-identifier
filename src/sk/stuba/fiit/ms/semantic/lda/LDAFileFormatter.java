package sk.stuba.fiit.ms.semantic.lda;

import java.io.PrintWriter;
import java.util.List;

import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class LDAFileFormatter implements SearchFormatter {

    public static final String FILE = "lda.dat";

    public void write(final List<Session> sessions) {
        try {
            PrintWriter writer = new PrintWriter(FILE, "UTF-8");

            for (Session session : sessions) {
                for (Search search : session.getAllSearches()) {
                    writer.println(search.getId() + " X " + searchToText(search));
                }
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendText(final StringBuilder sb, final String text, final boolean appendSpace) {
        if (!text.isEmpty()) {
            sb.append(text);
        }

        if (appendSpace) {
            sb.append(' ');
        }
    }

    @Override
    public String searchToText(final Search search) {
        StringBuilder sb = new StringBuilder();

        appendText(sb, search.getQuery(), true);

        for (Result result : search.getResults()) {
            appendText(sb, resultToText(result), true);
        }

        return sb.toString().trim();
    }

    private String resultToText(final Result result) {
        StringBuilder sb = new StringBuilder();

        appendText(sb, result.getTitle(), true);
        appendText(sb, result.getSnippet(), true);
        appendText(sb, result.getContent(), false);

        return sb.toString();
    }

}
